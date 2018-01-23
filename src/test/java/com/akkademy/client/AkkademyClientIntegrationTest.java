package com.akkademy.client;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

public class AkkademyClientIntegrationTest {


    @Test
    public void itShouldSetRecord() throws Exception {
        AkkademyClient client = new AkkademyClient("127.0.0.1:2552");
        client.set("123", 123);
        Integer result = (Integer) ((CompletableFuture<Object>) client.get("123")).get();
        assert (result == 123);
    }
}
