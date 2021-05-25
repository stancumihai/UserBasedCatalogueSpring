package com.example.db_project.service.department;

import com.example.db_project.dao.department.DepartmentDao;
import com.example.db_project.model.users.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentDao departmentDao;

    @Autowired
    public DepartmentService(@Qualifier("DepartmentRepo") DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    public Department getDepartmentById(int id) {
        return departmentDao.getDepartmentById(id);
    }

    public List<Department> getAllDepartments() {
        return departmentDao.getAllDepartments();
    }

    public int deleteDepartment(int id) {
        return departmentDao.deleteDepartment(id);
    }

    public Department createDepartment(Department department) {
        return departmentDao.createDepartment(department);
    }

}
