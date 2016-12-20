package domaene;

import com.github.haschi.haushaltsbuch.api.kommando.BucheEinnahme;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBucheEinnahme;
import cucumber.api.Transform;
import cucumber.api.java.de.Wenn;
import org.axonframework.commandhandling.gateway.CommandGateway;
import testsupport.DieWelt;
import testsupport.MoneyConverter;

import javax.inject.Inject;
import javax.money.MonetaryAmount;

public final class EinnahmeBuchenSteps
{
    private final CommandGateway commandGateway;

    private final DieWelt welt;

    @Inject
    public EinnahmeBuchenSteps(final CommandGateway commandGateway, final DieWelt welt)
    {
        this.commandGateway = commandGateway;
        this.welt = welt;
    }

    @Wenn("^ich meine Einnahme von (-?\\d+,\\d{2} [A-Z]{3}) per \"([^\"]*)\" an \"([^\"]*)\" buche$")
    public void ich_meine_einnahme_per_an_buche(
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag,
            final String sollkonto,
            final String habenkonto)
    {

        final BucheEinnahme kommando = ImmutableBucheEinnahme.builder()
                .haushaltsbuchId(this.welt.getAktuelleHaushaltsbuchId())
                .sollkonto(sollkonto)
                .habenkonto(habenkonto)
                .geldbetrag(währungsbetrag)
                .build();

        this.commandGateway.sendAndWait(kommando);
    }
}
