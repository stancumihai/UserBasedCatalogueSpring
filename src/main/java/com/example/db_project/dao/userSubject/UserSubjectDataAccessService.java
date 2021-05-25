package com.example.db_project.dao.userSubject;

import com.example.db_project.mapper.subject.ActivityRowMapper;
import com.example.db_project.mapper.subjectProfessor.SubjectProfessorRowMapper;
import com.example.db_project.mapper.subjectStudent.StudentActivityRowMapper;
import com.example.db_project.mapper.subjectStudent.SubjectStudentRowMapper;
import com.example.db_project.model.course.Activity;
import com.example.db_project.model.course.StudentActivity;
import com.example.db_project.model.course.SubjectProfessor;
import com.example.db_project.model.course.SubjectStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

@Repository("UserSubjectRepo")
public class UserSubjectDataAccessService implements UserSubjectDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public int enrollStudentToSubject(int subjectId, int studentId) {
        try {
            String sql = "insert into subjectstudent (student, subject, subjectGrade) VALUES (?, ?, ?)";
            return jdbcTemplate.update(sql, studentId, subjectId, 0);
        } catch (DuplicateKeyException e) {
            return 0;
        }
    }

    @Override
    public int assignProfessorToSubject(int subjectId, int professorId) {
        try {
            String sql = "insert into subjectprofessor (subject, professor) VALUES (?, ?)";
            return jdbcTemplate.update(sql, subjectId, professorId);
        } catch (DuplicateKeyException e) {
            return 0;
        }
    }

    @Override
    public int deleteStudentFromSubject(int subjectId, int studentId) {

        String sql = "delete from subjectstudent where subject= ? and student= ?";
        return jdbcTemplate.update(sql, subjectId, studentId);

    }

    @Override
    public boolean isEnrolledToSubject(int subjectId, int studentId) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("isEnrolledToSubject");
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("studentId", studentId)
                .addValue("subjectId", subjectId);
        Map<String, Object> out = jdbcCall.execute(in);
        System.out.println(out);
        return (int) out.get("enrolled") == 1;
    }

    @Override
    public List<SubjectStudent> getStudentSubjectsByStudentId(int studentId) {
        String sql = "select * from subjectstudent where student = ?";
        return jdbcTemplate.query(sql, new SubjectStudentRowMapper(), studentId);
    }

    @Override
    public List<StudentActivity> getStudentActivityByStudentId(int studentId) {
        String sql = "select * from studentactivity where student = ?";
        return jdbcTemplate.query(sql, new StudentActivityRowMapper(), studentId);
    }

    @Override
    public List<SubjectStudent> getSubjectStudentsBySubjectId(int subjectId) {
        String sql = "select * from subjectstudent where subject = ?";
        return jdbcTemplate.query(sql, new SubjectStudentRowMapper(), subjectId);
    }

    @Override
    public List<SubjectProfessor> getSubjectProfessorsBySubjectId(int subjectId) {
        String sql = "select * from subjectprofessor where subject = ?";
        return jdbcTemplate.query(sql, new SubjectProfessorRowMapper(), subjectId);
    }


    @Override
    public Optional<List<Activity>> getActivitiesByProfessorId(int profId) {
        String sql = "select * from activity where professor= ?";
        List<Activity> res = jdbcTemplate.query(sql, new ActivityRowMapper(), profId);
        return Optional.of(res);
    }

    @Override
    public List<SubjectProfessor> getSubjectsByProfessorId(int profId) {
        String sql = "select * from subjectprofessor where professor = ?";
        return jdbcTemplate.query(sql, new SubjectProfessorRowMapper(), profId);
    }

    @Override
    public List<StudentActivity> getProfessorStudents(int professorId) {
        String sql = "select * from professorstudents where professor = ?";

        return jdbcTemplate.query(sql, new StudentActivityRowMapper(), professorId);
    }

    @Override
    public void setActivityGrade(float newGrade, int id) {
        String updateQuery = "update studentactivity set activityGrade=? where id=?";
        jdbcTemplate.update(updateQuery, newGrade, id);
        System.out.println("updated");

//        StudentActivity studentActivity = getStudentActivity(id);
//        int activityId = studentActivity.getActivity().getId();
//        int studentId = studentActivity.getStudent().getId();
//
//        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("insertFinalMark");
//        SqlParameterSource in = new MapSqlParameterSource()
//                .addValue("activityId", activityId)
//                .addValue("studentId", studentId);
//        jdbcCall.execute(in);
    }

    @Override
    public StudentActivity getStudentActivity(int id) {
        String sql = "select * from studentactivity where id = ?";

        return jdbcTemplate.queryForObject(sql, new StudentActivityRowMapper(), id);
    }

    @Override
    public int enrollStudentToActivity(int studentId, int activityId) {
        String sql = "insert into studentactivity (student, activity, activityGrade) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, studentId, activityId, 0);
    }

    @Override
    public StudentActivity updateStudentActivity(StudentActivity studentActivity) {
        System.out.println(studentActivity);
        String sql = "update studentactivity set activity = ? where id = ?";
        int updated = jdbcTemplate.update(sql, studentActivity.getActivity().getId(), studentActivity.getId());
        System.out.println(updated);
        return studentActivity;
    }
}
