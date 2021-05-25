package com.example.db_project.mapper.studyGroup;

import com.example.db_project.model.course.GroupActivityState;
import com.example.db_project.model.studyGroup.StudentActivityGroup;
import com.example.db_project.model.studyGroup.StudyGroup;
import com.example.db_project.model.users.Professor;
import com.example.db_project.model.users.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class StudentActivityGroupRowMapper implements RowMapper<StudentActivityGroup> {

    @Override
    public StudentActivityGroup mapRow(ResultSet resultSet, int i) throws SQLException {

        StudentActivityGroup studentActivityGroup = new StudentActivityGroup();

        studentActivityGroup.setId(resultSet.getInt("id"));

        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setId(resultSet.getInt("studyGroup"));
        studentActivityGroup.setStudyGroup(studyGroup);

        studentActivityGroup.setMinAttendees(resultSet.getInt("minAttendees"));
        studentActivityGroup.setStartDate(resultSet.getTimestamp("startDate"));
        studentActivityGroup.setDuration(resultSet.getInt("duration"));
        studentActivityGroup.setEnrollmentDeadline(resultSet.getTimestamp("enrollmentDeadline"));

        GroupActivityState state = GroupActivityState.valueOf(resultSet.getString("state").toUpperCase());
        studentActivityGroup.setState(state);

        Student student = new Student();
        student.setId(resultSet.getInt("initiator"));
        studentActivityGroup.setInitiator(student);

        Professor professor = new Professor();
        professor.setId(resultSet.getInt("professor"));
        studentActivityGroup.setProfessor(professor);

        return studentActivityGroup;

    }
}

