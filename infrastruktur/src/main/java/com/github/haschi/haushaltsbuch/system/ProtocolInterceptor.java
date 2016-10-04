package com.github.haschi.haushaltsbuch.system;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@SuppressWarnings("checkstyle:designforextension")
@Logged
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class ProtocolInterceptor
{

    @Inject
    private Logger log;

    @AroundInvoke
    protected Object protocolInvocation(final InvocationContext ic) throws Exception
    {
        final StringBuilder parameters = new StringBuilder("[");
        final String collect = Arrays.stream(ic.getParameters())
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        parameters.append(collect);

        parameters.append("]");
        this.log.log(
                Level.INFO,
                "{0}.{1}({2})",
                new Object[]{ic.getMethod()
                        .getDeclaringClass().getTypeName(), ic.getMethod().getName(), parameters.toString()});
        return ic.proceed();
    }
}

