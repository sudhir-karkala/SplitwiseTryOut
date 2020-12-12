package com.splitwise.services;

import com.splitwise.models.User;
import com.splitwise.repositories.UserRepository;

/**
 * @author sudhir on 11-Dec-2020
 */
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(String userId) {
        return userRepository.getUserById(userId);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
