package com.github.haschi.modelldokumentation;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.PackageDoc;

import java.util.Arrays;

class ContextName
{
    private final PackageDoc packageDoc;

    ContextName(final PackageDoc packageDoc)
    {
        this.packageDoc = packageDoc;
    }

    @Override
    public String toString()
    {
        return Arrays.stream(this.packageDoc.annotations())
                .filter(ContextName::istAbgegrenzterKontext)
                .flatMap(a -> Arrays.stream(a.elementValues()))
                .reduce((a, b) ->
                {
                    if (ContextName.istAnnotationValue(b))
                    {
                        return b;
                    }
                    else
                    {
                        return a;
                    }
                })
                .orElseThrow(() -> new IllegalStateException("Interner Fehler: Unbekannter Kontext"))
                .value()
                .value()
                .toString();
    }

    static boolean istAbgegrenzterKontext(final PackageDoc p)
    {
        return Arrays.stream(p.annotations()).anyMatch(ContextName::istAbgegrenzterKontext);
    }

    static boolean istAnnotationValue(final AnnotationDesc.ElementValuePair elementValuePair)
    {
        return elementValuePair.element().name().equals("value");
    }

    private static boolean istAbgegrenzterKontext(final AnnotationDesc annotationDesc)
    {
        return annotationDesc.annotationType()
                .qualifiedName()
                .equals("com.github.haschi.modeling.de.AbgegrenzterKontext");
    }
}
