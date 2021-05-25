package com.example.db_project.dao.userSubject;

import com.example.db_project.model.course.Activity;
import com.example.db_project.model.course.StudentActivity;
import com.example.db_project.model.course.SubjectProfessor;
import com.example.db_project.model.course.SubjectStudent;

import java.util.List;
import java.util.Optional;

public interface UserSubjectDao {


    int enrollStudentToSubject(int subjectId, int studentId);

    int assignProfessorToSubject(int subjectId, int professorId);

    int deleteStudentFromSubject(int subjectId, int studentId);

    boolean isEnrolledToSubject(int subjectId, int studentId);

    List<SubjectStudent> getStudentSubjectsByStudentId(int studentId);

    List<StudentActivity> getStudentActivityByStudentId(int studentId);

    List<SubjectStudent> getSubjectStudentsBySubjectId(int subjectId);

    List<SubjectProfessor> getSubjectProfessorsBySubjectId(int subjectId);

    Optional<List<Activity>> getActivitiesByProfessorId(int profId);

    List<SubjectProfessor> getSubjectsByProfessorId(int profId);

    List<StudentActivity> getProfessorStudents(int professorId);

    void setActivityGrade(float id, int newGrade);

    StudentActivity getStudentActivity(int id);

    int enrollStudentToActivity(int studentId, int activityId);

    StudentActivity updateStudentActivity(StudentActivity studentActivity);

}
