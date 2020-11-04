package com.ratingapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRatingDTO {

    private Long id;

    @NonNull
    @NotNull
    private ShopDTO shop;

    @Min(1)
    @Max(5)
    private int rating;

    @NonNull
    @NotNull
    private UserDTO user;

    @NonNull
    @NotNull
    @Size(min = 20, max = 2048)
    private String comment;

    @NonNull
    @NotNull
    @Size(min = 20)
    private String receipt;

    @Size(min = 2)
    private String picture;

    @CreatedDate
    @PastOrPresent
    private LocalDateTime created;

    @LastModifiedDate
    @PastOrPresent
    private LocalDateTime modified;

}