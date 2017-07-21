package com.github.haschi.haushaltsbuch.fixture;

import com.github.haschi.haushaltsbuch.api.Aggregatkennung;

import java.util.stream.Stream;

/**
 * Created by matthias on 21.07.17.
 */
public interface Ereignisquelle
{
    <T> Stream<Object> ereignisseLesen(Aggregatkennung aggregatkennung);
}
