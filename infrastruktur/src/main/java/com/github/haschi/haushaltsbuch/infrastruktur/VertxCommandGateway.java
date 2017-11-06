package com.github.haschi.haushaltsbuch.infrastruktur;

import org.axonframework.messaging.annotation.MetaDataValue;

import java.util.concurrent.CompletableFuture;

public interface VertxCommandGateway
{
    <T> CompletableFuture<Object> send(
            Object anweisung,
            @MetaDataValue("threadId") final long threadId);
}
