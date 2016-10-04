package testsupport;

import com.github.haschi.haushaltsbuch.domaene.aggregat.Sollsaldo;
import cucumber.api.Transformer;

public final class SollsaldoConverter
        extends Transformer<Sollsaldo>
{

    @Override
    public Sollsaldo transform(final String währungsbetrag)
    {
        final MoneyConverter moneyConverter = new MoneyConverter();
        return new Sollsaldo(moneyConverter.transform(währungsbetrag));
    }
}
