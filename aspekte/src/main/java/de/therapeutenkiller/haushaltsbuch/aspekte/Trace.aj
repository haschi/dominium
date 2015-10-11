package com.stackoverflow.aspects;
/*
public aspect Trace {
    pointcut publicMethodExecuted(): execution(public !static * *(..));

    after(): publicMethodExecuted() {
        System.out.printf("Enters on method: %s. \n", thisJoinPoint.getSignature());

        Object[] arguments = thisJoinPoint.getArgs();
        for (int i =0; i < arguments.length; i++){
            Object argument = arguments[i];
            if (argument != null){
                System.out.printf("With argument of type %s and value %s. \n", argument.getClass().toString(), argument);
            }
        }
        System.out.printf("Exits method: %s. \n", thisJoinPoint.getSignature());
    }
}
*/
