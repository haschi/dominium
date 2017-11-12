package com.github.haschi.haushaltsbuch.testing;

import org.immutables.value.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Value.Style(
        typeAbstract = "_*",
        typeImmutable = "*",
        jdkOnly = true,
        defaultAsDefault = true,
        privateNoargConstructor = false,
        jacksonIntegration = false,
        visibility = Value.Style.ImplementationVisibility.PUBLIC,
        defaults = @Value.Immutable(builder = false, copy = false)
)
public @interface EinfacherWert
{
    String value() default "";
}
