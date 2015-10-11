package de.therapeutenkiller.haushaltsbuch.aspekte;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by mhaschka on 11.10.15.
 */
@Aspect
public class NotNullPrecondition {

  @Before("execution(public * *(..))")
  public void checkArguments(JoinPoint joinPoint) throws Throwable {
    Object[] arguments = joinPoint.getArgs();
    MethodSignature signature = (MethodSignature)joinPoint.getSignature();
    Method method = signature.getMethod();

    System.out.format("Testing arguments for %s: ", joinPoint.getSignature());

    for (int i = 0; i < arguments.length; i++) {
      if (arguments[i] == null) {
        Parameter[] parameters = method.getParameters();
        if (i < parameters.length) {
          Annotation ann = parameters[i].getAnnotation(CanBeNull.class);
          if (ann == null) {
            System.out.println("FAILED");
            throw new ContractException(parameters[i].toString());
          }
        }
      }
    }

    System.out.println("OK");
  }


  @AfterReturning(value = "execution(public * *(..))", returning = "returnValue")
  public void checkResult(JoinPoint joinPoint, Object returnValue) throws Throwable {

    System.out.format("Testing result of %s", joinPoint.getSignature());

    if (returnValue == null) {
      MethodSignature signature = (MethodSignature) joinPoint.getSignature();
      Method method = signature.getMethod();
      Class<?> returnType = method.getReturnType();
      if (!returnType.equals(Void.TYPE)) {
        System.out.println("FAILED");
        throw new ContractException("Rückgabewert darf nicht null sein.");
      }
    }
    System.out.println("OK");
  }
}
