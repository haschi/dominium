package org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de;

import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;
import org.immutables.value.Value;

@Value.Style(
        // Detect names starting with underscore
        typeAbstract = "_*",
        // Generate without any suffix, just raw detected name
        typeImmutable = "*",
        // Make generated public, leave underscored as package private
        visibility = Value.Style.ImplementationVisibility.PUBLIC,
        passAnnotations = {XStreamConverter.class},
        // Seems unnecessary to have builder or superfluous copy method
        defaults = @Value.Immutable(builder = false, copy = false))
public @interface Eingehüllt
{
}
