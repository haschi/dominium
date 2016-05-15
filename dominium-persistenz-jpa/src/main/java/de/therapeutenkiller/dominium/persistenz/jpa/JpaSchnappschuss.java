package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Schnappschuss;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class JpaSchnappschuss<T> implements Schnappschuss<T, UUID> {

    private static final long serialVersionUID = -5757129473844941841L;

    @SuppressWarnings("FieldCanBeLocal")
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private final UUID id;

    public JpaSchnappschuss() {
        super();
        this.id = UUID.randomUUID();
    }
}
