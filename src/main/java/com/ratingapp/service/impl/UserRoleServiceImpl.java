package com.ratingapp.service.impl;

import com.ratingapp.exception.NotFoundEntityException;
import com.ratingapp.model.Roles;
import com.ratingapp.model.UserRole;
import com.ratingapp.repository.UserRoleRepository;
import com.ratingapp.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleServiceImpl (UserRoleRepository userRoleRepository){
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public List<UserRole> findAllUserRoles() {
        return userRoleRepository.findAll();
    }

    @Override
    public UserRole findById(Long id) {
        return userRoleRepository.findById(id).orElseThrow(() -> new NotFoundEntityException(String.format("User role with ID %d does not exist.", id)));
    }

    @Override
    public UserRole createUserRole(UserRole userRole) {
        userRoleRepository.saveAndFlush(userRole);
        return userRole;
    }

    @Override
    public Long count() {
        return userRoleRepository.count();
    }

    @Override
    public UserRole findByNameIgnoreCase(String role) {
        return userRoleRepository.findByNameIgnoreCase(role);
    }

}
