package com.akkademy.server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import com.akkademy.messages.RemoveRequest;
import com.akkademy.messages.SetIfNotExistsRequest;
import com.akkademy.messages.SetRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AkkademyDbTest {

    private ActorSystem system = ActorSystem.create();

    @Test
    public void itShouldPlaceKeyValueFromSetMessageIntoMap() {
        TestActorRef<AkkademyDb> actorRef = TestActorRef.create(system, Props.create(AkkademyDb.class));

        actorRef.tell(new SetRequest("key", "value"), ActorRef.noSender());


        AkkademyDb akkademyDb = actorRef.underlyingActor();

        assertEquals(akkademyDb.map.get("key"), "value");
    }

    @Test
    public void itShouldNotSetIfValueAlready() {
        TestActorRef<AkkademyDb> actorRef = TestActorRef.create(system, Props.create(AkkademyDb.class));

        actorRef.tell(new SetRequest("key", "value1"), ActorRef.noSender());

        actorRef.tell(new SetIfNotExistsRequest("key", "value2"), ActorRef.noSender());

        AkkademyDb akkademyDb = actorRef.underlyingActor();

        assertEquals(akkademyDb.map.get("key"), "value1");
    }

    @Test
    public void itShouldRemoveExistingValue() {
        TestActorRef<AkkademyDb> actorRef = TestActorRef.create(system, Props.create(AkkademyDb.class));

        actorRef.tell(new SetRequest("key", "value1"), ActorRef.noSender());

        actorRef.tell(new RemoveRequest("key"), ActorRef.noSender());

        AkkademyDb akkademyDb = actorRef.underlyingActor();

        assertNull(akkademyDb.map.get("key"));
    }
}
