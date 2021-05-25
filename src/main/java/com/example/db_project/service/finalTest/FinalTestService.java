package com.example.db_project.service.finalTest;


import com.example.db_project.dao.finalTest.FinalTestDao;
import com.example.db_project.model.course.FinalTest;
import com.example.db_project.service.activity.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinalTestService {
    private final FinalTestDao finalTestDao;
    private final ActivityService activityService;

    @Autowired
    public FinalTestService(FinalTestDao finalTestDao, ActivityService activityService) {
        this.finalTestDao = finalTestDao;
        this.activityService = activityService;
    }

    public FinalTest createFinalTest(FinalTest finalTest) {
        FinalTest createdTest = finalTestDao.createTest(finalTest);
        createdTest.setActivity(activityService.getActivityById(finalTest.getActivity().getId()));
        return finalTest;
    }

    public List<FinalTest> getStudentFinalTests(int studentId) {
        List<FinalTest> finalTestList = finalTestDao.getStudentFinalTests(studentId);
        finalTestList.forEach(
                finalTest -> finalTest.setActivity(activityService.getActivityById(finalTest.getActivity().getId())));
        return finalTestList;
    }

    public List<FinalTest> getProfessorFinalTests(int professorId) {
        List<FinalTest> finalTestList = finalTestDao.getProfessorFinalTests(professorId);
        finalTestList.forEach(
                finalTest -> finalTest.setActivity(activityService.getActivityById(finalTest.getActivity().getId())));
        return finalTestList;
    }

}
