package com.example.db_project.dao.subject;

import com.example.db_project.model.course.Subject;
import com.example.db_project.model.course.SubjectActivityPonder;

import java.util.List;
import java.util.Optional;

public interface SubjectDao {

    List<Subject> getAllSubjects();

    Subject getSubjectById(int id);

    Optional<Subject> createSubject(Subject subject);

    Optional<Subject> updateSubject(Subject subject);

    int deleteSubject(int id);

    void insertPonder(int subjectId, String type, int ponder);

    List<SubjectActivityPonder> getSubjectPonders(int subjectId);

}
