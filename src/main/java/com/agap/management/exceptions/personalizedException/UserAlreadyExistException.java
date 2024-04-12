package com.agap.management.exceptions.personalizedException;

public class UserAlreadyExistException extends RuntimeException {

    public static String BASE = "User already exists with email %s";

    public UserAlreadyExistException(String email) {
        super(String.format(BASE, email));
    }
}
