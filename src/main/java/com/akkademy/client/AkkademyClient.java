package com.akkademy.client;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import com.akkademy.messages.GetRequest;
import com.akkademy.messages.RemoveRequest;
import com.akkademy.messages.SetIfNotExistsRequest;
import com.akkademy.messages.SetRequest;
import com.typesafe.config.ConfigFactory;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;

public class AkkademyClient {

    private final ActorSystem system = ActorSystem.create("akkademy-db-client", ConfigFactory.load("client-application.conf"));

    private final ActorSelection remoteDb;

    public AkkademyClient(String remoteAddress) {
        remoteDb = system.actorSelection("akka://akkademy@" + remoteAddress + "/user/akkademy-db");
    }

    public CompletionStage set(String key, Object value) {
        return toJava(ask(remoteDb, new SetRequest(key, value), new Timeout(2000, TimeUnit.MILLISECONDS)));
    }

    public CompletionStage setIfNotExists(String key, Object value) {
        return toJava(ask(remoteDb, new SetIfNotExistsRequest(key, value), new Timeout(2000, TimeUnit.MILLISECONDS)));
    }

    public CompletionStage remove(String key) {
        return toJava(ask(remoteDb, new RemoveRequest(key), new Timeout(2000, TimeUnit.MILLISECONDS)));
    }

    public CompletionStage<Object> get(String key) {
        return toJava(ask(remoteDb, new GetRequest(key), new Timeout(2000, TimeUnit.MILLISECONDS)));
    }
}
