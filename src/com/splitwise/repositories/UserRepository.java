package com.splitwise.repositories;

import com.splitwise.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sudhir on 11-Dec-2020
 */
public class UserRepository {
    Map<String, User> userMap = new HashMap<>();
    List<User> users = new ArrayList<>();

    public void addUser(User user) {
        userMap.put(user.getUserId(), user);
        users.add(user);
    }

    public User getUserById(String userId) {
        return userMap.get(userId);
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void setAllUsers(List<User> users) {
        this.users = users;
        for (User user : users) {
            userMap.put(user.getUserId(), user);
        }
    }
}
