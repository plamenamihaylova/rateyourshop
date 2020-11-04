package com.ratingapp.model.dto;

import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long id;

    @NonNull
    @NotNull
    @Size(min = 3)
    private String name;
}
