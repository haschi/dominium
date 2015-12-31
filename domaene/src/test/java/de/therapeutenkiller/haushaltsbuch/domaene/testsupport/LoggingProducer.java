package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

class LoggingProducer {

    @Produces
    public final Logger createLogger(final InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass()); // NOPMD
    }
}
