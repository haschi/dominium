package de.therapeutenkiller.haushaltsbuch.system;

import org.jboss.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public final class JbossLoggerProducer {
    @Produces
    public Logger createLogger(final InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getTypeName());
    }
}
