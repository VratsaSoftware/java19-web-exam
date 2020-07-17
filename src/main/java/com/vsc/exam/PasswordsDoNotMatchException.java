package com.vsc.exam;

public class PasswordsDoNotMatchException extends Exception {

    public PasswordsDoNotMatchException(String message) {
        super(message);
    }
}
