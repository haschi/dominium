package testsupport;

import com.github.haschi.haushaltsbuch.api.Habensaldo;
import cucumber.api.Transformer;

public final class HabensaldoConverter
        extends Transformer<Habensaldo>
{

    @Override
    public Habensaldo transform(final String währungsbetrag)
    {
        final MoneyConverter converter = new MoneyConverter();
        return new Habensaldo(converter.transform(währungsbetrag));
    }
}
