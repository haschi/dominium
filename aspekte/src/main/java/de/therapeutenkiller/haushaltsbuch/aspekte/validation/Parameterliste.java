package de.therapeutenkiller.haushaltsbuch.aspekte.validation;

import de.therapeutenkiller.haushaltsbuch.aspekte.ArgumentIstNull;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

class Parameterliste {
    private final Parameter[] parameter;

    public Parameterliste(final Method method) {
        this(method.getParameters());
    }

    public Parameterliste(final Parameter... parameter) {
        this.parameter = parameter.clone();
    }

    public final void argumentePrüfen(final Object... argumente) {
        for (int i = 0; i < argumente.length; i++) {
            if (!(this.istOptionalerParameter(i) || this.istGültig(i, argumente[i]))) {
                throw new ArgumentIstNull(this.parameter[i].toString());
            }
        }
    }

    private boolean istOptionalerParameter(final int parameterIndex) {
        return parameterIndex >= this.parameter.length;
    }

    private boolean istGültig(final int parameterIndex, final Object argument) {

        final Parameterprüfung parameterprüfung = new Parameterprüfung(
            this.parameter[parameterIndex]);

        return parameterprüfung.istGültig(argument);
    }
}
