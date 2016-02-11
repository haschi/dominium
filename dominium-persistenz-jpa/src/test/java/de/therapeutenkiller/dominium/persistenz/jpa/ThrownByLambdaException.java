package de.therapeutenkiller.dominium.persistenz.jpa;

public final class ThrownByLambdaException extends RuntimeException {
    public ThrownByLambdaException(final Throwable throwable) {
        super(throwable);
    }
}
