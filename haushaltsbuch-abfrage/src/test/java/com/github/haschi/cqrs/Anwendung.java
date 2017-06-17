package com.github.haschi.cqrs;

import org.apache.commons.lang3.tuple.Pair;
import org.axonframework.config.Configuration;
import org.axonframework.config.ModuleConfiguration;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by matthias on 17.06.17.
 */
public class Anwendung
{
    public Stream<Function<Configuration, Object>> commandHandler()
    {
        return null;
    }

    public Stream<ModuleConfiguration> module()
    {
        return null;
    }

    public Stream<Pair<Class<?>, Function<Configuration, ?>>> komponenten()
    {
        return null;
    }
}
