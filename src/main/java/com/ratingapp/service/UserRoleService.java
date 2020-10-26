package com.ratingapp.service;

import com.ratingapp.model.Roles;
import com.ratingapp.model.UserRole;

import java.util.List;

public interface UserRoleService {
    List<UserRole> getAllUserRoles();
    UserRole findById(Long id);
    UserRole createUserRole(UserRole userRole);
    Long count();
    UserRole findByName(Roles role);
}
