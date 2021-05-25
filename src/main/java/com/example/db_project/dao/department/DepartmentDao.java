package com.example.db_project.dao.department;

import com.example.db_project.model.users.Department;

import java.util.List;

public interface DepartmentDao {
    Department getDepartmentById(int id);

    List<Department> getAllDepartments();

    Department createDepartment(Department department);

    int deleteDepartment(int departmentId);

}
