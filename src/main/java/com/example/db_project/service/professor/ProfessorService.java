package com.example.db_project.service.professor;

import com.example.db_project.dao.professor.ProfessorDao;
import com.example.db_project.model.users.Professor;
import com.example.db_project.model.users.User;
import com.example.db_project.service.department.DepartmentService;
import com.example.db_project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    private final ProfessorDao professorDao;
    private final UserService userService;
    private final DepartmentService departmentService;

    @Autowired
    public ProfessorService(@Qualifier("ProfessorRepo") ProfessorDao professorDao, UserService userService, DepartmentService departmentService) {
        this.professorDao = professorDao;
        this.userService = userService;
        this.departmentService = departmentService;
    }

    public List<Professor> getAllProfessors() {
        List<Professor> professorList = professorDao.getAllProfessors();
        for (Professor professor : professorList) {
            Optional<User> user = userService.getUserById(professor.getUser().getId());
            if (user.isPresent()) {
                user.get().setRole("professor");
                professor.setUser(user.get());
            }
        }
        return professorList;
    }

    public Optional<Professor> getProfessorByUserId(int userId) {
        Optional<Professor> professor = professorDao.getProfessorByUserId(userId);
        professor.ifPresent(this::setProfessorUser);
        professor.ifPresent(this::setProfessorDepartment);
        return professor;
    }

    public Optional<Professor> getProfessorById(int professorId) {
        Optional<Professor> professor = professorDao.getProfessorById(professorId);
        professor.ifPresent(this::setProfessorUser);
        professor.ifPresent(this::setProfessorDepartment);
        return professor;
    }

    public Professor createProfessor(Professor professor) {
        Professor createdProfessor = professorDao.createProfessor(professor);
        setProfessorUser(createdProfessor);
        setProfessorDepartment(createdProfessor);
        return createdProfessor;
    }

    public Professor updateProfessor(Professor professor) {
        return professorDao.updateProfessor(professor);
    }

    private void setProfessorUser(Professor professor) {
        Optional<User> user = userService.getUserById(professor.getUser().getId());
        user.ifPresent(professor::setUser);
    }

    private void setProfessorDepartment(Professor professor) {
        professor.setDepartment(departmentService.getDepartmentById(professor.getDepartment().getId()));
    }
}
