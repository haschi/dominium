package com.github.haschi.haushaltsbuch.infrastruktur

import org.axonframework.messaging.annotation.MetaDataValue

import java.util.concurrent.CompletableFuture

interface VertxCommandGateway
{
    fun <T> send(
            anweisung: Any,
            @MetaDataValue("threadId") threadId: Long): CompletableFuture<T>
}
