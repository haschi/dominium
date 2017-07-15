package testsupport;

import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableLegeKontoMitAnfangsbestandAn;
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;

import javax.money.MonetaryAmount;
import java.util.function.Function;

public class Kontostand
{

    public String kontoname;

    @XStreamConverter(MoneyConverter.class)
    public MonetaryAmount betrag;

    public Kontoart kontoart;

    public static Function<Kontostand, ImmutableLegeKontoMitAnfangsbestandAn> alsKontoMitKontostandAnlegenKommando
            (final Aggregatkennung haushaltsbuchId)
    {

        return (Kontostand kontostand) -> ImmutableLegeKontoMitAnfangsbestandAn.builder()
                .haushaltsbuchId(haushaltsbuchId)
                .kontobezeichnung(kontostand.kontoname)
                .kontoart(kontostand.kontoart)
                .betrag(kontostand.betrag)
                .build();
    }
}
