package de.therapeutenkiller.haushaltsbuch.domaene;

public interface HaushaltsbuchRepository {
    void hinzufügen(final Haushaltsbuch haushaltsbuch);

    Haushaltsbuch besorgen();

    void leeren();
}
