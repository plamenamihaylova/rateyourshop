package com.ratingapp.service.impl;

import com.ratingapp.model.User;
import com.ratingapp.repository.UserRepository;
import com.ratingapp.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        userRepository.saveAndFlush(user);
        return user;
    }

    @Override
    public Long count() {
        return userRepository.count();
    }
}
