package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.domaene.abfrage.KontoSaldieren;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Habensaldo;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Saldo;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Sollsaldo;
import de.therapeutenkiller.haushaltsbuch.domaene.api.AusgabeBuchenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HaushaltsbuchAggregatKontext;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.Kontostand;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Singleton
public final class AusgabeBuchenSteps {

    private final KontoSaldieren kontoSaldieren;
    private final HaushaltsbuchAggregatKontext kontext;

    @Inject
    AusgabeBuchenSteps(
            final HaushaltsbuchAggregatKontext kontext,
            final KontoSaldieren kontoSaldieren) {
        this.kontext = kontext;
        this.kontoSaldieren = kontoSaldieren;
    }

    @Wenn("^ich meine Ausgabe von (-?\\d+,\\d{2} [A-Z]{3}) per \"([^\"]*)\" an \"([^\"]*)\" buche$")
    public void wenn_ich_meine_ausgabe_buche(
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag,
            final String sollkonto,
            final String habenkonto)  {

        this.kontext.kommandoAusführen(new AusgabeBuchenKommando(
                this.kontext.aktuellesHaushaltsbuch(),
                sollkonto,
                habenkonto,
                währungsbetrag
        ));
    }

    @Dann("^werde ich folgende Kontostände erhalten:$")
    public void dann_werde_ich_folgende_Kontostände_erhalten(final List<Kontostand> kontostände) { // NOPMD Dataflow

        for (final Kontostand kontostand : kontostände) {
            final Saldo saldo = this.kontoSaldieren.ausführen(
                    this.kontext.aktuellesHaushaltsbuch(),
                    kontostand.kontoname);

            // TODO Besser in einem Konverter
            final Saldo erwartetesSaldo = saldoFürKonto(kontostand);
            assertThat(saldo).isEqualTo(erwartetesSaldo); // NOPMD
        }
    }

    private static Saldo saldoFürKonto(final Kontostand kontostand) {
        if (kontostand.kontoart.equals(Kontoart.Aktiv)) { //NOPMD LoD TODO
            return new Habensaldo(kontostand.betrag); // NOPMD
        } else if (kontostand.kontoart.equals(Kontoart.Ertrag)) { //NOPMD LoD TODO
            return new Sollsaldo(kontostand.betrag); //NOPMD LoD TODO
        } else if (kontostand.kontoart.equals(Kontoart.Aufwand)) { //NOPMD LoD TODO
            return new Habensaldo(kontostand.betrag); //NOPMD LoD TODO
        }
        return null;
    }
}
