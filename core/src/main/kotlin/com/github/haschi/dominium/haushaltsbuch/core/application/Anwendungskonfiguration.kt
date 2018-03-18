package com.github.haschi.dominium.haushaltsbuch.core.application

import com.github.haschi.dominium.haushaltsbuch.core.domain.Historie
import com.github.haschi.dominium.haushaltsbuch.core.domain.InventarProjektion
import com.github.haschi.dominium.haushaltsbuch.core.model.Haushaltsbuch
import com.github.haschi.dominium.haushaltsbuch.core.model.Inventur
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.SimpleCommandBus
import org.axonframework.config.Configuration
import org.axonframework.config.Configurer
import org.axonframework.config.DefaultConfigurer
import java.util.function.BiFunction

open class Anwendungskonfiguration(private val infrastruktur: Infrastrukturfabrik)
{

    private val konfiguration: Configuration = DefaultConfigurer.defaultConfiguration()
            .configureLogger()
            .configureCommandBus(infrastruktur::commandBus)
            .configureAggregate(Inventur::class.java)
            .configureAggregate(Haushaltsbuch::class.java)
            .registerComponent(Historie::class.java, infrastruktur::historie)
            .registerQueryHandler { InventarProjektion(it.getComponent(Historie::class.java)) }
            .configureEventStore(infrastruktur::eventStore)

            .buildConfiguration()

    private fun Configurer.configureLogger(): Configurer
    {
        return this.configureMessageMonitor(
                { configuration ->
                    BiFunction { comp: Class<*>, name: String ->
                        ({ componentType: Class<*>, n: String ->
                            infrastruktur.loggingMonitor(configuration, n)
                        })(comp, name)
                    }
                })
    }

    fun start(builder: (Configuration) -> Anwendung): Anwendung
    {
        konfiguration.start()
        return builder(konfiguration)
    }
}




