package de.therapeutenkiller.coding.aspekte;

import de.therapeutenkiller.coding.aspekte.validation.Konstruktor;
import de.therapeutenkiller.coding.aspekte.validation.Methode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Lambda-Ausdrücke werden nicht berücksichtigt.
 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=471347
 */
@Aspect
public class NullReferenzPrüfung {

    @Before("execution(public * *(..)) && "
            + "! (within(de.therapeutenkiller.coding.aspekte..*) "
            + "|| execution(public boolean *.equals(Object)))")
    public final void argumentePrüfen(final JoinPoint joinPoint) {
        final Methode methode = new Methode((MethodSignature) joinPoint.getSignature());
        methode.argumentePrüfen(joinPoint.getArgs());
    }

    @Before("execution(!private (!(is(InnerType) || is(AnonymousType)) && *).new(..)) && "
            + "!within(de.therapeutenkiller.coding.aspekte..*)")
    public final void konstruktorArgumentePrüfen(final JoinPoint joinPoint) {
        final ConstructorSignature signature = (ConstructorSignature) joinPoint.getSignature();
        final Konstruktor konstruktor = new Konstruktor(signature);
        konstruktor.argumentePrüfen(joinPoint.getArgs());
    }

    @AfterReturning(value = "execution(public !void *(..)) && "
        + "! within(de.therapeutenkiller.coding.aspekte..*)",
        returning = "returnValue")
    public final void rückgabewertPrüfen(final JoinPoint joinPoint, final Object returnValue) {

        if (returnValue == null) {
            final Methode methode = new Methode((MethodSignature) joinPoint.getSignature());
            methode.rückgabewertPrüfen();
        }
    }
}
