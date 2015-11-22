package de.therapeutenkiller.haushaltsbuch.domaene;

import java.util.UUID;

public interface HaushaltsbuchRepository {
    void hinzufügen(final Haushaltsbuch haushaltsbuch);

    Haushaltsbuch besorgen(UUID haushaltsbuchId);

    void leeren();
}
