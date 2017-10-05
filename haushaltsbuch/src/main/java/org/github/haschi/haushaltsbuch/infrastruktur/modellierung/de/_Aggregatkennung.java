package org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de;

import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@Eingehüllt
public abstract class _Aggregatkennung extends Umhüller<UUID>
{
    public static Aggregatkennung neu()
    {
        return Aggregatkennung.of(UUID.randomUUID());
    }

    public static Aggregatkennung of(final String id)
    {
        return Aggregatkennung.of(UUID.fromString(id));
    }
}
