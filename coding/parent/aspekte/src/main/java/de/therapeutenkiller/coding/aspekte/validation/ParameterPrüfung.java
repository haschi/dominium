package de.therapeutenkiller.coding.aspekte.validation;

import java.lang.reflect.Parameter;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;

class ParameterPrüfung {
    private final Parameter parameter;

    ParameterPrüfung(final Parameter parameter) {

        super();

        this.parameter = parameter;
    }

    public final boolean istGültig(final Object argument) {
        return argument != null || this.darfNullSein();
    }

    public final boolean darfNullSein() {
        return this.parameter.getAnnotation(DarfNullSein.class) != null;
    }
}
