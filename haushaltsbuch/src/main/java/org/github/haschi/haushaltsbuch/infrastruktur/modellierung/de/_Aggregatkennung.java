package org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@Eingehüllt
@JsonSerialize(using = AggregatkennungSerialisierer.class, as = Aggregatkennung.class)
@JsonDeserialize(using = AggregatkennungDeserialisierer.class, as = Aggregatkennung.class)
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
