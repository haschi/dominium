package com.github.haschi.modeling.de;

import org.immutables.value.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Value.Style(jdkOnly = true,
        // siehe https://github.com/immutables/immutables/issues/222
        defaultAsDefault = true,
        privateNoargConstructor = true,
        visibility = Value.Style.ImplementationVisibility.PUBLIC)
public @interface Anweisung
{
    String value() default "";
}
