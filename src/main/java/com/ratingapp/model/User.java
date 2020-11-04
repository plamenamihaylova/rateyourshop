package com.ratingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.*;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Entity
@Table(name = "users",
        uniqueConstraints = { @UniqueConstraint(columnNames = "username", name = "uniqueUsernameConstraint"),
                                @UniqueConstraint(columnNames = "user_email", name = "uniqueEmailConstraint")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "active", "authorities", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
public class User implements UserDetails {

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
    @Size(min = 2, max = 50)
    @Column(name = "username")
    private String username;

    @JsonProperty(access = WRITE_ONLY)
    @NonNull
    @NotNull
    @Size(min=3)
    @Column(name = "password")
    private String password;

    @NonNull
    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @NotNull
    @Size(min = 2, max = 50)
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

    private boolean active = true;

    @NonNull
    @NotNull
    @ManyToOne (targetEntity = UserRole.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role_id", nullable = false)
    private UserRole role;

    public User (@NonNull @NotNull @Size(min = 2, max = 50) String username,
                 @NonNull @NotNull @Size(min=3) String password,
                 @NonNull @NotNull @Size(min = 2, max = 50) String firstName,
                 @NonNull @NotNull @Size(min = 2, max = 50) String lastName,
                 @NonNull @NotNull @Size(min = 5) @Email String email,
                 @NonNull @NotNull @Size(min = 2) String profilePicture,
                 @NonNull @NotNull UserRole role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profilePicture = profilePicture;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

       List<UserRole> defaultUserRoles = Arrays.asList(
                new UserRole(UserRole.USER),
                new UserRole(UserRole.ADMIN)
        );
        return defaultUserRoles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
