package com.github.haschi.dominium.haushaltsbuch.core.application

import com.github.haschi.dominium.haushaltsbuch.core.domain.Historie
import com.github.haschi.dominium.haushaltsbuch.core.domain.InventarProjektion
import com.github.haschi.dominium.haushaltsbuch.core.domain.bilanz.BilanzAblage
import com.github.haschi.dominium.haushaltsbuch.core.domain.bilanz.BilanzEventHandler
import com.github.haschi.dominium.haushaltsbuch.core.domain.bilanz.BilanzQueryHandler
import com.github.haschi.dominium.haushaltsbuch.core.domain.inventur.GruppenQueryHandler
import com.github.haschi.dominium.haushaltsbuch.core.model.Bilanz
import com.github.haschi.dominium.haushaltsbuch.core.model.Haushaltsbuch
import com.github.haschi.dominium.haushaltsbuch.core.model.Inventur
import com.github.haschi.dominium.haushaltsbuch.core.model.InventurSaga
import org.axonframework.config.Configuration
import org.axonframework.config.DefaultConfigurer
import org.axonframework.config.EventHandlingConfiguration
import org.axonframework.config.SagaConfiguration

open class Anwendungskonfiguration(private val infrastruktur: Infrastrukturfabrik)
{
    private val eh = EventHandlingConfiguration().registerEventHandler { config -> BilanzEventHandler(config.getComponent(BilanzAblage::class.java)) }
    private val konfiguration: Configuration = DefaultConfigurer.defaultConfiguration()
            .configureCommandBus(infrastruktur::commandBus)
            .configureQueryBus(infrastruktur::queryBus)
            .configureAggregate(Inventur::class.java)
            .configureAggregate(Haushaltsbuch::class.java)
            .configureAggregate(Bilanz::class.java)
            .registerComponent(Historie::class.java, infrastruktur::historie)
            .registerComponent(BilanzAblage::class.java) { BilanzAblage() }
            .registerQueryHandler { InventarProjektion(it.getComponent(Historie::class.java)) }
            .registerQueryHandler { BilanzQueryHandler(it.getComponent(BilanzAblage::class.java)) }
            .registerQueryHandler { GruppenQueryHandler() }
            .configureEventStore(infrastruktur::eventStore)
            .registerModule(SagaConfiguration.subscribingSagaManager(InventurSaga::class.java))
            .registerModule(eh)
            .buildConfiguration()

    fun start(builder: (Configuration) -> Anwendung): Anwendung
    {
        konfiguration.start()
        return builder(konfiguration)
    }
}




