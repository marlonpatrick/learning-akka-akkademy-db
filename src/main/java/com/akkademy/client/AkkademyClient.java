package com.akkademy.client;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.akkademy.messages.GetRequest;
import com.akkademy.messages.SetRequest;
import com.typesafe.config.ConfigFactory;

import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;

public class AkkademyClient {

    private final ActorSystem system = ActorSystem.create("akkademy-db-client", ConfigFactory.load("client-application.conf"));

    private final ActorSelection remoteDb;

    public AkkademyClient(String remoteAddress) {
        remoteDb = system.actorSelection("akka://akkademy@" + remoteAddress + "/user/akkademy-db");
    }

    public CompletionStage set(String key, Object value) {
        return toJava(ask(remoteDb, new SetRequest(key, value), 2000));
    }

    public CompletionStage<Object> get(String key) {
        return toJava(ask(remoteDb, new GetRequest(key), 2000));
    }
}
