package com.github.haschi.haushaltsbuch.infrastruktur

import org.axonframework.eventhandling.EventMessage
import org.axonframework.messaging.ExecutionException
import org.axonframework.messaging.Message
import org.axonframework.messaging.MessageDispatchInterceptor
import org.axonframework.messaging.unitofwork.CurrentUnitOfWork
import org.axonframework.messaging.unitofwork.ExecutionResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.function.BiFunction

class EventLoggingInterceptor :
        MessageDispatchInterceptor<EventMessage<*>>
{
    private val logger: Logger = LoggerFactory.getLogger(EventLoggingInterceptor::class.java)

    override fun handle(messages: List<EventMessage<*>>)
            : BiFunction<Int, EventMessage<*>, EventMessage<*>>
    {
        CurrentUnitOfWork.ifStarted { unitOfWork ->
            val published = messages.joinToString(limit = 5) { format(it) }
            val result = ergebnisText(unitOfWork.executionResult)

            logger.info("EVENT   [$published] $result")
        }

        return BiFunction { _, m -> m }
    }

    private fun ergebnisText(executionResult: ExecutionResult?): String
    {
        return if (executionResult != null)
        {
            if (executionResult.isExceptionResult)
            {
                var exception = executionResult.exceptionResult
                exception = if (exception is ExecutionException) exception.cause else exception

                "=> Fehler: [${exception.javaClass.simpleName}]"
            }
            else ""
        }
        else ""
    }

    private fun format(message: Message<*>) = message.payloadType.simpleName
}

