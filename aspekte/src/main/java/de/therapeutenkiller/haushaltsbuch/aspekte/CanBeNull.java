package de.therapeutenkiller.haushaltsbuch.aspekte;

import java.lang.annotation.*;

/**
 * Created by mhaschka on 11.10.15.
 */
@Target( {ElementType.PARAMETER })
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface CanBeNull {}
