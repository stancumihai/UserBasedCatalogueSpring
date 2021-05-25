package com.example.db_project.mapper.subjectStudent;

import com.example.db_project.model.course.Activity;
import com.example.db_project.model.users.Student;
import com.example.db_project.model.course.StudentActivity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentActivityRowMapper implements RowMapper<StudentActivity> {
    @Override
    public StudentActivity mapRow(ResultSet resultSet, int i) throws SQLException {
        StudentActivity studentActivity = new StudentActivity();

        studentActivity.setId(resultSet.getInt("id"));
        studentActivity.setActivityGrade(resultSet.getFloat("activityGrade"));

        Activity activity = new Activity();
        activity.setId(resultSet.getInt("activity"));
        studentActivity.setActivity(activity);
        Student student = new Student();
        student.setId(resultSet.getInt("student"));
        studentActivity.setStudent(student);
        return studentActivity;
    }
}
