package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.java.de.Angenommen;

import javax.inject.Inject;

public final class GeimeinsameSteps {

    private final HaushaltsbuchführungBeginnen haushaltsbuchführungBeginnen;

    @Inject
    public GeimeinsameSteps(final HaushaltsbuchführungBeginnen haushaltsbuchführungBeginnen) {

        this.haushaltsbuchführungBeginnen = haushaltsbuchführungBeginnen;
    }

    @Angenommen("^ich habe mit der Haushaltsbuchführung begonnen$")
    public void ich_habe_mit_der_Haushaltsbuchführung_begonnen() {
        this.haushaltsbuchführungBeginnen.ausführen();
    }
}
