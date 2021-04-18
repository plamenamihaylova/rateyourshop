package com.ratingapp.enums;

import com.ratingapp.model.UserRole;

public class Enums {

    public class Category {
        public final static String FOOD = "Food";
        public final static String FURNITURE = "Furniture";
        public final static String HOME_UTILITIES = "Home utilities";
        public final static String TECHNICAL_SUPPLIES = "Technical supplies";
        public final static String COMPUTERS = "Computers";
        public final static String PHONE_ACCESSORIES = "Phone accessories";
        public final static String BOOKS = "Books";
        public final static String SHOES = "Shoes";
        public final static String CLOTHES = "Clothes";
        public final static String JEWELRY = "Jewelry";
        public final static String ONLINE_SERVICES = "Online services";
        public final static String ONLINE_COURSES = "Online courses";
        public final static String COURSES = "Courses";
        public final static String GAMES = "Games";
    }

    public class User {
        public final static String DEFAULT_PROFILE_PICTURE = "https://grandimageinc.com/wp-content/uploads/2015/09/icon-user-default.png";
        public final static String USER_ROLE_ADMIN = UserRole.ADMIN;
        public final static String USER_ROLE_USER = UserRole.USER;
    }

}
