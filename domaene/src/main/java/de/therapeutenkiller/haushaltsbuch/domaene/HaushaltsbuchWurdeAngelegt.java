package de.therapeutenkiller.haushaltsbuch.domaene;

public class HaushaltsbuchWurdeAngelegt extends Domänenereignis {
    public Haushaltsbuch haushaltsbuch;

    public HaushaltsbuchWurdeAngelegt(final Haushaltsbuch haushaltsbuch) {
        super();
        this.haushaltsbuch = haushaltsbuch;
    }
}
