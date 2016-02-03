package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.Ereignisstrom;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@SuppressWarnings("checkstyle:designforextension")
@Entity()
public class JpaEreignisstrom extends Ereignisstrom<JpaEreignisMetaDaten> {

    @Id
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public void setVersion(final long version) {
        this.version = version;
    }

    public  JpaEreignisstrom() {
        super(StringUtils.EMPTY);
    }

    public JpaEreignisstrom(final String streamName) {
        super(streamName);
    }

    @Override
    protected <A> JpaDomänenereignisUmschlag<A> umschlagErzeugen(final Domänenereignis<A> ereignis) {

        final JpaEreignisMetaDaten meta = new JpaEreignisMetaDaten(
                this.getName(),
                this.getVersion());

        return new JpaDomänenereignisUmschlag<>(ereignis, meta);
    }
}
