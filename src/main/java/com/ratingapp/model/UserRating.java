package com.ratingapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_ratings",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","shop_id"}, name = "uniqueUserRatingConstraint")})
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

    @Min(1)
    @Max(5)
    @Column(name = "rating")
    private int rating;

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

    @Size(min = 2)
    @Column(name = "picture")
    private String picture;

    @CreatedDate
    @PastOrPresent
    @Column(name = "date_created", updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    @PastOrPresent
    @Column(name = "date_modified")
    private LocalDateTime modified;

}