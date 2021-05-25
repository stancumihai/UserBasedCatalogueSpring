package com.example.db_project.dao.student;

import com.example.db_project.mapper.user.StudentRowMapper;
import com.example.db_project.model.users.Student;
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

@Repository("StudentRepo")
public class StudentDataAccessService implements StudentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM student";

        return jdbcTemplate.query(
                sql,
                new StudentRowMapper());
    }

    @Override
    public Optional<Student> getStudentByUserId(int id) {
        try {
            String sql = "select * from student where user = ?";
            Student student = jdbcTemplate.queryForObject(sql, new StudentRowMapper(), id);
            assert student != null;
            return Optional.of(student);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Student createStudent(Student student) {
        String sql = "insert into student (user, yearOfStudy, numberOfHours) VALUES (:user, :yearOfStudy, :numberOfHours)";
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("user", student.getUser().getId())
                .addValue("yearOfStudy", student.getYearOfStudy())
                .addValue("numberOfHours", student.getNumberOfHours());

        namedParameterJdbcTemplate.update(sql, parameters, holder);
        student.setId(Objects.requireNonNull(holder.getKey()).intValue());

        return student;
    }

    @Override
    public Student updateStudent(Student student) {
        String sql = "update student set yearOfStudy = ?, numberOfHours = ? where id = ?";
        jdbcTemplate.update(sql, student.getYearOfStudy(), student.getNumberOfHours(), student.getId());
        return student;
    }

    @Override
    public Optional<Student> getStudentById(int studentId) {
        String sql = "select * from student where id = ?";
        Student student = jdbcTemplate.queryForObject(sql, new StudentRowMapper(), studentId);
        assert student != null;
        return Optional.of(student);
    }

}
