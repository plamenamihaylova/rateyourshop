package com.ratingapp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 3
    )
    @Column(name = "user_id")
    private Long id;

    @NonNull
    @NotNull
    @Column(name = "username")
    private String username;

    @NonNull
    @NotNull
    @Email
    @Column(name = "user_email")
    private String email;

    @NonNull
    @NotNull
    @Column(name = "profile_picture")
    private String profilePicture;

    @NonNull
    @NotNull
    @ManyToOne (targetEntity = UserRole.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role_id", nullable = false)
    private UserRole userRole;
}
