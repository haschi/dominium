package com.github.haschi.dominium.haushaltsbuch.core.application

import com.github.haschi.dominium.haushaltsbuch.core.domain.Historie
import com.github.haschi.dominium.haushaltsbuch.core.domain.InventarProjektion
import com.github.haschi.dominium.haushaltsbuch.core.model.Haushaltsbuch
import com.github.haschi.dominium.haushaltsbuch.core.model.Inventur
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.gateway.CommandGatewayFactory
import org.axonframework.config.Configuration
import org.axonframework.config.Configurer
import org.axonframework.config.DefaultConfigurer
import org.axonframework.messaging.Message
import org.axonframework.monitoring.MessageMonitor
import org.axonframework.queryhandling.QueryGateway
import java.util.function.BiFunction
import kotlin.reflect.KClass
import kotlin.reflect.KFunction1

class Anwendungskonfiguration(private val infrastruktur: Infrastrukturfabrik)
{
    private val commandbus: CommandBus get() = konfiguration.commandBus()

    private val queryGateway: QueryGateway get() = konfiguration.queryGateway()

    private val historie: Historie by lazy {
        konfiguration.getComponent(Historie::class.java)
    }

    private val konfiguration: Configuration = DefaultConfigurer.defaultConfiguration()
            .configureLogger(infrastruktur::loggingMonitor)
            .configureAggregate(Inventur::class.java)
            .configureAggregate(Haushaltsbuch::class.java)
            .registerComponent(Historie::class.java, infrastruktur::historie)
            .registerQueryHandler { InventarProjektion(it.getComponent(Historie::class.java)) }
            .configureEventStore(infrastruktur::eventstore)

            .buildConfiguration()

    private fun Configurer.configureLogger(logger: KFunction1<@ParameterName(name = "konfiguration") Configuration, MessageMonitor<*>>): Configurer
    {
        this.configureMessageMonitor(
                { configuration ->
                    BiFunction { comp: Class<*>, name: String ->
                        ({ componentType: Class<*>, name: String ->
                            infrastruktur.loggingMonitor(configuration)
//                            when (componentType)
//                            {
//                                is EventProcessor -> registerEventProcessor(configuration)
//                                is CommandBus -> registerCommandBus(configuration, name)
//                                is EventBus -> registerEventBus(configuration, name)
//                                else -> throw kotlin.IllegalArgumentException(componentType.canonicalName)
//                            }
//                            throw IllegalArgumentException(String.format("Unrecognized component: [%s]",
//                                    componentType))
                        })(comp, name);
                    }
                })

        return this;
    }

    private fun registerEventBus(configuration: Configuration,
            name: String): MessageMonitor<Message<*>>
    {
        return infrastruktur.loggingMonitor(configuration)
    }

    private fun registerCommandBus(configuration: Configuration,
            name: String): MessageMonitor<Message<*>>
    {
        return infrastruktur.loggingMonitor(configuration)
    }

    private fun registerEventProcessor(configuration: Configuration): MessageMonitor<Message<*>>
    {
        return infrastruktur.loggingMonitor(configuration)
    }

    fun start()
    {
        konfiguration.start()
    }

    fun stop()
    {
        konfiguration.shutdown()
    }

    private val commandGatewayFactory
        get() = CommandGatewayFactory(commandbus)
                .registerCommandCallback(infrastruktur.logger())

    private fun <T : Any> gateway(kClass: KClass<T>): T
    {
        return commandGatewayFactory.createGateway(kClass.java)
    }

    fun api(): Dominium
    {
        return Dominium(
                gateway(HaushaltsbuchführungApi::class),
                gateway(InventurApi::class),
                queryGateway,
                historie)
    }
}




