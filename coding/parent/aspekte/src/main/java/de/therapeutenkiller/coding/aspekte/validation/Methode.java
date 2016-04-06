package de.therapeutenkiller.coding.aspekte.validation;

import org.aspectj.lang.reflect.MethodSignature;

public final class Methode {

    private final MethodSignature signatur;

    public Methode(final MethodSignature signatur) {
        super();
        this.signatur = signatur;
    }

    public void rückgabewertPrüfen() {
        final Rückgabewert rückgabewert = new Rückgabewert(this.signatur.getMethod());
        rückgabewert.prüfen();
    }

    public void argumentePrüfen(final Object... arguments) {
        final Parameterliste parameterliste = new Parameterliste(
                this.signatur,
                this.signatur.getMethod().getParameters());

        parameterliste.argumentePrüfen(arguments);
    }
}

