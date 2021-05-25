package com.example.db_project.dao.department;


import com.example.db_project.mapper.department.DepartmentRowMapper;
import com.example.db_project.model.users.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository("DepartmentRepo")
public class DepartmentDataAccessService implements DepartmentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Department getDepartmentById(int id) {
        String sql = "select * from department where id = ?";
        return jdbcTemplate.queryForObject(sql, new DepartmentRowMapper(), id);
    }

    @Override
    public List<Department> getAllDepartments() {
        String sql = "select * from department";
        return jdbcTemplate.query(sql, new DepartmentRowMapper());
    }

    @Override
    public Department createDepartment(Department department) {
        String sql = "insert into department (name) value (:name)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", department.getName());

        namedParameterJdbcTemplate.update(sql, parameters, holder);
        department.setId(Objects.requireNonNull(holder.getKey()).intValue());
        return department;
    }

    @Override
    public int deleteDepartment(int departmentId) {
        String sql = "delete from department where id = ?";
        return jdbcTemplate.update(sql, departmentId);
    }
}
