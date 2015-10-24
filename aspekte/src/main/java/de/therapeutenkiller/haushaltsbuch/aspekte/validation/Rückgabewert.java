package de.therapeutenkiller.haushaltsbuch.aspekte.validation;

import de.therapeutenkiller.haushaltsbuch.aspekte.DarfNullSein;
import de.therapeutenkiller.haushaltsbuch.aspekte.ArgumentIstNull;
import de.therapeutenkiller.haushaltsbuch.aspekte.RückgabewertIstNull;

import java.lang.reflect.Method;

class Rückgabewert {
    private final Method method;

    public Rückgabewert(final Method method) {
        this.method = method;
    }

    public final void prüfen() {
        if (!this.method.getReturnType().equals(Void.TYPE)
            && this.method.getAnnotation(DarfNullSein.class) == null) {
            throw new RückgabewertIstNull(this.method);
        }
    }
}
