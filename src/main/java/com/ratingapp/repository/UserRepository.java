package com.ratingapp.repository;

import com.ratingapp.model.User;
import com.ratingapp.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFirstNameIgnoreCase(String firstName);
    List<User> findByLastNameIgnoreCase(String lastName);
    List<User> findByUserRole(UserRole userRole);
}
