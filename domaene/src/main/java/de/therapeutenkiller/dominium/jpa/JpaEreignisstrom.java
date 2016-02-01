package de.therapeutenkiller.dominium.jpa;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.Ereignisstrom;
import de.therapeutenkiller.dominium.persistenz.Umschlag;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@SuppressWarnings("checkstyle:designforextension")
public class JpaEreignisstrom<A> extends Ereignisstrom<A, JpaEreignisMetaDaten> {

    @Id
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public  JpaEreignisstrom() {
        super(StringUtils.EMPTY);
    }

    public JpaEreignisstrom(final String streamName) {
        super(streamName);
    }

    @Override
    protected Umschlag<Domänenereignis<A>, JpaEreignisMetaDaten> umschlagErzeugen(final Domänenereignis<A> ereignis) {

        final JpaEreignisMetaDaten meta = new JpaEreignisMetaDaten(
                this.getName(),
                this.getVersion());

        return new JpaDomänenereignisUmschlag<>(ereignis, meta);
    }
}
