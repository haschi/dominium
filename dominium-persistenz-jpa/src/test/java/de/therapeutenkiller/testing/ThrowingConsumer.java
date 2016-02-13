package de.therapeutenkiller.testing;

import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowingConsumer<T>  extends Consumer<T> {
    void doAccept(T t)
            throws Throwable;

    default void accept(T t) {
        try {
            this.doAccept(t);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}

