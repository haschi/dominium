package com.github.haschi.haushaltsbuch.fixture;

import com.github.haschi.haushaltsbuch.api.Aggregatkennung;

import java.util.stream.Stream;

public interface Ereignisquelle
{
    Stream<Object> ereignisseLesen(Aggregatkennung aggregatkennung);
}
