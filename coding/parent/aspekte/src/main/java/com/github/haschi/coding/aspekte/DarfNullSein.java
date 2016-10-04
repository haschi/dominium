package com.github.haschi.coding.aspekte;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface DarfNullSein
{
}
