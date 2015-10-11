package de.therapeutenkiller.haushaltsbuch.aspekte;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mhaschka on 11.10.15.
 */
@Target( {ElementType.PARAMETER })
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface CanBeNull {}
