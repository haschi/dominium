package de.therapeutenkiller.coding.aspekte.validation;

import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException;
import org.aspectj.lang.reflect.CodeSignature;

import java.lang.reflect.Parameter;

class Parameterliste {
    final CodeSignature signature;
    private final Parameter[] parameter;

    Parameterliste(final CodeSignature signature, final Parameter[] parameter) {

        super();

        this.signature = signature;
        this.parameter = parameter;
    }

    public final void argumentePrüfen(final Object... argumente) {
        for (int i = 0; i < argumente.length; i++) {
            if (!(this.istOptionalerParameter(i) || this.istGültig(i, argumente[i]))) {
                final String name = this.signature.getParameterNames()[i];
                final String fehlermeldung = String.format("Parameter '%s' ist null.", name);

                throw new ArgumentIstNullException(fehlermeldung);
            }
        }
    }

    private boolean istOptionalerParameter(final int parameterIndex) {
        return parameterIndex >= this.parameter.length;
    }

    private boolean istGültig(final int parameterIndex, final Object argument) {
        final ParameterPrüfung parameterPrüfung = new ParameterPrüfung(
            this.parameter[parameterIndex]);

        return parameterPrüfung.istGültig(argument);
    }
}
