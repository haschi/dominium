package com.github.haschi.testing;

public final class ThrownByLambdaException extends RuntimeException {
    public ThrownByLambdaException(final Throwable throwable) {
        super(throwable);
    }
}
