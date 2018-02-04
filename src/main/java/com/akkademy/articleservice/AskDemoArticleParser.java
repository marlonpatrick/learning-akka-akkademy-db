package com.akkademy.articleservice;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.util.Timeout;
import com.akkademy.messages.GetRequest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;


public class AskDemoArticleParser extends AbstractActor {

    private final ActorSelection cacheActor;
    private final ActorSelection httpClientActor;
    private final ActorSelection artcileParseActor;
    private final Timeout timeout;

    public AskDemoArticleParser(String cacheActorPath, String httpClientActorPath, String artcileParseActorPath, Timeout timeout) {
        this.cacheActor = context().actorSelection(cacheActorPath);
        this.httpClientActor = context().actorSelection(httpClientActorPath);
        this.artcileParseActor = context().actorSelection(artcileParseActorPath);
        this.timeout = timeout;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(ParseArticle.class, msg -> {

                    final CompletionStage cacheResult = toJava(ask(cacheActor, new GetRequest(msg.url), timeout));

                    final CompletionStage result = cacheResult.handle((cacheResultValue, throwable) -> {

                        if (cacheResultValue == null) {
                            return toJava(ask(httpClientActor, msg.url, timeout)).
                                    thenCompose(rawArticle -> toJava(
                                            ask(artcileParseActor,
                                                    new ParseHtmlArticle(msg.url,
                                                            ((HttpResponse) rawArticle).body), timeout))
                                    );
                        }

                        return CompletableFuture.completedFuture(cacheResultValue);

                    }).thenCompose(x -> x);

                    final ActorRef senderRef = sender();

                    result.handle((resultValue, throwable) -> {

                        if (resultValue == null) {
                            senderRef.tell(new akka.actor.Status.Failure((Throwable) throwable), self());
                        }else{
                            if (resultValue instanceof ArticleBody) {
                                String body = ((ArticleBody) resultValue).body; //parsed article
                                cacheActor.tell(body, self()); //cache it
                                senderRef.tell(body, self()); //reply
                            } else if (resultValue instanceof String){ //cached article
                                senderRef.tell(resultValue, self());
                            }
                        }

                        return null;
                    });

                }).build();
    }
}