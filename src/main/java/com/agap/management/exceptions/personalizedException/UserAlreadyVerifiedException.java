package com.agap.management.exceptions.personalizedException;

public class UserAlreadyVerifiedException extends RuntimeException {

    public static String BASE = "Account with email %s is already verified";


    public UserAlreadyVerifiedException(String email) {
        super(String.format(BASE, email));
    }
}