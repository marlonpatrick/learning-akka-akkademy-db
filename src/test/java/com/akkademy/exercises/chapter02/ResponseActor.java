package com.akkademy.exercises.chapter02;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.akkademy.exercises.chapter01.StoreMessageRequest;

public class ResponseActor extends AbstractActor {

    protected final LoggingAdapter log = Logging.getLogger(context().system(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> sender().tell(new StringBuilder(message).reverse().toString(), ActorRef.noSender()))
                .matchAny(o -> log.info("received unknown message {}", o))
                .build();
    }
}