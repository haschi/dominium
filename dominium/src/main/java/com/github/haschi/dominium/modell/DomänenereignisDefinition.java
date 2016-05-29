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
    build = "erzeugen",
    builder = "erbauer",
    privateNoargConstructor = true,
    typeBuilder = "Erbauer",
    typeInnerBuilder = "Erbauer",
    visibility = Value.Style.ImplementationVisibility.PUBLIC,
    builderVisibility = Value.Style.BuilderVisibility.SAME)
public @interface DomänenereignisDefinition {

}
