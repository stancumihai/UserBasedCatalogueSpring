package com.example.db_project.service.adminSubject;


import com.example.db_project.dao.subject.SubjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminSubjectService {

    @Autowired
    private SubjectDao subjectDao;


}
