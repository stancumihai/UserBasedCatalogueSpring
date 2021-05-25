package com.example.db_project.dao.user;

import com.example.db_project.mapper.user.*;
import com.example.db_project.model.users.Address;
import com.example.db_project.model.users.User;
import com.example.db_project.model.users.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


@Repository("UserRepo")
public class UserDataAccessService implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public List<User> listAllUsers() {
        String sql = "SELECT * FROM useranduserrole";

        return jdbcTemplate.query(
                sql,
                new UserRowMapper());
    }

    @Override
    public Optional<User> getUserById(int id) {
        try {
            String sql = "SELECT * FROM useranduserrole WHERE ID = ?";

            User user = jdbcTemplate.queryForObject(sql,
                    new UserRowMapper(), id);
            assert user != null;
            user.setUserInfo(getUserInfo(user.getId()));
            return Optional.of(user);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        try {
            String sql = "SELECT * FROM useranduserrole WHERE username = ?";

            User res = jdbcTemplate.queryForObject(sql,
                    new UserRowMapper(), username);
            assert res != null;
            return Optional.of(res);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public boolean validateUserCredentials(String username, String password) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("validateUserCredentials");
        SqlParameterSource in = new MapSqlParameterSource().addValue("inUsername", username)
                .addValue("inPassword", password);
        Map<String, Object> out = jdbcCall.execute(in);

        System.out.println(out);

        return (int) out.get("res") == 1;
    }

    @Override
    public UserInfo getUserInfo(int userId) {
        try {
            String sql = "SELECT * from UserInfo where userId = ?";
            UserInfo res = jdbcTemplate.queryForObject(sql,
                    new UserInfoRowMapper(), userId);
            assert res != null;
            return res;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Address getUserAddress(int userId) {
        try {
            String sql = "SELECT * from address where userId = ?";
            Address res = jdbcTemplate.queryForObject(sql,
                    new AddressRowMapper(), userId);
            assert res != null;
            return res;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User createUser(User user) {
        String sql = "insert into user (username, password, is_active) VALUES (:username, :password, :is_active)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("password", user.getPassword())
                .addValue("is_active", true);

        namedParameterJdbcTemplate.update(sql, parameters, holder);
        user.setId(Objects.requireNonNull(holder.getKey()).intValue());
        user.setActive(true);
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sql = "update user set username = ?, password = ? , is_active = ? where id = ?";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getIsActive(), user.getId());
        return user;
    }

    @Override
    public UserInfo createUserInfo(UserInfo userInfo) {
        String sql = "insert into userinfo (userId, name, surname, cnp) VALUES (:userId, :name, :surname, :cnp)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", userInfo.getName())
                .addValue("surname", userInfo.getSurname())
                .addValue("cnp", userInfo.getCNP())
                .addValue("userId", userInfo.getUserId());

        namedParameterJdbcTemplate.update(sql, parameters, holder);
        userInfo.setId(Objects.requireNonNull(holder.getKey()).intValue());

        return userInfo;
    }

    @Override
    public Address createUserAddress(Address address) {
        String sql = "insert into address (userId, street, number, phone, email, IBAN, contractNr) " +
                "VALUES (:userId, :street, :number, :phone, :email, :IBAN, :contractNr)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("userId", address.getUserId())
                .addValue("street", address.getStreet())
                .addValue("number", address.getNumber())
                .addValue("phone", address.getPhone())
                .addValue("email", address.getEmail())
                .addValue("IBAN", address.getIBAN())
                .addValue("contractNr", address.getContractNr());

        namedParameterJdbcTemplate.update(sql, parameters, holder);
        address.setId(Objects.requireNonNull(holder.getKey()).intValue());

        return address;
    }

    @Override
    public Optional<List<User>> getSuggestionsForStudyGroup(int studyGroup) {
        String sql = "select * from user u join student s on s.user=u.id " +
                "join subjectstudent  ss on ss.student=s.id " +
                "where ss.subject = ? and ss.student not in " +
                "(select student from studentstudygroup ssg where ssg.studygroup = ?);";
        List<User> res = jdbcTemplate.query(sql, new UserRowMapper(), studyGroup, studyGroup);
        return Optional.of(res);
    }

    @Override
    public Optional<Address> updateAddress(Address address, int id) {

        String updateQuery = "UPDATE address SET userId=? ,street = ?, number = ?, phone = ?, " +
                "email = ?, IBAN= ? , contractNr = ? where id=?;";
        int updated = jdbcTemplate.update
                (updateQuery, address.getUserId(), address.getStreet(), address.getNumber()
                        , address.getPhone(), address.getEmail(), address.getIBAN(), address.getContractNr(), id);

        if (updated == 1) {
            return Optional.of(address);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserInfo> updateUserInfo(UserInfo userInfo, int id) {
        String updateQuery = "UPDATE userinfo SET userId=? ,name = ?, surname = ?, cnp = ? where id=?";
        int updated = jdbcTemplate.update
                (updateQuery, userInfo.getUserId(), userInfo.getName(), userInfo.getSurname(), userInfo.getCNP(), id);

        if (updated == 1) {
            return Optional.of(userInfo);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAdmins() {
        String sql = "SELECT * FROM useranduserrole WHERE role = ?";
        List<User> admins = jdbcTemplate.query(sql, new UserRowMapper(), "admin");
        admins.forEach(admin -> admin.setUserInfo(getUserInfo(admin.getId())));
        return admins;
    }

    @Override
    public int deleteUser(int userId) {
        String sql = "delete from user where id = ?";
        return jdbcTemplate.update(sql, userId);
    }

}
