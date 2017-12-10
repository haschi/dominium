package com.github.haschi.dominium.haushaltsbuch.core.application

import com.github.haschi.domain.haushaltsbuch.modell.core.commands.BeginneInventur
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.ErfasseInventar
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.BeendeInventur
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import java.util.concurrent.CompletableFuture

interface InventurApi
{
    fun send(anweisung: BeginneInventur): CompletableFuture<Aggregatkennung>
    fun send(anweisung: ErfasseInventar): CompletableFuture<Any>
    fun send(anweisung: BeendeInventur): CompletableFuture<Any>
}
