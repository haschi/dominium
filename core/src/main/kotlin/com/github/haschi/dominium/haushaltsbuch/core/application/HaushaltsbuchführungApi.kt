package com.github.haschi.dominium.haushaltsbuch.core.application

import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneHaushaltsbuchführung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import java.util.concurrent.CompletableFuture

interface HaushaltsbuchführungApi
{
    fun send(anweisung: BeginneHaushaltsbuchführung): CompletableFuture<Aggregatkennung>
}