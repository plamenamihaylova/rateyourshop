package com.ratingapp.service;

import com.ratingapp.model.Roles;
import com.ratingapp.model.UserRole;

import java.util.List;

public interface UserRoleService {
    List<UserRole> findAllUserRoles();
    UserRole findById(Long id);
    UserRole findByNameIgnoreCase(String role);
    UserRole createUserRole(UserRole userRole);
    Long count();
}
