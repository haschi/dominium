package com.github.haschi.haushaltsbuch.infrastruktur

import com.github.haschi.dominium.haushaltsbuch.core.model.Zeit
import java.time.LocalDateTime

object SystemZeit : Zeit
{
    override var jetzt = LocalDateTime.now()
        get() = LocalDateTime.now()
}