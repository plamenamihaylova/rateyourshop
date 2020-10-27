package com.ratingapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRating {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "user_rating_sequence",
            sequenceName = "user_rating_sequence",
            allocationSize = 3
    )
    @Column(name = "user_rating_id")
    private Long id;

    @ManyToOne (targetEntity = Shop.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToOne (targetEntity = Rating.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "rating_id", nullable = false)
    private Rating rating;

    @ManyToOne (targetEntity = User.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NonNull
    @NotNull
    @Size(min = 20, max = 2048)
    @Column(name = "comment")
    private String comment;

    @NonNull
    @NotNull
    @Size(min = 20)
    @Column(name = "receipt")
    private String receipt;

    @Column(name = "picture")
    private String picture;

    @PastOrPresent
    @Column(name = "date_created")
    private LocalDateTime created = LocalDateTime.now();

    @PastOrPresent
    @Column(name = "date_modified")
    private LocalDateTime modified = LocalDateTime.now();

}