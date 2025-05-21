package com.gaby.finalreq;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final Map<String, AppUser> users = new HashMap<>();

    public UserService() {
        users.put("Gaby", new AppUser("Gaby", "85423olb"));
    }

    public AppUser findByUsername(String username) {
        return users.get(username);
    }
}