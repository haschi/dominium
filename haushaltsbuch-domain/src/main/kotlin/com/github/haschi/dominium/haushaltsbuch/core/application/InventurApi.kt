package com.github.haschi.dominium.haushaltsbuch.core.application

import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.ErfasseInventar
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeendeInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import java.util.concurrent.CompletableFuture

interface InventurApi
{
    fun send(anweisung: BeginneInventur): CompletableFuture<Aggregatkennung>
    fun send(anweisung: ErfasseInventar): CompletableFuture<Any>
    fun send(anweisung: BeendeInventur): CompletableFuture<Any>
}
