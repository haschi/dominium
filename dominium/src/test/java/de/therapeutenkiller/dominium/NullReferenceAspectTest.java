package de.therapeutenkiller.dominium;

import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class NullReferenceAspectTest {

    public void methode(final String parameter) {}

    @Test(expected = ArgumentIstNullException.class)
    public final void methode_darf_nicht_mit_null_aufgerufen_werden() {
        this.methode(null);
    }
}
