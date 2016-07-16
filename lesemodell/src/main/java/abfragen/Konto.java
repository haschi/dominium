package abfragen;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Konto {

    @EmbeddedId KontoId id;
    int kontoart;
    BigDecimal saldo;
    String währung;
}

