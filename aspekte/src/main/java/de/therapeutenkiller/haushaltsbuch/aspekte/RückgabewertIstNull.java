package de.therapeutenkiller.haushaltsbuch.aspekte;

import java.lang.reflect.Method;

public class RückgabewertIstNull extends RuntimeException {
    public RückgabewertIstNull(final Method method) {
        super(String.format(
            "Rückgabewert der Methode '%s' ist null.",
            method.getName()));
    }
}
