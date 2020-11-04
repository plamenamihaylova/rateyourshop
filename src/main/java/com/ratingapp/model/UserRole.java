package com.ratingapp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "user_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements Serializable {

    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name="user_role_sequence",
            sequenceName = "user_role_sequence",
            allocationSize = 3
    )
    @Column(name = "user_role_id")
    private Long id;

    @NonNull
    @NotNull
    @Column(name = "user_role_name")
    private String name;

    public UserRole (String name) {
        this.name = name;
    }

}
