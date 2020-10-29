package com.ratingapp.service;

import com.ratingapp.exception.InvalidEntityDataException;
import com.ratingapp.exception.NotFoundEntityException;
import com.ratingapp.model.User;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User findById(Long id) throws NotFoundEntityException;
    List<User> findByFirstNameIgnoreCase(String firstName) throws NotFoundEntityException;
    List<User> findByLastNameIgnoreCase(String lastName) throws NotFoundEntityException;
    List<User> findByUserRole(String userRole) throws NotFoundEntityException;
    User createUser(User user) throws DataIntegrityViolationException, InvalidEntityDataException;
    User updateUser(User user) throws DataIntegrityViolationException, InvalidEntityDataException, NotFoundEntityException;
    User deleteUser(Long id)  throws NotFoundEntityException;
    Long count();
}
