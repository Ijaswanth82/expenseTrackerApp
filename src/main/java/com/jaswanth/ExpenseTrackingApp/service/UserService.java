package com.jaswanth.ExpenseTrackingApp.service;

import com.jaswanth.ExpenseTrackingApp.model.User;

public interface UserService {
    User registerUser(User user);
    User findByUsername(String username);
    boolean existsByUsername(String username);
}
