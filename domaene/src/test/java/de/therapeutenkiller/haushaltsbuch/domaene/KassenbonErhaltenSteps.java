package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;

public final class KassenbonErhaltenSteps {

    @Given("^mein Vermögen beträgt (\\d+) €$")
    public void given_mein_vermögen_beträgt(final Integer betrag) {
        throw new PendingException();
    }
}
