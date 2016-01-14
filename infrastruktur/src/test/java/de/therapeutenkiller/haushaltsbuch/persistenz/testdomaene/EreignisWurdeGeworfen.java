package de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene;

public class EreignisWurdeGeworfen implements Domänenereignis<TestAggregat> {

    private final String vorname;
    private final String nachname;

    public EreignisWurdeGeworfen() {
        this.vorname = null;
        this.nachname = null;
    }

    public EreignisWurdeGeworfen(final String vorname, final String nachname) {

        this.vorname = vorname;
        this.nachname = nachname;
    }

    public final void anwendenAuf(final TestAggregat aggregat) {
        aggregat.falls(this);
    }
}