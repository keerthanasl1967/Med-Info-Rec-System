package com.medicinalplants.model;

import java.sql.Timestamp;

public class User {
    private int id;
    private String username;
    private String email;
    private String passwordHash;
    private String role;
    private Timestamp createdAt;

    public User(int id, String username, String email, String passwordHash, String role, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getRole() { return role; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setRole(String role) { this.role = role; }
}
