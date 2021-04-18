package com.ratingapp.init;

import com.ratingapp.enums.Enums;
import com.ratingapp.model.*;
import com.ratingapp.service.CategoryService;
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

    private final CategoryService categoryService;
    private final UserRoleService userRoleService;
    private final UserService userService;

    @Autowired
    public DataInit(CategoryService categoryService,
                    UserRoleService userRoleService,
                    UserService userService){
        this.categoryService = categoryService;
        this.userRoleService = userRoleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {

        addDefaultCategories();

        addDefaultUserRoles();

        addDefaultUsers();
    }

    private void addDefaultCategories() {
        List<Category> defaultCategories = generateDefaultCategories();

        if (categoryService.count() == 0){
            defaultCategories.forEach(categoryService::createCategory);
        }
    }

    private List<Category> generateDefaultCategories() {
        return Arrays.asList(
                new Category(Enums.Category.FOOD),
                new Category(Enums.Category.FURNITURE),
                new Category(Enums.Category.HOME_UTILITIES),
                new Category(Enums.Category.TECHNICAL_SUPPLIES),
                new Category(Enums.Category.COMPUTERS),
                new Category(Enums.Category.PHONE_ACCESSORIES),
                new Category(Enums.Category.BOOKS),
                new Category(Enums.Category.SHOES),
                new Category(Enums.Category.CLOTHES),
                new Category(Enums.Category.JEWELRY),
                new Category(Enums.Category.ONLINE_SERVICES),
                new Category(Enums.Category.ONLINE_COURSES),
                new Category(Enums.Category.COURSES),
                new Category(Enums.Category.GAMES)
        );
    }

    private void addDefaultUserRoles() {
        List<UserRole> defaultUserRoles = generateDefaultUserRoles();

        if (userRoleService.count() == 0){
            defaultUserRoles.forEach(userRoleService::createUserRole);
        }
    }

    private List<UserRole>  generateDefaultUserRoles() {
        return Arrays.asList(
                new UserRole(Enums.User.USER_ROLE_ADMIN),
                new UserRole(Enums.User.USER_ROLE_USER)
        );
    }

    private void addDefaultUsers() {
        List<User> defaultUsers = generateDefaultUsers();

        if (userService.count() == 0) {
            defaultUsers.forEach(userService::createUser);
        }
    }

    private List<User> generateDefaultUsers() {
        return Arrays.asList(
                new User("john",
                        "admin007",
                        "John",
                        "Smith",
                        "john.smith@gmail.com",
                        Enums.User.DEFAULT_PROFILE_PICTURE,
                        userRoleService.findByNameIgnoreCase(Enums.User.USER_ROLE_ADMIN)),
                new User("stan",
                        "registration",
                        "Stan",
                        "Doe",
                        "stan.doe@gmail.com",
                        Enums.User.DEFAULT_PROFILE_PICTURE,
                        userRoleService.findByNameIgnoreCase(Enums.User.USER_ROLE_USER)),
                new User("jenifer",
                        "registration",
                        "Jenifer",
                        "Wood",
                        "jenifer.wood@gmail.com",
                        Enums.User.DEFAULT_PROFILE_PICTURE,
                        userRoleService.findByNameIgnoreCase(Enums.User.USER_ROLE_USER))
        );
    }
}