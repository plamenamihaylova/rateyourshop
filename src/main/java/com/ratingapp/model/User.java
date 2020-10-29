package com.ratingapp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users",
        uniqueConstraints = { @UniqueConstraint(columnNames = "username", name = "uniqueUsernameConstraint"),
                                @UniqueConstraint(columnNames = "user_email", name = "uniqueEmailConstraint")})
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
    @NotEmpty
    @Column(name = "username")
    private String username;

    @NonNull
    @NotNull
    @NotEmpty
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @NotNull
    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

    @NonNull
    @NotNull
    @NotEmpty
    @Email
    @Column(name = "user_email")
    private String email;

    @NonNull
    @NotNull
    @NotEmpty
    @Column(name = "profile_picture")
    private String profilePicture;

    @NonNull
    @NotNull
    @ManyToOne (targetEntity = UserRole.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role_id", nullable = false)
    private UserRole userRole;
}
