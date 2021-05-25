package com.example.db_project.service.subject;

import com.example.db_project.dao.activity.ActivityDao;
import com.example.db_project.dao.subject.SubjectDao;
import com.example.db_project.model.course.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    private final SubjectDao subjectDao;

    @Autowired
    public SubjectService(@Qualifier("SubjectRepo") SubjectDao subjectDao, ActivityDao activityDao) {
        this.subjectDao = subjectDao;
    }

    public List<Subject> getAllSubjects() {
        return subjectDao.getAllSubjects();
    }

    public Subject getSubjectById(int id) {
        return subjectDao.getSubjectById(id);
    }


    public Optional<Subject> createSubject(Subject subject) {
        return subjectDao.createSubject(subject);
    }

    public Optional<Subject> updateSubject(Subject subject) {
        return subjectDao.updateSubject(subject);
    }

    public boolean deleteSubject(int id) {

        int deleted = subjectDao.deleteSubject(id);
        return deleted == 1;
    }

    public List<SubjectActivityPonder> getSubjectActivityPonders(int subjectId) {
        return subjectDao.getSubjectPonders(subjectId);
    }

    public void insertPonder(SubjectActivityPonder subjectActivityPonder) {
        subjectDao.insertPonder(subjectActivityPonder.getSubject().getId(), subjectActivityPonder.getType().toString(),
                subjectActivityPonder.getPonder());
    }


}






