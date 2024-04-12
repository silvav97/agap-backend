package com.agap.management.exceptions.personalizedException;

public class ChangePasswordException extends RuntimeException {

    public ChangePasswordException(String message) {
        super(message);
    }
}