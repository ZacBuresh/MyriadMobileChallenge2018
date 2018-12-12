package com.example.sam01.final_project.model;

import org.json.JSONObject;

public class User {
    private String token, id, username;

    public User(String token, String id, String username) {
        this.token = token;
        this.id = id;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
