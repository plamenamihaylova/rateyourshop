package com.ratingapp.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name="category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 3
    )
    @Column(name = "category_id")
    private Long id;

    @NonNull
    @NotNull
    @Size(min = 3)
    @Column(name = "category_name")
    private String name;
}
