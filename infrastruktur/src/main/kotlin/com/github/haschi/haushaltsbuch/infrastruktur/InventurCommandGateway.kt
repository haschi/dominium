package com.github.haschi.haushaltsbuch.infrastruktur

import com.github.haschi.domain.haushaltsbuch.modell.core.commands.BeginneInventur
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.ErfasseInventar
import com.github.haschi.domain.haushaltsbuch.modell.core.events.BeendeInventur
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import java.util.concurrent.CompletableFuture

interface InventurCommandGateway
{
    fun send(anweisung: BeginneInventur): CompletableFuture<Aggregatkennung>
    fun send(anweisung: ErfasseInventar): CompletableFuture<Any>
    fun send(anweisung: BeendeInventur): CompletableFuture<Any>
}
