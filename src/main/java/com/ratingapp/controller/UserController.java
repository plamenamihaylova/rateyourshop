package com.ratingapp.controller;

import com.ratingapp.exception.InvalidEntityDataException;
import com.ratingapp.exception.ValidationErrorsException;
import com.ratingapp.model.User;
import com.ratingapp.model.dto.UserDTO;
import com.ratingapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper){
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<UserDTO> getAllUsers(@RequestParam(name = "firstName", required = false) String firstName,
                                  @RequestParam(name = "lastName", required = false) String lastName,
                                  @RequestParam(name = "role", required = false) String role){
        if (firstName != null){
           return userService.findByFirstNameIgnoreCase(firstName)
                   .stream().map(this::convertUserDTO)
                   .collect(Collectors.toList());
        }
        if (lastName != null){
            return userService.findByLastNameIgnoreCase(lastName)
                    .stream().map(this::convertUserDTO)
                    .collect(Collectors.toList());
        }
        if (role != null){
            return userService.findByUserRole(role)
                    .stream().map(this::convertUserDTO)
                    .collect(Collectors.toList());
        }
        return userService.findAllUsers()
                .stream().map(this::convertUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public UserDTO getUserById(@PathVariable Long id) throws EntityNotFoundException {
        return convertUserDTO(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<User> createNewUser(@Valid @RequestBody User user,
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
    public User updateUser(@PathVariable Long id,
                           @Valid @RequestBody User user,
                           Errors errors){
        if (errors.hasErrors()){
            throw new ValidationErrorsException(errors);
        }

        if (!id.equals(user.getId())) {
            throw new InvalidEntityDataException(
                    String.format("Url ID %d differs from entity's body ID %d", id, user.getId()));
        }

        return userService.updateUser(user);

    }

    @DeleteMapping("{id}")
    public User deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    private UserDTO convertUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToUserEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }
}
