package com.example.db_project.utils;

import java.util.UUID;

public class SessionKeyGenerator {

    public String generateKey(){
        return UUID.randomUUID().toString();
    }

}
