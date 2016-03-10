package de.therapeutenkiller.coding.aspekte.validation;

import org.aspectj.lang.reflect.ConstructorSignature;

/**
 * Created by m.haschka on 11/3/15.
 */
public class Konstruktor {

    private final ConstructorSignature constructor;

    public Konstruktor(ConstructorSignature constructor) {
        this.constructor = constructor;
    }

    public void argumentePrüfen(final Object... arguments) {
        final Parameterliste parameterliste = new Parameterliste(
                this.constructor,
                this.constructor.getConstructor().getParameters());

        parameterliste.argumentePrüfen(arguments);
    }
}
