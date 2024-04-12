package com.agap.management.exceptions.personalizedException;

public class VerificationTokenAlreadyExpiredException extends RuntimeException {

    public static String BASE = "Verification link has expired. A new verification email has been sent to %s";

    public VerificationTokenAlreadyExpiredException(String email) {
        super(String.format(BASE, email));
    }
}