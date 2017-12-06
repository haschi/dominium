package com.github.haschi.haushaltsbuch.infrastruktur

import com.github.haschi.domain.haushaltsbuch.modell.core.commands.BeginneHaushaltsbuchführung
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import java.util.concurrent.CompletableFuture

interface HaushaltsbuchführungCommandGateway
{
    fun send(anweisung: BeginneHaushaltsbuchführung): CompletableFuture<Aggregatkennung>
}