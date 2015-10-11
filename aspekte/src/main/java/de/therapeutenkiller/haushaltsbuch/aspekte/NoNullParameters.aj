package de.therapeutenkiller.haushaltsbuch.aspekte;
/*
public aspect NoNullParameters {

    pointcut publicMethodExecuted(): execution(public * *(..));

    before(): publicMethodExecuted() {
        System.out.printf("Testing arguments for method: %s. \n", thisJoinPoint.getSignature());

        Object[] arguments = thisJoinPoint.getArgs();
        for (int i = 0; i < arguments.length; i++) {
            Object argument = arguments[i];

            if (argument == null) {
                throw new ContractException(thisJoinPoint.getSignature());
            }
        }
    }
}
*/
