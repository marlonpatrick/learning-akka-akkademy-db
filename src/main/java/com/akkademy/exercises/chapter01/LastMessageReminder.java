package com.akkademy.exercises.chapter01;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.akkademy.message.SetRequest;

import java.util.HashMap;
import java.util.Map;

public class LastMessageReminder extends AbstractActor {

    protected final LoggingAdapter log = Logging.getLogger(context().system(), this);

    private String lastMessage;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(StoreMessageRequest.class, message -> lastMessage = message.getMessage())
                .matchAny(o -> log.info("received unknown message {}", o))
                .build();
    }

    public String getLastMessage() {
        return lastMessage;
    }
}