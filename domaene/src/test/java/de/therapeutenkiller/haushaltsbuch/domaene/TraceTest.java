package de.therapeutenkiller.haushaltsbuch.domaene;

import de.therapeutenkiller.haushaltsbuch.aspekte.CanBeNull;
import org.junit.Test;

/**
 * Created by mhaschka on 10.10.15.
 */

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
}
