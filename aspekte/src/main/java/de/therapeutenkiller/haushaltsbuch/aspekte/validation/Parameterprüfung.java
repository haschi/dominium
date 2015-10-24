package de.therapeutenkiller.haushaltsbuch.aspekte.validation;

import de.therapeutenkiller.haushaltsbuch.aspekte.DarfNullSein;

import java.lang.reflect.Parameter;

class Parameterprüfung {
    private final Parameter parameter;

    public Parameterprüfung(final Parameter parameter) {
        this.parameter = parameter;
    }

    public final boolean istGültig(final Object argument) {
        return argument != null || this.darfNullSein();
    }

    public final boolean darfNullSein() {
        return this.parameter.getAnnotation(DarfNullSein.class) != null;
    }
}
