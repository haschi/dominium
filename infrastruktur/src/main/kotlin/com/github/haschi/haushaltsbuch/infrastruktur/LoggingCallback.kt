package com.github.haschi.haushaltsbuch.infrastruktur

import org.axonframework.commandhandling.CommandCallback
import org.axonframework.commandhandling.CommandMessage
import org.slf4j.LoggerFactory

class LoggingCallback private constructor() : CommandCallback<Any, Any>
{
    private val logger = LoggerFactory.getLogger("COMMAND")

    override fun onSuccess(message: CommandMessage<*>, result: Any?)
    {
        logger.info("[${message.commandName}] ${message.identifier} => OK ${result ?: ""}")
    }

    override fun onFailure(message: CommandMessage<*>, cause: Throwable)
    {
        logger.warn("[${message.commandName}] ${message.identifier} => ERR ${cause.localizedMessage}")
    }

    companion object
    {
        val INSTANCE = LoggingCallback()
    }
}
