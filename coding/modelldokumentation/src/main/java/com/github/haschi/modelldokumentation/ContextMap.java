package com.github.haschi.modelldokumentation;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

class ContextMap
{
    private static final Logger logger = Logger.getLogger(ContextMap.class.getName());
    private final List<BoundedContext> context = new ArrayList<>();

    static ContextMap create(final RootDoc root)
    {
        final ContextMap contextMap = new ContextMap();

        final List<BoundedContext> boundedContexts = Arrays.stream(root.specifiedPackages())
                .filter(ContextName::istAbgegrenzterKontext)
                .map(BoundedContext::new)
                .collect(Collectors.toList());

        for (final BoundedContext boundedContext : boundedContexts)
        {
            contextMap.add(boundedContext);
            logger.log(
                    Level.INFO,
                    String.format("Kontext: %s in %s", boundedContext.getName(), boundedContext.getPackageName()));
        }

        contextMap.modellErbauen(root);

        return contextMap;
    }

    private void modellErbauen(final RootDoc root)
    {
        final List<Modellelement> modellelements = Arrays.stream(root.classes())
                .filter(classDoc -> Optional.ofNullable(Arrays.stream(classDoc.annotations())
                        .reduce(null, ReduceToModellelementannotation(this, classDoc))).isPresent())
                .map(classDoc -> new Modellelement(
                        classDoc,
                        Arrays.stream(classDoc.annotations())
                                .reduce(null, ReduceToModellelementannotation(this, classDoc))))
                .collect(Collectors.toList());

        for (final Modellelement modellelement : modellelements)
        {
            getModel(modellelement.getClassDoc().containingPackage().name()).getModell()
                    .get(modellelement.getAnnotationDesc().annotationType().qualifiedName())
                    .add(modellelement.getDescription());
        }
    }

    private static BinaryOperator<AnnotationDesc> ReduceToModellelementannotation(
            final ContextMap cm, final ClassDoc classDoc)
    {
        return (a, b) ->
        {
            if (cm.getModel(classDoc.containingPackage().name())
                    .getModell()
                    .keySet()
                    .contains(b.annotationType().qualifiedName()))
            {
                if (a == null)
                {
                    return b;
                }
                else
                {
                    throw new IllegalStateException("Mehr als eine Modellannotation gefunden");
                }
            }
            else
            {
                return a;
            }
        };
    }

    public void add(final BoundedContext boundedContext)
    {
        this.context.add(boundedContext);
    }

    private BoundedContext getModel(final String packageName)
    {
        for (final BoundedContext boundedContext : this.context)
        {
            if (packageName.startsWith(boundedContext.getPackageName()))
            {
                return boundedContext;
            }
        }

        throw new IllegalStateException(packageName);
    }

    List<BoundedContext> contexts()
    {
        return this.context;
    }
}
