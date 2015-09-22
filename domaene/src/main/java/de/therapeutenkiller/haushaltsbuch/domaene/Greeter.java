package de.therapeutenkiller.haushaltsbuch.domaene;


public final class Greeter {

  @CoverageIgnore private Greeter() {
    // $COVERAGE-IGNORE$
  }

  public static String sayHello(String matthias) {
    if (matthias == null) {
      throw new IllegalArgumentException();
    }

    return "Hello, " + matthias + "!";
  }


  @CoverageIgnore public static void unusedMethod() {

  }
}
