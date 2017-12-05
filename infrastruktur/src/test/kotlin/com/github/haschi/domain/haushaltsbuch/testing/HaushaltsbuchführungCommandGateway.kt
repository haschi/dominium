package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.domain.haushaltsbuch.modell.core.commands.BeginneHaushaltsbuchführung
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import java.util.concurrent.CompletableFuture

interface HaushaltsbuchführungCommandGateway
{
    fun send(anweisung: BeginneHaushaltsbuchführung): CompletableFuture<Aggregatkennung>
}