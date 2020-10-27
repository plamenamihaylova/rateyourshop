package com.ratingapp.service;

import com.ratingapp.model.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User findById(Long id);
    User createUser(User user);
    Long count();
}
