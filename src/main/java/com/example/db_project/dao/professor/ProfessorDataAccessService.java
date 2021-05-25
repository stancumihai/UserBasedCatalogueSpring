package com.example.db_project.dao.professor;

import com.example.db_project.mapper.user.ProfessorRowMapper;
import com.example.db_project.model.users.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository("ProfessorRepo")
public class ProfessorDataAccessService implements ProfessorDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Professor> getAllProfessors() {
        String sql = "SELECT * FROM professor";

        return jdbcTemplate.query(
                sql,
                new ProfessorRowMapper());
    }

    @Override
    public Optional<Professor> getProfessorByUserId(int id) {
        try {
            String sql = "select * from professor where user = ?";

            Professor professor = jdbcTemplate.queryForObject(sql, new ProfessorRowMapper(), id);
            assert professor != null;
            return Optional.of(professor);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Professor> getProfessorById(int professorId) {
        String sql = "select * from professor where id = ?";
        Professor professor = jdbcTemplate.queryForObject(sql, new ProfessorRowMapper(), professorId);
        assert professor != null;
        return Optional.of(professor);
    }

    @Override
    public Professor createProfessor(Professor professor) {
        String sql = "insert into professor (user, minHours, maxHours, department) VALUES (:user, :minHours, :maxHours, :department)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("user", professor.getUser().getId())
                .addValue("minHours", professor.getMinHours())
                .addValue("maxHours", professor.getMaxHours())
                .addValue("department", professor.getDepartment().getId());


        namedParameterJdbcTemplate.update(sql, parameters, holder);
        professor.setId(Objects.requireNonNull(holder.getKey()).intValue());

        return professor;
    }

    @Override
    public Professor updateProfessor(Professor professor) {
        String sql = "update professor set minHours = ?, maxHours = ? , department = ? where id = ?";
        jdbcTemplate.update(sql, professor.getMinHours(), professor.getMaxHours(), professor.getDepartment().getId(), professor.getId());
        return professor;
    }


}
