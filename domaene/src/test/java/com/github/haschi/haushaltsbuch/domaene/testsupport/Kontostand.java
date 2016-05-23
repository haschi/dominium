package com.github.haschi.haushaltsbuch.domaene.testsupport;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;
import com.github.haschi.haushaltsbuch.api.kommando.LegeKontoMitAnfangsbestandAn;

import javax.money.MonetaryAmount;
import java.util.UUID;
import java.util.function.Function;

public class Kontostand {

    public String kontoname;

    @XStreamConverter(MoneyConverter.class)
    public MonetaryAmount betrag;

    public Kontoart kontoart;

    public static Function<Kontostand, LegeKontoMitAnfangsbestandAn>
        alsKontoMitKontostandAnlegenKommando(final UUID haushaltsbuchId) {
        return (Kontostand kontostand) -> new LegeKontoMitAnfangsbestandAn(
                haushaltsbuchId,
                kontostand.kontoname,
                kontostand.kontoart,
                kontostand.betrag);
    }
}
