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
public abstract class JpaSchnappschuss<T> extends Schnappschuss<T, UUID> {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    public JpaSchnappschuss() {
        this.id = UUID.randomUUID();
    }
}
