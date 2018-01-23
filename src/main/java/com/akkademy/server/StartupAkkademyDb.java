package com.akkademy.server;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.akkademy.messages.SetRequest;
import com.typesafe.config.ConfigFactory;

public class StartupAkkademyDb {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("akkademy", ConfigFactory.load("server-application.conf"));

        ActorRef local = system.actorOf(Props.create(AkkademyDb.class), "akkademy-db");

        System.out.println(local);

        local.tell(new SetRequest("minhaChaveLocal","meuValorLocal"), ActorRef.noSender());

        ActorSelection remote = system.actorSelection("akka://akkademy@127.0.0.1:2552/user/akkademy-db");

        System.out.println(local.isTerminated());

        System.out.println(remote);

        remote.tell(new SetRequest("minhaChaveRemota","meuValorRemoto"), ActorRef.noSender());
    }
}
