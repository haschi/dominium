package com.github.haschi.haushaltsbuch.infrastruktur

import org.axonframework.commandhandling.CommandMessage
import org.axonframework.messaging.InterceptorChain
import org.axonframework.messaging.MessageHandlerInterceptor
import org.axonframework.messaging.unitofwork.UnitOfWork
import org.slf4j.LoggerFactory

object CommandLoggingInterceptor : MessageHandlerInterceptor<CommandMessage<*>>
{
    private val logger = LoggerFactory.getLogger(CommandLoggingInterceptor::class.java)

    override fun handle(uof: UnitOfWork<out CommandMessage<*>>, chain: InterceptorChain?): Any?
    {
        logger.info(formatieren(uof.message))

        return chain!!.proceed()
    }

    private fun formatieren(message: CommandMessage<*>): String
    {
        val command = message.payloadType.simpleName
        val traceId = message.metaData["traceId"] ?: "-"
        val messageId = message.identifier

        return "[$messageId:$traceId] $command"
    }
}