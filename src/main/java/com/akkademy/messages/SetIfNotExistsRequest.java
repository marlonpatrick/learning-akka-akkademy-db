package com.akkademy.messages;

import java.io.Serializable;

public class SetIfNotExistsRequest implements Serializable {

    public final String key;

    public final Object value;

    public SetIfNotExistsRequest(String key, Object value) {
        this.key = key;
        this.value = value;
    }


    @Override
    public String toString() {
        return "SetRequest{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
