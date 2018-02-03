package com.akkademy.exceptions;

public class InvalidValueException extends RuntimeException {

    public final String key;

    public InvalidValueException(String key) {
        this.key = key;
    }

}
