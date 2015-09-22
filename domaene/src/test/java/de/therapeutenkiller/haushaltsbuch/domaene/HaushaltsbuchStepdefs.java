package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.java8.De;


/**
 * Created by mhaschka on 20.09.15.
 */


public class HaushaltsbuchStepdefs implements De {

  private Geldbörse geldbörse;

  /*
  public HaushaltsbuchStepdefs() {
    // Angenommen("^ich habe eine leere Geldbörse$", () -> {
    //  this.geldbörse = Geldbörse.Leer;
    //});

    Wenn("^ich (\\d+) (.*) in meine Geldbörse stecke$", (Integer betrag, String währung) -> {
      Geld geld = new Geld(betrag, währung);
      this.geldbörse.hineinstecken(geld);
      ;
    });

    Dann("^befinden sich (\\d+) (.*) in meiner Geldbörse$", (Integer betrag, String währung) -> {
      Geld geld = new Geld(betrag, währung);
      assert this.geldbörse.getInhalt() ==  geld;
    });

    Angenommen("^in meiner Geldbörse befinden sich (\\d+) (.*)$",
        (Integer betrag, String währung) -> {
          Geld geld = new Geld(betrag, währung);
          this.geldbörse = new Geldbörse(geld);
        });

    Angenommen("^mein Vermögen beträgt (\\d+) €$", (Integer arg1) -> {
      // Write code here that turns the phrase above into concrete actions
      throw new PendingException();
    });

    Wenn("^ich einen Kassenbon mit (\\d+) € verbuche$", (Integer arg1) -> {
      // Write code here that turns the phrase above into concrete actions
      throw new PendingException();
    });

    Dann("^besitze ich ein Vermögen in Höhe von (\\d+) €$", (Integer arg1) -> {
      // Write code here that turns the phrase above into concrete actions
      throw new PendingException();
    });
  }
  */
}
