package com.ratingapp.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ratingapp.model.UserRole;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({ "authorities", "accountNonExpired", "accountNonLocked", "centralsNonExpired", "enabled"})
public class UserDTO {

    private Long id;

    @NonNull
    @NotNull
    @Size(min = 2, max = 50)
    private String username;

    @NonNull
    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;

    @NonNull
    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;

    @NonNull
    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NonNull
    @NotNull
    private String profilePicture;

    @NonNull
    @NotNull
    @NotEmpty
    private UserRole userRole;
}
