package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.domain.haushaltsbuch.modell.core.commands.BeginneHaushaltsbuchführung
import java.util.concurrent.CompletableFuture

interface HaushaltsbuchführungCommandGateway
{
    fun send(anweisung: BeginneHaushaltsbuchführung): CompletableFuture<Any>
}