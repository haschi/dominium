package com.github.haschi.haushaltsbuch.infrastruktur

import com.github.haschi.domain.haushaltsbuch.modell.core.queries.LeseInventar
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Inventar

interface InvenarQueryGateway
{
    fun send(abfrage: LeseInventar): Inventar
}