package com.example.db_project.api.finalTest;


import com.example.db_project.model.course.FinalTest;
import com.example.db_project.service.finalTest.FinalTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/final_test")
public class FinalTestController {

    private final FinalTestService finalTestService;

    @Autowired
    public FinalTestController(FinalTestService finalTestService) {
        this.finalTestService = finalTestService;
    }

    @PostMapping
    public FinalTest createTest(@RequestBody FinalTest finalTest) {
        return finalTestService.createFinalTest(finalTest);
    }

    @GetMapping()
    public List<FinalTest> getFinalTests(@RequestParam(name = "studentId", required = false) Optional<Integer> studentId,
                                         @RequestParam(name = "professorId", required = false) Optional<Integer> professorId) {
        if (studentId.isPresent()) {
            return finalTestService.getStudentFinalTests(studentId.get());
        } else if (professorId.isPresent()) {
            return finalTestService.getProfessorFinalTests(professorId.get());
        }

        return null;
    }
}
