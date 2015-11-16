package de.therapeutenkiller.haushaltsbuch.domaene;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;

public class TraceTest {

    public final void methodeMitParameter(final String einString) {
        System.out.println(einString);//NOPMD
    }

    @SuppressWarnings("SameReturnValue")
    public final Object methodeGibtNullZurück() {
        return null;
    }

    @SuppressWarnings("EmptyMethod")
    public void parameterCanBeNull(@DarfNullSein final Object parameter) {//NOPMD
    }

    @SuppressWarnings("SameReturnValue")
    @DarfNullSein
    public final Object annotierteMethodeGibtNullZurück() {
        return null;
    }
}
