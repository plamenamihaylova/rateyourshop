package com.ratingapp.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewerDTO {

    private Long id;

    @NonNull
    @NotNull
    @Size(min = 2, max = 50)
    private String username;

    @NonNull
    @NotNull
    private String profilePicture;

}
