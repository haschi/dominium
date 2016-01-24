package de.therapeutenkiller.dominium.jpa;

import de.therapeutenkiller.dominium.lagerung.Ereignisstrom;
import de.therapeutenkiller.dominium.aggregat.Domänenereignis;
import de.therapeutenkiller.dominium.lagerung.DomänenereignisUmschlag;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class JpaEreignisstrom<A> extends Ereignisstrom<A> {

    @Id
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getVersion() {
        return this.version;
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
    public final DomänenereignisUmschlag<A> umschlagErzeugen(final Domänenereignis<A> ereignis, final int version)  {
        return new JpaDomänenereignisUmschlag<>(ereignis, version, this.name);
    }
}
