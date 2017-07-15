package com.github.haschi.haushaltsbuch.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@Eingehüllt
@JsonSerialize(as = Aggregatkennung.class)
@JsonDeserialize(as = Aggregatkennung.class)
public abstract class _Aggregatkennung extends Umhüller<UUID>
{
    public static Aggregatkennung neu() {
        return Aggregatkennung.of(UUID.randomUUID());
    }

    public static Aggregatkennung of(final String id)
    {
        return Aggregatkennung.of(UUID.fromString(id));
    }
}
