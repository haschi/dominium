package de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene;

import org.junit.Assert;
import org.junit.Test;

public class AggregatwurzelBewirktRückruf {

    @Test
    public final void rückruf() {
        final TestAggregat aggregat = new TestAggregat();
        aggregat.ereignisWerfen();

        Assert.assertTrue(aggregat.ereignisGeworfen());
    }
}
