package com.example.db_project.dao.finalTest;

import com.example.db_project.mapper.finalTest.FinalTestRowMapper;
import com.example.db_project.model.course.FinalTest;
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


@Repository("FinalTestRepo")
public class FinalTestDataAccessService implements FinalTestDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public FinalTest createTest(FinalTest finalTest) {
        String sql = "insert into finaltest (date, activity, type) VALUES (:date, :activity, :type)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("activity", finalTest.getActivity().getId())
                .addValue("date", finalTest.getDate())
                .addValue("type", finalTest.getType().toString());

        namedParameterJdbcTemplate.update(sql, parameters, holder);
        finalTest.setId(Objects.requireNonNull(holder.getKey()).intValue());
        return finalTest;
    }

    @Override
    public List<FinalTest> getProfessorFinalTests(int professorId) {
        String sql = "select * from finaltest join activity a on a.id = finaltest.activity where a.professor = ?";
        return jdbcTemplate.query(sql, new FinalTestRowMapper(), professorId);
    }

    @Override
    public List<FinalTest> getStudentFinalTests(int studentId) {
        String sql = "select * from finaltest " +
                "join activity a on a.id = finaltest.activity join studentactivity s on a.id = s.activity " +
                "where s.student = ?";
        return jdbcTemplate.query(sql, new FinalTestRowMapper(), studentId);
    }
}
