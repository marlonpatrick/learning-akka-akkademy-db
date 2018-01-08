package com.akkademy.chapter01;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import com.akkademy.exercises.chapter01.LastMessageReminder;
import com.akkademy.exercises.chapter01.StoreMessageRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LastMessageReminderTest  {

    private ActorSystem system = ActorSystem.create();

    @Test
    public void itShouldStoreMessageFromStoreMessage() {
        TestActorRef<LastMessageReminder> actorRef = TestActorRef.create(system, Props.create(LastMessageReminder.class));

        actorRef.tell(new StoreMessageRequest("Some message"), ActorRef.noSender());

        LastMessageReminder lastMessageReminder = actorRef.underlyingActor();

        assertEquals(lastMessageReminder.getLastMessage(), "Some message");
    }

    @Test
    public void itShouldStoreOnlyLastMessageMessageFromStoreMessage() {
        TestActorRef<LastMessageReminder> actorRef = TestActorRef.create(system, Props.create(LastMessageReminder.class));

        actorRef.tell(new StoreMessageRequest("First message"), ActorRef.noSender());
        actorRef.tell(new StoreMessageRequest("Second message"), ActorRef.noSender());

        LastMessageReminder lastMessageReminder = actorRef.underlyingActor();

        assertEquals(lastMessageReminder.getLastMessage(), "Second message");
    }
}