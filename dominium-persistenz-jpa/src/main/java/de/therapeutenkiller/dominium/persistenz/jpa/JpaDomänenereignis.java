package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Domänenereignis;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public abstract class JpaDomänenereignis<T> extends Domänenereignis<T> {

    @Id
    UUID id;

    public JpaDomänenereignis() {
        this.id = UUID.randomUUID();
    }
}
