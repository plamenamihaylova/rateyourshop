package com.ratingapp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "rating_sequence",
            sequenceName = "rating_sequence",
            allocationSize = 3
    )
    @Column(name = "rating_id")
    private Long id;

    @Min(1)
    @Max(5)
    @NonNull
    @NotNull
    @Column(name = "rating_value")
    private int value;

}
