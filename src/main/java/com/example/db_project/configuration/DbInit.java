//package com.example.db_project.configuration;
//
//import com.example.db_project.dao.permission.PermissionDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
//@Component
//public class DbInit {
//    private final PermissionDao permissionDao;
//
//    @Autowired
//    public DbInit(PermissionDao permissionDao) {
//        this.permissionDao = permissionDao;
//    }
//
//
//    @PostConstruct
//    @Bean
//    private void postConstruct() {
//        permissionDao.initializePermissions();
//    }
//}
