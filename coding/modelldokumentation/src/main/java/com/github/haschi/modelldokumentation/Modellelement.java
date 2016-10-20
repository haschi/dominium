package com.github.haschi.modelldokumentation;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;

import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Modellelement
{
    private final Logger logger = Logger.getLogger(Modellelement.class.getName());

    public ClassDoc getClassDoc()
    {
        return this.classDoc;
    }

    private final ClassDoc classDoc;

    public AnnotationDesc getAnnotationDesc()
    {
        return this.annotationDesc;
    }

    private final AnnotationDesc annotationDesc;

    public Modellelement(final ClassDoc classDoc, final AnnotationDesc annotationDesc)
    {
        this.classDoc = classDoc;
        this.annotationDesc = annotationDesc;

        logger.info("Modellelement " + classDoc.name() + ", " + annotationDesc.annotationType().qualifiedName());
    }

    public String getDescription()
    {
        for (final AnnotationDesc.ElementValuePair elementValuePair : this.annotationDesc.elementValues())
        {
            if (ContextName.istAnnotationValue(elementValuePair))
            {
                return elementValuePair.value().value().toString();
            }
        }

        return Arrays.stream(this.classDoc.name().split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])"))
                .collect(Collectors.joining(" "));
    }
}

