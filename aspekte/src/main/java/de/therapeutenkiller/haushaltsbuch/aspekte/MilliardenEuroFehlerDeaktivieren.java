package de.therapeutenkiller.haushaltsbuch.aspekte;

import de.therapeutenkiller.haushaltsbuch.aspekte.validation.Methode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=471347
 */
@Aspect
public class MilliardenEuroFehlerDeaktivieren {

    @Before("execution(public * *(..)) && ! within(de.therapeutenkiller.haushaltsbuch.aspekte.validation.*)")
    public final void argumentePrüfen(final JoinPoint joinPoint) {
        final Methode methode = new Methode((MethodSignature) joinPoint.getSignature());
        methode.argumentePrüfen(joinPoint.getArgs());
    }

    @AfterReturning(value = "execution(public !void *(..)) && ! within(de.therapeutenkiller.haushaltsbuch.aspekte.validation.*)", returning = "returnValue")
    public final void rückgabewertPrüfen(final JoinPoint joinPoint, final Object returnValue) {

        if (returnValue == null) {
            final Methode methode = new Methode((MethodSignature) joinPoint.getSignature());
            methode.rückgabewertPrüfen();
        }
    }
}
