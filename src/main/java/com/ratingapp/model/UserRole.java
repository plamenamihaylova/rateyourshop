package com.ratingapp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class UserRole {

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
}
