package de.therapeutenkiller.haushaltsbuch.aspekte.validation;

import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

public final class Methode {
    private final Method methode;

    public Methode(final MethodSignature signatur) {
        this.methode = signatur.getMethod();
    }

    public void rückgabewertPrüfen() {
        final Rückgabewert rückgabewert = new Rückgabewert(this.methode);
        rückgabewert.prüfen();
    }

    public void argumentePrüfen(final Object... arguments) {
        final Parameterliste parameterliste = new Parameterliste(this.methode);
        parameterliste.argumentePrüfen(arguments);
    }
}

