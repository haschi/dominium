package org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.SOURCE)
public @interface AbgegrenzterKontext
{
    String value();
}
