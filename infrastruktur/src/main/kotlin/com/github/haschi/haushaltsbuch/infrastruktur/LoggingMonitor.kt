package com.github.haschi.haushaltsbuch.infrastruktur

import io.vertx.core.logging.LoggerFactory
import org.axonframework.messaging.Message
import org.axonframework.monitoring.MessageMonitor
import org.axonframework.monitoring.MessageMonitor.MonitorCallback

class LoggingMonitor : MessageMonitor<Message<*>>
{
    override fun onMessageIngested(message: Message<*>): MonitorCallback
    {
        logger.info("Message ingested")
        return MessageMonitorCallback(message);
    }

    companion object
    {
        val logger = LoggerFactory.getLogger("MESSAGE")
    }

    class MessageMonitorCallback(private val message: Message<*>) : MonitorCallback
    {

        override fun reportIgnored()
        {
            logger.info("${message.identifier} ${message.payloadType} IGNORED")
        }

        override fun reportSuccess()
        {
            logger.info("${message.identifier} ${message.payloadType} OK")
        }

        override fun reportFailure(cause: Throwable)
        {
            logger.error("${message.identifier} ${message.payloadType} ERROR: ${cause.localizedMessage}")
        }
    }
}