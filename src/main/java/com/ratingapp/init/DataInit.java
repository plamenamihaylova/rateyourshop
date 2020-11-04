package com.ratingapp.init;

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
    public void run(String... args) throws Exception {

        List<Category> defaultCategories = Arrays.asList(
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
                new Category("Jewelry"));
        if (categoryService.count() == 0){
            defaultCategories.forEach(categoryService::createCategory);
        }

        List<UserRole> defaultUserRoles = Arrays.asList(
                new UserRole(UserRole.USER),
                new UserRole(UserRole.ADMIN)
        );
        if (userRoleService.count() == 0){
            defaultUserRoles.forEach(userRoleService::createUserRole);
        }

        List<User> defaultUsers = Arrays.asList(
                new User("john",
                        "admin007",
                        "John",
                        "Smith",
                        "john.smith@gmail.com",
                        "https://grandimageinc.com/wp-content/uploads/2015/09/icon-user-default.png",
                        //new HashSet<>(Arrays.asList(Roles.ADMIN))),
                        //new HashSet<>(Arrays.asList(defaultUserRoles.get(1)))),
                        userRoleService.findByNameIgnoreCase(UserRole.ADMIN)),
                new User("stan",
                        "registration",
                        "Stan",
                        "Satan",
                        "stan.satan@gmail.com",
                        "https://grandimageinc.com/wp-content/uploads/2015/09/icon-user-default.png",
                        //new HashSet<>(Arrays.asList(Roles.REGISTERED))),
                        //new HashSet<>(Arrays.asList(defaultUserRoles.get(1)))),
                        userRoleService.findByNameIgnoreCase(UserRole.USER)),
                new User("jenifer",
                        "registration",
                        "Jenifer",
                        "Wood",
                        "jenifer.wood@gmail.com",
                        "https://grandimageinc.com/wp-content/uploads/2015/09/icon-user-default.png",
                        //new HashSet<UserRole>(Arrays.asList(new UserRole(UserRole.ROLE_USER))))
                        userRoleService.findByNameIgnoreCase(UserRole.USER))
        );


        if (userService.count() == 0){
            defaultUsers.forEach(userService::createUser);
        }
    }
}