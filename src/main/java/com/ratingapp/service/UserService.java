package com.ratingapp.service;

import com.ratingapp.model.User;

public interface UserService {
    User createUser(User user);
    Long count();
}
