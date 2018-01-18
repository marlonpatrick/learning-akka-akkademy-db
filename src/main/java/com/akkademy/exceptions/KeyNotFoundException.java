package com.akkademy.exceptions;

public class KeyNotFoundException extends RuntimeException {

    public final String key;

    public KeyNotFoundException(String key) {
        this.key = key;
    }

}
