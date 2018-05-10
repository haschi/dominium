package com.github.haschi.dominium.haushaltsbuch.core.domain.bilanz

import com.github.haschi.dominium.haushaltsbuch.core.model.events.PrivateEröffnungsbilanzVorgeschlagen
import org.axonframework.eventhandling.EventHandler

class BilanzEventHandler(val ablage: BilanzAblage)
{
    @EventHandler
    fun falls(ereignis: PrivateEröffnungsbilanzVorgeschlagen)
    {
        ablage.speichern(ereignis.inventurId, ereignis.bilanz)
    }
}