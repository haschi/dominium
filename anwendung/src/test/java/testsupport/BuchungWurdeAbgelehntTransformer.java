package testsupport;

import com.github.haschi.haushaltsbuch.api.ereignis.BuchungWurdeAbgelehnt;
import com.github.haschi.haushaltsbuch.api.ereignis.ImmutableBuchungWurdeAbgelehnt;
import cucumber.api.Transformer;

public final class BuchungWurdeAbgelehntTransformer
        extends Transformer<BuchungWurdeAbgelehnt>
{

    @Override
    public BuchungWurdeAbgelehnt transform(final String grund)
    {
        return ImmutableBuchungWurdeAbgelehnt.builder().grund(grund).build();
    }
}
