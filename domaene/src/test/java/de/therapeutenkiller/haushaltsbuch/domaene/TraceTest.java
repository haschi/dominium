package de.therapeutenkiller.haushaltsbuch.domaene;

import de.therapeutenkiller.haushaltsbuch.aspekte.CanBeNull;
import org.junit.Test;

/**
 * Created by mhaschka on 10.10.15.
 */

public class TraceTest {

  public void methodeMitParameter(String einString) {
    System.out.println(einString);
  }

  public Object methodeGibtNullZurück() {
    return null;
  }

  public void parameterCanBeNull(@CanBeNull Object parameter) {
  }
}
