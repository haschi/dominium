package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Domänenereignis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class JpaDomänenereignis<T> extends Domänenereignis<T> {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    UUID id;

    public JpaDomänenereignis() {
        this.id = UUID.randomUUID();
    }
}
