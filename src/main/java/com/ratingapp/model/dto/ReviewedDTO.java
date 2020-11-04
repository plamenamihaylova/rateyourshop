package com.ratingapp.model.dto;

import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ReviewedDTO {

    @NonNull
    @NotNull
    @NotEmpty
    private String shopName;

    @NonNull
    @NotNull
    private String categoryName;

    @NonNull
    @NotNull
    @NotEmpty
    @URL
    private String url;

//    private Double ratingAverage;
//
//    private Integer ratingsCount;
}
