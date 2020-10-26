package com.ratingapp.init;

import com.ratingapp.model.*;
import com.ratingapp.repository.UserRepository;
import com.ratingapp.service.CategoryService;
import com.ratingapp.service.RatingService;
import com.ratingapp.service.UserRoleService;
import com.ratingapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DataInit implements CommandLineRunner{

    private RatingService ratingService;
    private CategoryService categoryService;
    private UserRoleService userRoleService;
    private UserService userService;

    @Autowired
    public DataInit(RatingService ratingService, CategoryService categoryService, UserRoleService userRoleService, UserService userService){
        this.ratingService = ratingService;
        this.categoryService = categoryService;
        this.userRoleService = userRoleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Rating> ratings = Arrays.asList(
                new Rating[]{
                        new Rating(1),
                        new Rating(2),
                        new Rating(3),
                        new Rating(4),
                        new Rating(5)
                }
        );
        if (ratingService.count() == 0){
            ratings.stream().forEach(rating -> ratingService.createRating(rating));
        }

        List<Category> defaultCategories = Arrays.asList(
                new Category[]{
                        new Category("Food"),
                        new Category("Furniture"),
                        new Category("Home utilities"),
                        new Category("Technical supplies"),
                        new Category("Computers"),
                        new Category("Phone accessories"),
                        new Category("Book store"),
                        new Category("Shoes"),
                        new Category("Clothes"),
                        new Category("Online Service"),
                        new Category("Online Course"),
                        new Category("Course"),
                        new Category("Games"),
                        new Category("Jewelry"),

                }
        );
        if (categoryService.count() == 0){
            defaultCategories.stream().forEach(category -> categoryService.createCategory(category));
        }

        List<UserRole> defaultUserRoles = Arrays.asList(
                new UserRole[]{
                        new UserRole(Roles.ADMIN),
                        new UserRole(Roles.REGISTERED),
                        new UserRole(Roles.ANONYMOUS)
                }
        );
        if (userRoleService.count() == 0){
            defaultUserRoles.stream().forEach(userRole -> userRoleService.createUserRole(userRole));
        }

        List<User> defaultUsers = Arrays.asList(
                new User[]{
                        new User("john", "john.smith@gmail.com","https://grandimageinc.com/wp-content/uploads/2015/09/icon-user-default.png", userRoleService.findByName(Roles.ADMIN)),
                        new User("stan", "stan.satan@gmail.com","https://grandimageinc.com/wp-content/uploads/2015/09/icon-user-default.png", userRoleService.findByName(Roles.REGISTERED)),
                        new User("sam", "sam.smith@gmail.com","https://grandimageinc.com/wp-content/uploads/2015/09/icon-user-default.png", userRoleService.findByName(Roles.ANONYMOUS)),
                        new User("jenifer", "jenifer.wood@gmail.com","https://grandimageinc.com/wp-content/uploads/2015/09/icon-user-default.png", userRoleService.findByName(Roles.REGISTERED)),
                }
        );
        if (userService.count() == 0){
            defaultUsers.stream().forEach(user -> userService.createUser(user));
        }
    }
}