package com.github.haschi.haushaltsbuch.infrastruktur

import io.vertx.core.logging.LoggerFactory
import org.axonframework.messaging.Message
import org.axonframework.monitoring.MessageMonitor
import org.axonframework.monitoring.MessageMonitor.MonitorCallback

class LoggingMonitor(private val name: String) : MessageMonitor<Message<*>>
{
    override fun onMessageIngested(message: Message<*>): MonitorCallback
    {
        return MessageMonitorCallback(name, message);
    }

    companion object
    {
        val logger = LoggerFactory.getLogger("MESSAGE")
    }

    class MessageMonitorCallback(private val name: String, private val message: Message<*>) :
            MonitorCallback
    {

        override fun reportIgnored()
        {
            logger.info("$name ${message.payloadType.simpleName} IGNORED")
        }

        override fun reportSuccess()
        {
            logger.info("$name ${message.payloadType.simpleName} OK")
        }

        override fun reportFailure(cause: Throwable)
        {
            logger.error("$name ${message.payloadType.simpleName} ERROR: ${cause.localizedMessage}")
        }
    }
}