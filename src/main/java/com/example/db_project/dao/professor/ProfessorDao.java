package com.example.db_project.dao.professor;


import com.example.db_project.model.users.Professor;

import java.util.List;
import java.util.Optional;

public interface ProfessorDao {
    List<Professor> getAllProfessors();

    Optional<Professor> getProfessorByUserId(int userId);

    Optional<Professor> getProfessorById(int professorId);

    Professor createProfessor(Professor professor);

    Professor updateProfessor(Professor professor);
}
