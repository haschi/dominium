package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.persistenz.Ereignisstrom;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@SuppressWarnings("checkstyle:designforextension")
@Entity
public class JpaEreignisstrom extends Ereignisstrom<UUID, JpaEreignisMetaDaten> {

    public JpaEreignisstrom() {
        super(UUID.randomUUID());
    }

    @Id
    @Column(columnDefinition = "BINARY(16)")
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
    protected <A> JpaDomänenereignisUmschlag<A> umschlagErzeugen(final A ereignis) {

        final JpaEreignisMetaDaten meta = new JpaEreignisMetaDaten(
                this.getIdentitätsmerkmal(),
                this.getVersion());

        return new JpaDomänenereignisUmschlag<>(ereignis, meta);
    }
}
