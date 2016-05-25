package com.github.haschi.dominium.modell;

import org.immutables.value.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@Value.Style(
    defaults = @Value.Immutable,
    typeAbstract = "*Definition",
    typeImmutable = "*",
    build = "erzeugen",
    builder = "erbauer",
    privateNoargConstructor = true,
    typeBuilder = "Erbauer",
    typeInnerBuilder = "Erbauer",
    visibility = Value.Style.ImplementationVisibility.PUBLIC)
public @interface DomänenereignisDefinition {

}
