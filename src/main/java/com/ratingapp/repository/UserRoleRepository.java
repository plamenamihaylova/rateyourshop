package com.ratingapp.repository;

import com.ratingapp.model.Roles;
import com.ratingapp.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByName(String role);
}
