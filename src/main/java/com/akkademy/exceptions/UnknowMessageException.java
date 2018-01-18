package com.akkademy.exceptions;

public class UnknowMessageException extends RuntimeException{

    public final Object message;

    public UnknowMessageException(Object message){
        this.message = message;
    }
}
