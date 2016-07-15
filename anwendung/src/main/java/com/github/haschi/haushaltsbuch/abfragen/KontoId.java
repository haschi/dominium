package com.github.haschi.haushaltsbuch.abfragen;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class KontoId {

    @Column(columnDefinition = "BINARY(16)")
    UUID haushaltsbuch;

    @Column(columnDefinition = "VARCHAR(128)")
    String kontoname;
}
