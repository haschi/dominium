package de.therapeutenkiller.haushaltsbuch.domaene;

import org.junit.Ignore;
import org.junit.Test;

import java.util.function.Function;

// @RunWith(JUnit4.class)
@Ignore
public class MilliardEuroFehlerDeaktivierenLambdaTest {

    final Function<Integer, Integer> quadrat = (Integer zahl) -> zahl * zahl;

    @Test
    public final void helloTest() {


        final Integer ergebnis = this.quadrat.apply(null);
        assert ergebnis == 16;
    }

    @Test
    public final void otherTest() {

        this.callMethod((Integer zahl) -> zahl * zahl);
    }

    private void callMethod(final Function<Integer, Integer> function) {

        final Integer ergebnis = function.apply(null);
    }
}
