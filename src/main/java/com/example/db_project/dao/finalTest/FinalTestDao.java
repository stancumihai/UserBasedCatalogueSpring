package com.example.db_project.dao.finalTest;

import com.example.db_project.model.course.FinalTest;

import java.util.List;

public interface FinalTestDao {
    FinalTest createTest(FinalTest finalTest);

    List<FinalTest> getProfessorFinalTests(int professorId);

    List<FinalTest> getStudentFinalTests(int studentId);


}
