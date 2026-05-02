package com.pouya.library.Execptions;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}