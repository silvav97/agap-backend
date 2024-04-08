package com.agap.management.exceptions.personalizedException;

public class UserAlreadyExistException extends RuntimeException {

    public static String BASE = "User already exists with email: ";

    public UserAlreadyExistException(String message) {
        super(BASE + message);
    }
}
