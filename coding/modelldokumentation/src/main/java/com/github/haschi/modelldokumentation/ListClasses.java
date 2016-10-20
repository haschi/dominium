package com.github.haschi.modelldokumentation;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.FileWriter;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class ListClasses
{
    private static final Logger logger = Logger.getLogger(ListClasses.class.getName());

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

        final ContextMap cm = ContextMap.create(root);

        for (final ClassDoc classDoc : root.classes())
        {
            logger.info("Klasse " + classDoc.name());
            for (final AnnotationDesc annotation : classDoc.annotations())
            {
                logger.info("Annotation " + annotation.annotationType().name());
                final BoundedContext model = cm.getModel(classDoc.containingPackage().name());
                logger.info("in Kontext " + model.getName());
                logger.info(model.getModell().keySet().stream().collect(Collectors.joining(", ")));

                for (final String modellelement : model.getModell().keySet())
                {
                    logger.info("Suche Modellelement " + modellelement);
                    final List<String> col = cm.getModel(classDoc.containingPackage().name())
                            .getModell()
                            .get(modellelement);
                    if (annotation.annotationType().qualifiedName().equals(modellelement))
                    {
                        logger.info("Modellelement " + modellelement + " gefunden");
                        boolean gefunden = false;
                        for (final AnnotationDesc.ElementValuePair elementValuePair : annotation.elementValues())
                        {
                            if (ContextName.istAnnotationValue(elementValuePair))
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

}
