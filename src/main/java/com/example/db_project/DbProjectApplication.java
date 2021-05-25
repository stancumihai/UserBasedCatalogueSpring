package com.example.db_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class DbProjectApplication {

    public static void main(String[] args) {

        SpringApplication.run(DbProjectApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

}
