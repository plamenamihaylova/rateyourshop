package com.ratingapp.model.dto;

import lombok.*;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ShopDTO {

    private Long id;

    @NonNull
    @NotNull
    @NotEmpty
    private String shopName;

    @NonNull
    @NotNull
    private CategoryDTO category;

    @NonNull
    @NotNull
    @NotEmpty
    @URL
    private String url;

    @URL
    @Size(min = 2)
    private String logo;

    @Size(min = 2)
    private String phone;

    @Email
    @Size(min = 5)
    private String email;

    @Formula("(SELECT AVG(r.rating) FROM shops s " +
                    "JOIN user_ratings r ON r.shop_id = s.shop_id " +
                    "WHERE r.shop_id = shop_id)")
    private Double ratingAverage;

    @Formula("(SELECT COUNT(r.rating) FROM shops s " +
            "JOIN user_ratings r ON r.shop_id = s.shop_id " +
            "WHERE r.shop_id = shop_id)")
    private Integer ratingsCount;
}
