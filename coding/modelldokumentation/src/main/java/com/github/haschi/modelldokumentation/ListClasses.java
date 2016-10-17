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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

        final Map<String, BoundedContext> boundedContext = new ConcurrentHashMap<>();

        final ContextMap cm = new ContextMap();

        for (final PackageDoc packageDoc : root.specifiedPackages())
        {
            for (final AnnotationDesc annotationDesc : packageDoc.annotations())
            {
                if (annotationDesc.annotationType()
                        .qualifiedName()
                        .equals("com.github.haschi.modeling.de.AbgegrenzterKontext"))
                {
                    for (final AnnotationDesc.ElementValuePair elementValuePair : annotationDesc.elementValues())
                    {
                        if (elementValuePair.element().name().equals("value"))
                        {
                            final String name = elementValuePair.value().value().toString();
                            cm.add(new BoundedContext(packageDoc.name(), name));
                        }
                    }
                }
            }
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
                            if (elementValuePair.element().name().equals("value"))
                            {
                                final String name = elementValuePair.value().value().toString();
                                col.add(name);
                                gefunden = true;
                            }
                        }

                        if (!gefunden)
                        {
                            col.add(Arrays.stream(classDoc.name().split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])"))
                                    .collect(Collectors.joining(" ")));
                        }
                    }
                }

            }

            // System.out.println(classDoc.name());

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
}
