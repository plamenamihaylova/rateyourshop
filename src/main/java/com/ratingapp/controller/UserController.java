package com.ratingapp.controller;

import com.ratingapp.exception.InvalidEntityDataException;
import com.ratingapp.exception.ValidationErrorsException;
import com.ratingapp.model.User;
import com.ratingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(@RequestParam(name = "firstName", required = false) String firstName,
                                  @RequestParam(name = "lastName", required = false) String lastName,
                                  @RequestParam(name = "role", required = false) String role){
        if (firstName != null){
           return userService.findByFirstNameIgnoreCase(firstName);
        }
        if (lastName != null){
            return userService.findByLastNameIgnoreCase(lastName);
        }
        if (role != null){
            return userService.findByUserRole(role);
        }
        return userService.findAllUsers();
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable Long id) throws EntityNotFoundException {
        return userService.findById(id);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user,
                                           Errors errors,
                                           HttpServletRequest request){
        if (errors.hasErrors()){
            throw new ValidationErrorsException(errors);
        }
        User newUser = userService.createUser(user);
        return ResponseEntity
                .created(
                        UriComponentsBuilder.fromUriString(
                                request.getRequestURL().toString()).pathSegment("{id}")
                                .build(newUser.getId()))
                .body(newUser);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @Valid @RequestBody User user,
                                           Errors errors,
                                           HttpServletRequest request){
        if (errors.hasErrors()){
            throw new ValidationErrorsException(errors);
        }

        if (!id.equals(user.getId())) {
            throw new InvalidEntityDataException(
                    String.format("Url ID %d differs from entity's body ID %d", id, user.getId()));
        }

        User newUser = userService.updateUser(user);
        return ResponseEntity
                .created(
                        UriComponentsBuilder.fromUriString(
                                request.getRequestURL().toString()).pathSegment("{id}")
                                .build(newUser.getId()))
                .body(newUser);

    }

    @DeleteMapping("{id}")
    public User deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}
