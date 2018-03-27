package com.github.haschi.dominium.haushaltsbuch.core.domain.bilanz

import com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseEröffnungsbilanz
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Eröffnungsbilanz
import org.axonframework.queryhandling.QueryHandler

class BilanzQueryHandler
{
    @QueryHandler
    fun falls(abfrage: LeseEröffnungsbilanz): Eröffnungsbilanz
    {
        return Eröffnungsbilanz
    }
}