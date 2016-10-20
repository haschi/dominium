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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

        final List<Modellelement> modellelements = Arrays.stream(root.classes())
                .filter(classDoc -> Optional.ofNullable(Arrays.stream(classDoc.annotations())
                        .reduce(null, ReduceToModellelementannotation(cm, classDoc))).isPresent())
                .map(classDoc -> new Modellelement(
                        classDoc,
                        Arrays.stream(classDoc.annotations())
                                .reduce(null, ReduceToModellelementannotation(cm, classDoc))))
                .collect(Collectors.toList());

        for (final Modellelement modellelement : modellelements)
        {
            final String description = modellelement.getDescription();

            logger.info(modellelement.getClassDoc().name() + " heißt " + description);

            cm.getModel(modellelement.getClassDoc().containingPackage().name())
                    .getModell()
                    .get(modellelement.getAnnotationDesc().annotationType().qualifiedName())
                    .add(modellelement.getDescription());
        }

        //        Arrays.stream(root.classes()).forEach(classDoc ->
        //        {
        //            for (final AnnotationDesc annotation : classDoc.annotations())
        //            {
        //                final BoundedContext model = cm.getModel(classDoc.containingPackage().name());
        //                for (final String modellelement : model.getModell().keySet())
        //                {
        //                    final List<String> col = cm.getModel(classDoc.containingPackage().name())
        //                            .getModell()
        //                            .get(modellelement);
        //                    if (annotation.annotationType().qualifiedName().equals(modellelement))
        //                    {
        //                        boolean gefunden = false;
        //                        for (final AnnotationDesc.ElementValuePair elementValuePair : annotation
        // .elementValues())
        //                        {
        //                            if (ContextName.istAnnotationValue(elementValuePair))
        //                            {
        //                                final String name = elementValuePair.value().value().toString();
        //                                col.add(name);
        //                                gefunden = true;
        //                            }
        //                        }
        //
        //                        if (!gefunden)
        //                        {
        //                            col.add(stream(classDoc.name()
        //                                    .split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")).collect
        // (Collectors.joining(
        //                                    " ")));
        //                        }
        //                    }
        //                }
        //            }
        //        });

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
}
