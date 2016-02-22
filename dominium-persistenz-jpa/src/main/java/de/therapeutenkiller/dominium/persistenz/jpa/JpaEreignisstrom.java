package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.Ereignisstrom;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@SuppressWarnings("checkstyle:designforextension")
@Entity
public class JpaEreignisstrom extends Ereignisstrom<UUID, JpaEreignisMetaDaten<UUID>> {

    public JpaEreignisstrom() {
        super(UUID.randomUUID());
    }

    @Id
    @Override
    public UUID getIdentitätsmerkmal() {
        return this.identitätsmerkmal;
    }

    public void setIdentitätsmerkmal(final UUID identitätsmerkmal) {
        this.identitätsmerkmal = identitätsmerkmal;
    }

    public long getVersion() {
        return this.version;
    }

    public void setVersion(final long version) {
        this.version = version;
    }

    public JpaEreignisstrom(final UUID streamName) {
        super(streamName);
    }

    @Override
    protected <A> JpaDomänenereignisUmschlag<A> umschlagErzeugen(final Domänenereignis<A> ereignis) {

        final JpaEreignisMetaDaten<UUID> meta = new JpaEreignisMetaDaten<>(
                this.getIdentitätsmerkmal(),
                this.getVersion());

        return new JpaDomänenereignisUmschlag<>(ereignis, meta);
    }
}
