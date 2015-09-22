package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.PendingException;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;

/**
 * Created by mhaschka on 22.09.15.
 */
public class MyStepdefs {

  private Geldbörse geldbörse;

  @Angenommen("^ich habe eine leere Geldbörse$")
  public void ich_habe_eine_leere_Geldbörse() throws Throwable {
    this.geldbörse = Geldbörse.Leer;
  }

  @Wenn("^ich (\\d+) (.*) in meine Geldbörse stecke$")
  public void ich_€_in_meine_Geldbörse_stecke(int betrag, String währung) throws Throwable {
    Geld geld = new Geld(betrag, währung);
    this.geldbörse.hineinstecken(geld);
  }

  @Dann("^befinden sich (\\d+) (.*) in meiner Geldbörse$")
  public void befinden_sich_€_in_meiner_Geldbörse(int betrag, String währung) throws Throwable {
    Geld geld = new Geld(betrag, währung);
    assert this.geldbörse.getInhalt().equals(geld);
  }

  @Angenommen("^in meiner Geldbörse befinden sich (\\d+) €$")
  public void in_meiner_Geldbörse_befinden_sich_€(int arg1) throws Throwable {
    // Express the Regexp above with the code you wish you had
    throw new PendingException();
  }
}
