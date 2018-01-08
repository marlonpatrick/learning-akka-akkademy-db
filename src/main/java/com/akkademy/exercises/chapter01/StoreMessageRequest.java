package com.akkademy.exercises.chapter01;

public class StoreMessageRequest {

    private final String message;

    public StoreMessageRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
