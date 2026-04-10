package com.example.loginpage.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    // In-Memory user store using Map
    private final Map<String, String> users = new HashMap<>();

    public AuthService() {
        // Pre-loaded users (username -> password)
        users.put("admin", "admin123");
        users.put("john", "john123");
        users.put("roja", "roja123");
    }

    public boolean authenticate(String username, String password) {
        // Check if username exists and password matches
        return users.containsKey(username) && users.get(username).equals(password);
    }
}
