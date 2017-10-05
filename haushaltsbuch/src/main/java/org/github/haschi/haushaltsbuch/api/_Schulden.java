package org.github.haschi.haushaltsbuch.api;

import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Eingehüllt;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Umhüller;
import org.immutables.value.Value;
import org.javamoney.moneta.function.MonetaryFunctions;

import java.util.Collections;
import java.util.List;

@Value.Immutable
@Eingehüllt
public abstract class _Schulden extends Umhüller<List<Schuld>>
{
    public Währungsbetrag summe()
    {
        return Währungsbetrag.of(
                wert().stream()
                        .map(m -> m.währungsbetrag().wert())
                        .reduce(MonetaryFunctions.sum())
                        .orElse(Währungsbetrag.NullEuro().wert()));
    }

    public static Schulden leer()
    {
        return Schulden.of(Collections.emptyList());
    }
}
