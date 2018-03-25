package com.github.haschi.haushaltsbuch.infrastruktur

import org.axonframework.messaging.InterceptorChain
import org.axonframework.messaging.Message
import org.axonframework.messaging.MessageHandlerInterceptor
import org.axonframework.messaging.unitofwork.UnitOfWork
import org.slf4j.LoggerFactory

class LoggingInterceptor<T : Message<*>>(private val name: String)
    : MessageHandlerInterceptor<T>
{
    private val logger = LoggerFactory.getLogger(LoggingInterceptor::class.java)

    override fun handle(uow: UnitOfWork<out T>, chain: InterceptorChain): Any?
    {
        val start = System.nanoTime()
        val item = uow.message.payloadType.simpleName

        return try
        {
            val ergebnis = chain.proceed()
            val ausgabe = ergebnis?.javaClass?.simpleName ?: "nix"
            val end = System.nanoTime()
            val dauer = (end - start) / 100000

            logger.info("$name [$item] => [$ausgabe] ($dauer ms)")

            return ergebnis
        }
        catch (ausnahme: Exception)
        {
            logger.warn("[$item] fehlgeschlagen: '${ausnahme.message}'", ausnahme)
            throw ausnahme;
        }
    }
}