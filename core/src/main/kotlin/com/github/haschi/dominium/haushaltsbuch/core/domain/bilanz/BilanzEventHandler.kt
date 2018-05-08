package com.github.haschi.dominium.haushaltsbuch.core.domain.bilanz

import com.github.haschi.dominium.haushaltsbuch.core.model.events.PrivateEröffnungsbilanzVorgeschlagen
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import org.axonframework.eventhandling.EventHandler
import org.axonframework.messaging.MetaData

class BilanzEventHandler(val ablage: BilanzAblage)
{
    @EventHandler
    fun falls(ereignis: PrivateEröffnungsbilanzVorgeschlagen)
    {
        ablage.speichern(ereignis.inventurId, ereignis.bilanz)
    }
}