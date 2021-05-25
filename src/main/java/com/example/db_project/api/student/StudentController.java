package com.example.db_project.api.student;

import com.example.db_project.model.users.Student;
import com.example.db_project.model.studyGroup.StudentStudyGroup;
import com.example.db_project.service.student.StudentService;
import com.example.db_project.service.studyGroup.StudyGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {
    private final StudentService studentService;
    private final StudyGroupService studyGroupService;

    @Autowired
    public StudentController(StudentService studentService, StudyGroupService studyGroupService) {
        this.studentService = studentService;
        this.studyGroupService = studyGroupService;
    }

    @GetMapping()
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }


    @GetMapping(path = "{studentId}/study_groups")
    public List<StudentStudyGroup> getStudentStudyGroups(@PathVariable("studentId") int studentId) {
        return studyGroupService.getStudentStudyGroups(studentId);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping(path = "/{id}")
    public Student updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

}
