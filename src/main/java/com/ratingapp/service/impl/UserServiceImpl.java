package com.ratingapp.service.impl;

import com.ratingapp.exception.InvalidEntityDataException;
import com.ratingapp.exception.NotFoundEntityException;
import com.ratingapp.model.User;
import com.ratingapp.repository.UserRepository;
import com.ratingapp.repository.UserRoleRepository;
import com.ratingapp.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl (UserRepository userRepository, UserRoleRepository userRoleRepository){
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) throws NotFoundEntityException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundEntityException(String.format("User with ID %d does not exist.", id)));
    }

    @Override
    public List<User> findByFirstNameIgnoreCase(String firstName) throws NotFoundEntityException {
        List<User> result = userRepository.findByFirstNameIgnoreCase(firstName);
        if (result == null || result.isEmpty()){
            throw new NotFoundEntityException(String.format("User with first name '%s' does not exist.", firstName));
        }
        return result;
    }

    @Override
    public List<User> findByLastNameIgnoreCase(String lastName) throws NotFoundEntityException {
        List<User> result = userRepository.findByLastNameIgnoreCase(lastName);
        if (result == null || result.isEmpty()){
            throw new NotFoundEntityException(String.format("User with last name '%s' does not exist.", lastName));
        }
        return result;
    }

    @Override
    public List<User> findByUserRole(String userRole) throws NotFoundEntityException {
        List<User> result = userRepository.findByUserRole(userRoleRepository.findByNameIgnoreCase(userRole));
        if (result == null || result.isEmpty()){
            throw new NotFoundEntityException(String.format("User with role '%s' does not exist.", userRole));
        }
        return result;
    }

    @Override
    public User createUser(User user) throws DataIntegrityViolationException, InvalidEntityDataException {
        areDependentPropertiesCorrect(user);
        userRepository.saveAndFlush(user);
        return user;
    }

    @Override
    public User updateUser(User user) throws DataIntegrityViolationException,InvalidEntityDataException, NotFoundEntityException {
        findById(user.getId());
        areDependentPropertiesCorrect(user);
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

    private void areDependentPropertiesCorrect(User user){
        if (userRoleRepository.findAll().stream().noneMatch(userRole -> userRole.getId().equals(user.getUserRole().getId()))){
            throw new NotFoundEntityException(String.format("User role with ID %d does not exist.", user.getUserRole().getId()));
        }
    }
}
