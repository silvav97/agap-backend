package com.agap.management.exceptions.personalizedException;

public class UserNotEnabledYetException extends RuntimeException {

    public static String BASE = "User with email %s is not enabled yed";

    public UserNotEnabledYetException(String email) {
        super(String.format(BASE, email));
    }

}
