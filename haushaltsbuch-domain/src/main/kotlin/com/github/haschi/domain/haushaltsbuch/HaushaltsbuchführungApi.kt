package com.github.haschi.domain.haushaltsbuch

import com.github.haschi.domain.haushaltsbuch.modell.core.commands.BeginneHaushaltsbuchführung
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import java.util.concurrent.CompletableFuture

interface HaushaltsbuchführungApi
{
    fun send(anweisung: BeginneHaushaltsbuchführung): CompletableFuture<Aggregatkennung>
}