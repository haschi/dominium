package com.github.haschi.haushaltsbuch.infrastruktur

import io.vertx.core.logging.LoggerFactory
import org.axonframework.commandhandling.CommandCallback
import org.axonframework.commandhandling.CommandMessage
import java.text.MessageFormat

class LoggingCallback private constructor() : CommandCallback<Any, Any>
{
    private val logger = LoggerFactory.getLogger(LoggingCallback::class.java)

    override fun onSuccess(message: CommandMessage<*>, result: Any)
    {
        logger.info(
                MessageFormat.format(
                        "[{0}] {1} => OK {2}",
                        message.commandName,
                        message.identifier,
                        result))
    }

    override fun onFailure(message: CommandMessage<*>, cause: Throwable)
    {
        logger.warn(
                MessageFormat.format(
                        "[{0}] {1} => OK {2}",
                        message.commandName,
                        message.identifier,
                        cause.localizedMessage))
    }

    companion object
    {

        val INSTANCE = LoggingCallback()
    }
}
