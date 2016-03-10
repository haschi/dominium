package de.therapeutenkiller.coding.aspekte;

import java.lang.reflect.Method;

public class RückgabewertIstNullException extends RuntimeException {
    public RückgabewertIstNullException(final Method method) {
        super(String.format(
            "Rückgabewert der Methode '%s' ist null.",
            method.getName()));
    }
}
