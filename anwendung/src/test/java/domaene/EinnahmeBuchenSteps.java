package domaene;

import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBucheEinnahme;
import testsupport.DieWelt;
import testsupport.MoneyConverter;
import cucumber.api.Transform;
import cucumber.api.java.de.Wenn;
import com.github.haschi.haushaltsbuch.api.kommando.BucheEinnahme;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.inject.Inject;
import javax.money.MonetaryAmount;

public final class EinnahmeBuchenSteps {

    @Inject
    private DieWelt kontext;

    @Inject
    private CommandGateway commandGateway;

    @Wenn("^ich meine Einnahme von (-?\\d+,\\d{2} [A-Z]{3}) per \"([^\"]*)\" an \"([^\"]*)\" buche$")
    public void ich_meine_einnahme_per_an_buche(
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag,
            final String sollkonto,
            final String habenkonto) {

        final BucheEinnahme kommando = ImmutableBucheEinnahme.builder()
            .haushaltsbuchId(this.kontext.getAktuelleHaushaltsbuchId())
            .sollkonto(sollkonto)
            .habenkonto(habenkonto)
            .waehrungsbetrag(währungsbetrag)
            .build();

        this.commandGateway.sendAndWait(kommando);
    }
}
