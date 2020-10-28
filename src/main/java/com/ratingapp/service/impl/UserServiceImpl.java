package com.ratingapp.service.impl;

import com.ratingapp.exception.InvalidEntityDataException;
import com.ratingapp.exception.NotFoundEntityException;
import com.ratingapp.model.User;
import com.ratingapp.repository.UserRepository;
import com.ratingapp.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl (UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundEntityException(String.format("User with ID %d does not exist.", id)));
    }

    @Override
    public User createUser(User user) throws DataIntegrityViolationException {
        userRepository.saveAndFlush(user);
        return user;
    }

    @Override
    public User updateUser(User user) throws InvalidEntityDataException, NotFoundEntityException{
        findById(user.getId());
        userRepository.save(user);
        return user;
    }

    @Override
    public User deleteUser(Long id) throws NotFoundEntityException{
        User result = findById(id);
        userRepository.delete(result);
        return result;
    }

    @Override
    public Long count() {
        return userRepository.count();
    }
}
