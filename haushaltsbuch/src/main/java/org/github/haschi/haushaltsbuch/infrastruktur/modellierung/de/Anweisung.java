package org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de;

import org.immutables.value.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Value.Style(
        typeAbstract = "_*",
        typeImmutable = "*",
        jdkOnly = true,
        // siehe https://github.com/immutables/immutables/issues/222
        defaultAsDefault = true,
        privateNoargConstructor = true,
        visibility = Value.Style.ImplementationVisibility.PUBLIC)
public @interface Anweisung
{
    String value() default "";
}
