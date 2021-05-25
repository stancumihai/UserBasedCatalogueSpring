package com.example.db_project.dao.studyGroup;

import com.example.db_project.mapper.studyGroup.EnrolledStudentRowMapper;
import com.example.db_project.mapper.studyGroup.StudentActivityGroupRowMapper;
import com.example.db_project.mapper.studyGroup.StudentStudyGroupRowMapper;
import com.example.db_project.mapper.studyGroup.StudyGroupRowMapper;
import com.example.db_project.mapper.user.StudentRowMapper;
import com.example.db_project.mapper.user.UserRowMapper;
import com.example.db_project.model.studyGroup.EnrolledStudent;
import com.example.db_project.model.studyGroup.StudentActivityGroup;
import com.example.db_project.model.studyGroup.StudentStudyGroup;
import com.example.db_project.model.studyGroup.StudyGroup;
import com.example.db_project.model.users.Student;
import com.example.db_project.model.users.User;
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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository("StudyGroupRepo")
public class StudyGroupDataAccessService implements StudyGroupDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public StudentStudyGroup enrollStudentToStudyGroup(StudentStudyGroup studentStudyGroup) {
        String sql = "insert into studentstudygroup (student, studyGroup) VALUES (:student, :studyGroup)";

        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("student", studentStudyGroup.getStudent().getId())
                .addValue("studyGroup", studentStudyGroup.getStudyGroup().getId());

        namedParameterJdbcTemplate.update(sql, parameters, holder);


        studentStudyGroup.setId(Objects.requireNonNull(holder.getKey()).intValue());
        return studentStudyGroup;
    }

    @Override
    public Optional<StudentActivityGroup> getGroupActivityInfoById(int groupActivityId) {
        String sql = "select * from studentactivitygroup where id  = ( ? )";
        StudentActivityGroup res = jdbcTemplate
                .queryForObject(sql, new StudentActivityGroupRowMapper(), groupActivityId);

        assert res != null;
        return Optional.of(res);
    }

    @Override
    public List<StudentActivityGroup> getActivitiesByGroupId(int groupId) {

        String sql = "select * from studentactivitygroup where studyGroup = (?)";

        return jdbcTemplate
                .query(sql, new StudentActivityGroupRowMapper(), groupId);
    }

    @Override
    public List<StudyGroup> getStudyGroupsBySubjectId(int subjectId) {
        String sql = "select * from studygroup where subject = ?";
        return jdbcTemplate.query(sql, new StudyGroupRowMapper(), subjectId);
    }

    @Override
    public StudentActivityGroup createStudyGroupActivity
            (StudentActivityGroup studentActivityGroup) {

        String sql = "insert into studentactivitygroup" +
                "(studyGroup, minAttendees, startDate, duration, enrollmentDeadline, state, initiator) " +
                "values (:studyGroup, :minAttendees, :startDate, :duration, :enrollmentDeadline, :state, :initiator)";

        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("studyGroup", studentActivityGroup.getStudyGroup().getId())
                .addValue("minAttendees", studentActivityGroup.getMinAttendees())
                .addValue("startDate", studentActivityGroup.getStartDate())
                .addValue("duration", studentActivityGroup.getDuration())
                .addValue("enrollmentDeadline", studentActivityGroup.getEnrollmentDeadline())
                .addValue("state", studentActivityGroup.getState().toString())
                .addValue("initiator", studentActivityGroup.getInitiator().getId());

        namedParameterJdbcTemplate.update(sql, parameters, holder);
        studentActivityGroup.setId(Objects.requireNonNull(holder.getKey()).intValue());
        return studentActivityGroup;
    }

    public int enrollProfessorToStudentActivityGroup(int profId, int id) {
        String sql = "update studentactivitygroup set professor= ? where id= ?";
        return jdbcTemplate.update(sql, id, profId);
    }

    public EnrolledStudent enrollStudentToGroupActivity(int groupActivityId, int studentId) {
        String sql = "insert into enrolledstudent (studentActivity, student) VALUES (:studentActivity, :student)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("studentActivity", groupActivityId)
                .addValue("student", studentId);
        namedParameterJdbcTemplate.update(sql, parameters, holder);
        EnrolledStudent enrolledStudent = new EnrolledStudent();
        enrolledStudent.setId(Objects.requireNonNull(holder.getKey()).intValue());
        Student student = new Student();
        student.setId(studentId);
        StudentActivityGroup studentActivityGroup = new StudentActivityGroup();
        studentActivityGroup.setId(groupActivityId);
        enrolledStudent.setStudent(student);
        enrolledStudent.setActivity(studentActivityGroup);
        return enrolledStudent;
    }

    @Override
    public List<StudentStudyGroup> getStudyGroupStudents(int studyGroupId) {
        String sql = "select * from studentstudygroup where studyGroup = ?";
        return jdbcTemplate.query(sql, new StudentStudyGroupRowMapper(), studyGroupId);
    }

    @Override
    public StudentStudyGroup getEnrolledStatus(int studyGroupId, int studentId) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("isEnrolledInStudyGroup");
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("studentId", studentId)
                .addValue("studyGroupId", studyGroupId);

        Map<String, Object> out = jdbcCall.execute(in);
        System.out.println(out);
        boolean isEnrolled = (int) out.get("enrolled") == 1;
        if (isEnrolled) {
            String sql = "select * from studentstudygroup where studyGroup = ? and student = ?;";
            return jdbcTemplate.queryForObject(sql, new StudentStudyGroupRowMapper(), studyGroupId, studentId);
        } else
            return null;
    }

    @Override
    public StudyGroup getStudyGroupById(int studyGroupId) {
        String sql = "select * from studygroup where id = ?";
        return jdbcTemplate.queryForObject(sql, new StudyGroupRowMapper(), studyGroupId);
    }

    @Override
    public StudyGroup createStudyGroup(StudyGroup studyGroup) {
        String sql = "insert into studygroup (subject, creator, name) value (:subject, :creator, :name)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", studyGroup.getName())
                .addValue("subject", studyGroup.getSubject().getId())
                .addValue("creator", studyGroup.getCreator().getId());


        namedParameterJdbcTemplate.update(sql, parameters, holder);
        studyGroup.setId(Objects.requireNonNull(holder.getKey()).intValue());
        return studyGroup;
    }

    @Override
    public List<StudentStudyGroup> getStudentStudyGroups(int studentId) {
        String sql = "select * from studentstudygroup where student = ?";
        return jdbcTemplate.query(sql, new StudentStudyGroupRowMapper(), studentId);
    }

    @Override
    public List<EnrolledStudent> getGroupActivityEnrolledStudents(int groupActivityId) {
        String sql = "select * from enrolledstudent where studentActivity = ?";
        return jdbcTemplate.query(sql, new EnrolledStudentRowMapper(), groupActivityId);
    }

    @Override
    public int quitGroupActivity(int id) {
        String sql = "delete from enrolledstudent where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int quitStudyGroup(int id) {
        String sql = "delete from studentstudygroup where id = ?";
        return jdbcTemplate.update(sql, id);
    }


    @Override
    public List<Student> getSuggestionsForStudyGroup(int studyGroupId) {
        String sql = "select * from user u join student s on s.user=u.id " +
                "join subjectstudent  ss on ss.student=s.id " +
                "where ss.subject = ? and ss.student not in " +
                "(select student from studentstudygroup ssg where ssg.studygroup = ?)" +
                "and u.id not in (select creator from studygroup where id = ?)";
        StudyGroup studyGroup = getStudyGroupById(studyGroupId);
        return jdbcTemplate.query(sql, new StudentRowMapper(), studyGroup.getSubject().getId(), studyGroup.getId(), studyGroup.getId());
    }

    @Override
    public int deleteStudyGroup(int id) {
        String sql = "delete from studygroup where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateGroupActivity(StudentActivityGroup studentActivityGroup) {
        String sql = "update studentactivitygroup set state = ? where id = ?";
        jdbcTemplate.update(sql, studentActivityGroup.getState().toString(), studentActivityGroup.getId());
    }

}

