package com.github.haschi.dominium.haushaltsbuch.core.application

import com.github.haschi.dominium.haushaltsbuch.core.domain.Historie
import com.github.haschi.dominium.haushaltsbuch.core.domain.InventarProjektion
import com.github.haschi.dominium.haushaltsbuch.core.domain.bilanz.BilanzQueryHandler
import com.github.haschi.dominium.haushaltsbuch.core.model.Haushaltsbuch
import com.github.haschi.dominium.haushaltsbuch.core.model.Inventur
import org.axonframework.config.Configuration
import org.axonframework.config.DefaultConfigurer

open class Anwendungskonfiguration(private val infrastruktur: Infrastrukturfabrik)
{
    private val konfiguration: Configuration = DefaultConfigurer.defaultConfiguration()
            .configureCommandBus(infrastruktur::commandBus)
            .configureQueryBus(infrastruktur::queryBus)
            .configureAggregate(Inventur::class.java)
            .configureAggregate(Haushaltsbuch::class.java)
            .registerComponent(Historie::class.java, infrastruktur::historie)
            .registerQueryHandler { InventarProjektion(it.getComponent(Historie::class.java)) }
            .registerQueryHandler { BilanzQueryHandler() }
            .configureEventStore(infrastruktur::eventStore)

            .buildConfiguration()

    fun start(builder: (Configuration) -> Anwendung): Anwendung
    {
        konfiguration.start()
        return builder(konfiguration)
    }
}




