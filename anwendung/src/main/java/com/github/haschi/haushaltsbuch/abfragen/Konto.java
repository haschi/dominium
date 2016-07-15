package com.github.haschi.haushaltsbuch.abfragen;

import com.github.haschi.haushaltsbuch.api.Kontoart;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Konto {

    @EmbeddedId KontoId id;
    Kontoart kontoart;
    BigDecimal saldo;
    String währung;
}

