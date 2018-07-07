package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.dominium.haushaltsbuch.core.model.Zeit
import java.time.LocalDateTime

object Testzeit : Zeit
{
    override var jetzt: LocalDateTime = LocalDateTime.now()
}
