package com.github.haschi.haushaltsbuch.domaene.testsupport;

import com.github.haschi.dominium.modell.Domänenereignis;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import org.apache.commons.lang3.NotImplementedException;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

@Singleton
public class DieWelt {

    private UUID aktuelleHaushaltsbuchId;

    @Inject
    private BeanManager manager;

    public final List<Domänenereignis<HaushaltsbuchEreignisziel>> aktuellerEreignisstrom() {
        return this.getStream(this.getAktuelleHaushaltsbuchId());
    }

    public final <T> void kommandoAusführen(final T kommando) {
        this.manager.fireEvent(kommando);
    }

    public final List<Domänenereignis<HaushaltsbuchEreignisziel>> getStream(final UUID haushaltsbuchId) {
        throw new NotImplementedException("Nicht implementiert");
    }

    public final UUID getAktuelleHaushaltsbuchId() {
        return this.aktuelleHaushaltsbuchId;
    }

    public final void setAktuelleHaushaltsbuchId(final UUID aktuelleHaushaltsbuchId) {
        this.aktuelleHaushaltsbuchId = aktuelleHaushaltsbuchId;
    }
}
