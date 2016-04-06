package de.therapeutenkiller.testing;

import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowingConsumer<T>  extends Consumer<T> {
    void doAccept(T parameter)
            throws Throwable;

    default void accept(final T parameter) {
        try {
            this.doAccept(parameter);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (final Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}

