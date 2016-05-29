package com.github.haschi.dominium.modell;

import org.immutables.value.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Value.Style(
    defaultAsDefault = true,
    privateNoargConstructor = true,
    visibility = Value.Style.ImplementationVisibility.PACKAGE)
public @interface Memento {

}
