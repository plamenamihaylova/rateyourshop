package com.ratingapp.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewerDTO {

    @NonNull
    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;

    @NonNull
    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;

    @NonNull
    @NotNull
    private String profilePicture;

}
