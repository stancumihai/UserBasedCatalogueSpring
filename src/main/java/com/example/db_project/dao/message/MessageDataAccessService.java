package com.example.db_project.dao.message;

import com.example.db_project.mapper.message.MessageRowMapper;
import com.example.db_project.model.studyGroup.Message;
import org.springframework.beans.factory.annotation.Autowired;
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

@Repository("MessageRepo")
public class MessageDataAccessService implements MessageDao {


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Message> getAllMessages() {
        String sql = "Select * from message ";

        return jdbcTemplate.query(
                sql,
                new MessageRowMapper());
    }

    @Override
    public Message getMessageById(int id) {
        String sql = "Select * from message where id = ? ";

        return jdbcTemplate.queryForObject(sql, new MessageRowMapper(), id);

    }

    @Override
    public Optional<Message> createMessage(Message message) {
        String sql = "insert into message(student,studygroup,message) values(:student,:studygroup,:message)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("student", message.getStudent().getId())
                .addValue("studygroup", message.getStudyGroup().getId())
                .addValue("message", message.getMessage());
        namedParameterJdbcTemplate.update(sql, parameters, holder);
        message.setId(Objects.requireNonNull(holder.getKey()).intValue());
        return Optional.of(message);
    }


    @Override
    public List<Message> getMessagesByStudyGroupId(int studyGroup) {
        String sql = "Select * from message where studyGroup = ? ";

        return jdbcTemplate.query(sql, new MessageRowMapper(), studyGroup);
    }

    @Override
    public List<Message> getMessagesByStudentId(int student) {
        String sql = "Select * from message where student = ? ";
        return jdbcTemplate.query(sql, new MessageRowMapper(), student);
    }
}

