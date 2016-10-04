package com.github.haschi.coding.aspekte.validation;

import com.github.haschi.coding.aspekte.DarfNullSein;
import com.github.haschi.coding.aspekte.RückgabewertIstNullException;

import java.lang.reflect.Method;

class Rückgabewert
{
    private final Method method;

    Rückgabewert(final Method method)
    {
        super();
        this.method = method;
    }

    public final void prüfen()
    {
        if (!this.method.getReturnType().equals(Void.TYPE) && (this.method.getAnnotation(DarfNullSein.class) == null))
        {
            throw new RückgabewertIstNullException(this.method);
        }
    }
}
