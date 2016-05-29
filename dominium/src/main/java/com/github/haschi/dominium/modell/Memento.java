package com.github.haschi.dominium.modell;

import org.immutables.value.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Value.Style(typeBuilder = "*Erbauer", defaultAsDefault = true,
    privateNoargConstructor = true,
    builder = "builder",
    defaults = @Value.Immutable(intern = true),
    visibility = Value.Style.ImplementationVisibility.PRIVATE,
    builderVisibility = Value.Style.BuilderVisibility.PACKAGE)
public @interface Memento {

}
