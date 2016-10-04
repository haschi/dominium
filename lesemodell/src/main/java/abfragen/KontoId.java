package abfragen;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class KontoId
        implements Serializable
{

    private static final long serialVersionUID = 5303970782646091575L;

    @Column(columnDefinition = "BINARY(16)")
    UUID haushaltsbuch;

    @Column(columnDefinition = "VARCHAR(128)")
    String kontoname;
}
