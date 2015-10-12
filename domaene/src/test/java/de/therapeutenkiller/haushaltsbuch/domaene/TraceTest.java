package de.therapeutenkiller.haushaltsbuch.domaene;

import de.therapeutenkiller.haushaltsbuch.aspekte.CanBeNull;

public class TraceTest {

  public final void methodeMitParameter(final String einString) {
    System.out.println(einString);
  }

  @SuppressWarnings("SameReturnValue")
  public final Object methodeGibtNullZurück() {
    return null;
  }

  @SuppressWarnings("EmptyMethod")
  public void parameterCanBeNull(@CanBeNull final Object parameter) {
  }

  @SuppressWarnings("SameReturnValue")
  @CanBeNull
  public final Object annotierteMethodeGibtNullZurück() {
    return null;
  }
}
