package com.github.haschi.modelldokumentation;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.RootDoc;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class ListClasses
{
    public static boolean start(final RootDoc root)
    {
        System.out.println("================================");
        for (final String[] strings : root.options())
        {
            for (final String string : strings)
            {
                System.out.println("Optionen: " + string);
            }
        }
        System.out.println("================================");

        final ContextMap cm = new ContextMap();

        for (final PackageDoc packageDoc : root.specifiedPackages())
        {
            fallsAbgegrenztenKontext(packageDoc, a ->
            {
                abgegrenztenKontextHinzufügen(cm, a, packageDoc.name());
            });
        }

        for (final ClassDoc classDoc : root.classes())
        {
            for (final AnnotationDesc annotation : classDoc.annotations())
            {
                for (final String modellelement : cm.getModel(classDoc.containingPackage().name()).getModell().keySet())
                {
                    final List<String> col = cm.getModel(classDoc.containingPackage().name())
                            .getModell()
                            .get(modellelement);
                    if (annotation.annotationType().qualifiedName().equals(modellelement))
                    {
                        boolean gefunden = false;
                        for (final AnnotationDesc.ElementValuePair elementValuePair : annotation.elementValues())
                        {
                            if (istAnnotationValue(elementValuePair))
                            {
                                final String name = elementValuePair.value().value().toString();
                                col.add(name);
                                gefunden = true;
                            }
                        }

                        if (!gefunden)
                        {
                            col.add(stream(classDoc.name()
                                    .split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")).collect(Collectors.joining(
                                    " ")));
                        }
                    }
                }

            }
        }

        for (final BoundedContext c : cm.contexts())
        {

            try
            {
                Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
                Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

                Velocity.init();
                final VelocityContext context = new VelocityContext();
                context.put("anweisungen", c.getAnweisungen());
                context.put("ereignisse", c.getEreignisse());
                context.put("informationen", c.getInformationen());
                context.put("kontext", c.getName());

                final Template template = Velocity.getTemplate("modell.vm");

                try (final FileWriter writer = new FileWriter("index.html"))
                {
                    template.merge(context, writer);
                }

            } catch (final Exception e)
            {
                e.printStackTrace();
            }

        }
        return true;
    }

    private static void fallsAbgegrenztenKontext(
            final PackageDoc packageDoc, final Consumer<AnnotationDesc> annotationDescConsumer)
    {
        Arrays.stream(packageDoc.annotations())
                .filter(ListClasses::istAbgegrenzterKontext)
                .reduce(maximalEinExemplar())
                .ifPresent(annotationDescConsumer);
    }

    private static BinaryOperator<AnnotationDesc> maximalEinExemplar()
    {
        return (a, b) ->
        {
            throw new IllegalStateException();
        };
    }

    private static BinaryOperator<String> maximalEinWert()
    {
        return (a, b) ->
        {
            throw new IllegalStateException();
        };
    }

    private static void abgegrenztenKontextHinzufügen(
            final ContextMap cm, final AnnotationDesc a, final String packageName)
    {
        final String kontextName = stream(a.elementValues()).filter(ListClasses::istAnnotationValue)
                .map(evp -> evp.value().value().toString())
                .reduce(maximalEinWert())
                .get();

        cm.add(new BoundedContext(packageName, kontextName));
    }

    private static boolean istAnnotationValue(final AnnotationDesc.ElementValuePair elementValuePair)
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
