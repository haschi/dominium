package de.therapeutenkiller.coding.aspekte.validation;

import org.aspectj.lang.reflect.ConstructorSignature;

public class Konstruktor {

    private final ConstructorSignature constructor;

    public Konstruktor(final ConstructorSignature constructor) {
        super();
        this.constructor = constructor;
    }

    public final void argumentePrüfen(final Object... arguments) {
        final Parameterliste parameterliste = new Parameterliste(
                this.constructor,
                this.constructor.getConstructor().getParameters());

        parameterliste.argumentePrüfen(arguments);
    }
}
