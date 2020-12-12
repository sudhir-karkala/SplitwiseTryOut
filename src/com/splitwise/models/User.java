package com.splitwise.models;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sudhir on 11-Dec-2020
 */
public class User {
    private String userId;
    private String name;
    private String email;
    private long phoneNumber;
    private Map<User, Double> owesToMap;
    private Map<User, Double> owesFromMap;
    private double owesTo;
    private double owesFrom;

    public Map<User, Double> getOwesToMap() {
        return owesToMap;
    }

    public void setOwesToMap(Map<User, Double> owesToMap) {
        this.owesToMap = owesToMap;
    }

    public Map<User, Double> getOwesFromMap() {
        return owesFromMap;
    }

    public void setOwesFromMap(Map<User, Double> owesFromMap) {
        this.owesFromMap = owesFromMap;
    }

    public User() {
    }

    public User(String userId, String name, String email, long phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        owesFromMap = new HashMap<>();
        owesToMap = new HashMap<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getOwesTo() {
        return owesTo;
    }

    public void setOwesTo(double owesTo) {
        this.owesTo = owesTo;
    }

    public double getOwesFrom() {
        return owesFrom;
    }

    public void setOwesFrom(double owesFrom) {
        this.owesFrom = owesFrom;
    }

    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) return false;
        User guest = (User) obj;
        return (this.userId == guest.userId ||
                (this.userId != null && this.userId.equals(guest.userId)));
    }
}
