package de.therapeutenkiller.haushaltsbuch.aspekte;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
public class DisableBillionDollarBugAspect {

    @SuppressWarnings("RedundantThrows")
    @Before("execution(public * *(..))")
    public final void checkArguments(final JoinPoint joinPoint) throws Throwable {
        final Object[] arguments = joinPoint.getArgs();
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final Method method = signature.getMethod();

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] == null) {
                final Parameter[] parameters = method.getParameters();
                if (i < parameters.length) {
                    final Annotation ann = parameters[i].getAnnotation(CanBeNull.class);
                    if (ann == null) {
                        throw new ContractException(parameters[i].toString());
                    }
                }
            }
        }
    }

    @SuppressWarnings("RedundantThrows")
    @AfterReturning(value = "execution(public * *(..))", returning = "returnValue")
    public final void checkResult(final JoinPoint joinPoint, final Object returnValue)
        throws Throwable {

        if (returnValue == null) {
            final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            final Method method = signature.getMethod();
            final Class<?> returnType = method.getReturnType();
            if (!returnType.equals(Void.TYPE)) {
                if (method.getAnnotation(CanBeNull.class) == null) {
                    throw new ContractException("Rückgabewert darf nicht null sein.");
                }
            }
        }
    }
}
