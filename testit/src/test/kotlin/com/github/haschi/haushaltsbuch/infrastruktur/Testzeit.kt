package com.github.haschi.haushaltsbuch.infrastruktur

import com.github.haschi.dominium.haushaltsbuch.core.model.Zeit
import java.time.LocalDateTime

object Testzeit : Zeit
{
    override var jetzt: LocalDateTime = LocalDateTime.now()
}
