package com.github.haschi.haushaltsbuch.domaene.testsupport;

import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.HaushaltsbuchEreignis;
import org.apache.commons.lang3.NotImplementedException;

import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

@Singleton
public class DieWelt {

    private UUID aktuelleHaushaltsbuchId;

    public final List<HaushaltsbuchEreignis> aktuellerEreignisstrom() {
        return this.getStream(this.getAktuelleHaushaltsbuchId());
    }

    public final List<HaushaltsbuchEreignis> getStream(final UUID haushaltsbuchId) {
        throw new NotImplementedException("Nicht implementiert");
    }

    public final UUID getAktuelleHaushaltsbuchId() {
        return this.aktuelleHaushaltsbuchId;
    }

    public final void setAktuelleHaushaltsbuchId(final UUID aktuelleHaushaltsbuchId) {
        this.aktuelleHaushaltsbuchId = aktuelleHaushaltsbuchId;
    }
}
