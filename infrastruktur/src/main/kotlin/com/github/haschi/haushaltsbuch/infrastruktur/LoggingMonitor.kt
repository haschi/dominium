package com.github.haschi.haushaltsbuch.infrastruktur

import io.vertx.core.logging.LoggerFactory
import org.axonframework.messaging.Message
import org.axonframework.monitoring.MessageMonitor
import org.axonframework.monitoring.MessageMonitor.MonitorCallback

class LoggingMonitor(private val name: String) : MessageMonitor<Message<*>>
{
    override fun onMessageIngested(message: Message<*>): MonitorCallback
    {
        return MessageMonitorCallback(name, message, System.nanoTime());
    }

    companion object
    {
        val logger = LoggerFactory.getLogger("MESSAGE")
    }

    class MessageMonitorCallback(private val name: String,
            private val message: Message<*>,
            private val start: Long) :
            MonitorCallback
    {

        override fun reportIgnored()
        {
            logger.info("$name ${message.payloadType.simpleName} IGNORED (${dauer()} ms)")
        }

        override fun reportSuccess()
        {
            logger.info("$name ${message.payloadType.simpleName} OK (${dauer()} ms)")
        }

        override fun reportFailure(cause: Throwable)
        {
            logger.error("$name ${message.payloadType.simpleName} ERROR: ${cause.localizedMessage} (${dauer()} ms)")
        }

        private fun dauer(): Long
        {
            return System.nanoTime().minus(start) / 1000000
        }
    }
}