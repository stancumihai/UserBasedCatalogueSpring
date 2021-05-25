package com.example.db_project.dao.subject;

import com.example.db_project.mapper.subject.SubjectActivityPonderRowMapper;
import com.example.db_project.mapper.subject.SubjectRowMapper;
import com.example.db_project.model.course.Subject;
import com.example.db_project.model.course.SubjectActivityPonder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository("SubjectRepo")
public class SubjectDataAccessService implements SubjectDao {

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public List<Subject> getAllSubjects() {
        String sql = "select * from subject";

        return jdbcTemplate.query(sql, new SubjectRowMapper());
    }

    @Override
    public Subject getSubjectById(int id) {
        String sql = "select * from subject where id = ?";
        return jdbcTemplate.queryForObject(sql, new SubjectRowMapper(), id);
    }


    @Override
    public Optional<Subject> createSubject(Subject subject) {
        String sql = "insert into subject (name, description, maxStudents) VALUES (:name, :description, :maxStudents)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", subject.getName())
                .addValue("description", subject.getDescription())
                .addValue("maxStudents", subject.getMaxStudents());
        namedParameterJdbcTemplate.update(sql, parameters, holder);
        subject.setId(Objects.requireNonNull(holder.getKey()).intValue());

        return Optional.of(subject);
    }

    @Override
    public Optional<Subject> updateSubject(Subject subject) {
        String sql = "update subject set name = ?, description = ?, maxStudents = ? where id = ?";
        int updated = jdbcTemplate.update(sql, subject.getName(), subject.getDescription(), subject.getMaxStudents(), subject.getId());
        if (updated == 1) {
            return Optional.of(subject);
        }

        return Optional.empty();
    }

    @Override
    public int deleteSubject(int id) {
        String sql = "delete from subject where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public void insertPonder(int subjectId, String type, int ponder) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("insertCorrectPonder");
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("subjectId", subjectId)
                .addValue("newActivityType", type)
                .addValue("ponderValue", ponder);
        jdbcCall.execute(in);

    }

    @Override
    public List<SubjectActivityPonder> getSubjectPonders(int subjectId) {
        String sql = "select * from subjectactivityponder where subject = ?";
        return jdbcTemplate.query(sql, new SubjectActivityPonderRowMapper(), subjectId);
    }

}


