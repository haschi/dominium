package de.therapeutenkiller.coding.aspekte.validation;

import de.therapeutenkiller.coding.aspekte.RückgabewertIstNullException;
import de.therapeutenkiller.coding.aspekte.DarfNullSein;

import java.lang.reflect.Method;

class Rückgabewert {
    private final Method method;

    Rückgabewert(final Method method) {
        super();
        this.method = method;
    }

    public final void prüfen() {
        if (!this.method.getReturnType().equals(Void.TYPE)
            && this.method.getAnnotation(DarfNullSein.class) == null) {
            throw new RückgabewertIstNullException(this.method);
        }
    }
}
