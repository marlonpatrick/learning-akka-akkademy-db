package com.akkademy.messages;

import java.io.Serializable;

public class RemoveRequest implements Serializable{

    public final String key;

    public RemoveRequest(String key) {
        this.key = key;
    }
}
