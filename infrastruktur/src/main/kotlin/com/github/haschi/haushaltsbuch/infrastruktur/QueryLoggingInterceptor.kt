package com.github.haschi.haushaltsbuch.infrastruktur

import org.axonframework.messaging.InterceptorChain
import org.axonframework.messaging.MessageHandlerInterceptor
import org.axonframework.messaging.unitofwork.UnitOfWork
import org.axonframework.queryhandling.QueryMessage

object QueryLoggingInterceptor : MessageHandlerInterceptor<QueryMessage<*,*>>
{
    override fun handle(
            unitOfWork: UnitOfWork<out QueryMessage<*, *>>?,
            interceptorChain: InterceptorChain): Any
    {
        return interceptorChain.proceed()
    }
}