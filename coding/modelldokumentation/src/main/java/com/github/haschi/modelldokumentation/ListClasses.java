package com.github.haschi.modelldokumentation;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.FileWriter;
import java.util.*;
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

        final List<String> anweisungen = new ArrayList<>();
        final List<String> ereignisse = new ArrayList<>();
        final List<String> informationen = new ArrayList<>();

        final Map<String, List<String>> modell = new HashMap<>();

        modell.put("com.github.haschi.modeling.de.Anweisung", anweisungen);
        modell.put("com.github.haschi.modeling.de.Ereignis", ereignisse);
        modell.put("com.github.haschi.modeling.de.Information", informationen);

        for (final ClassDoc classDoc : root.classes())
        {
            for (final AnnotationDesc annotation : classDoc.annotations())
            {
                for (final String modellelement : modell.keySet())
                {
                    final List<String> col = modell.get(modellelement);
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

        try
        {
            Velocity.init();
            final VelocityContext context = new VelocityContext();
            context.put("anweisungen", anweisungen);
            context.put("ereignisse", ereignisse);
            context.put("informationen", informationen);

            final Template template = Velocity.getTemplate("Modell.html.vm");

            try (final FileWriter writer = new FileWriter("modell.html"))
            {
                template.merge(context, writer);
            }

        } catch (final Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }
}
