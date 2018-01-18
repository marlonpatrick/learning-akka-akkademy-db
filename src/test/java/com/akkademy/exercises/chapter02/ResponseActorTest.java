package com.akkademy.exercises.chapter02;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public class ResponseActorTest {

    public static void main(String[] args) {

        ActorSystem system = ActorSystem.create();

        ActorRef actorRef = system.actorOf(Props.create(ResponseActor.class));

        Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));

        Future future = Patterns.ask(actorRef, "Some Message", timeout);

        CompletionStage<String> completionStage = FutureConverters.toJava(future);

        completionStage.thenApply(msg -> new StringBuilder(msg).reverse()).thenAccept(msg -> System.out.println(msg)).thenRun(() -> system.terminate());

        System.out.println("END");
    }
}