package com.github.haschi.haushaltsbuch.system;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

public final class LogProducer
{

    @Produces
    public Logger createLogger(final InjectionPoint injectionPoint)
    {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getTypeName());
    }
}
