package com.akkademy.server;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

public class StartupAkkademyDb {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("akkademy", ConfigFactory.load("server-application.conf"));

        system.actorOf(Props.create(AkkademyDb.class), "akkademy-db");
    }
}
