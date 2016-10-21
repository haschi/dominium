package com.github.haschi.modelldokumentation;

import com.sun.javadoc.RootDoc;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        try
        {
            Velocity.init();
            final VelocityContext context = new VelocityContext();
            context.put("contexts", cm.contexts());

            final Template template = Velocity.getTemplate("index.vm");
            try (final FileWriter writer = new FileWriter("index.html"))
            {
                template.merge(context, writer);
            }
        } catch (final Exception e)
        {
            logger.log(Level.SEVERE, e.getMessage());
        }

        for (final BoundedContext c : cm.contexts())
        {

            try
            {
                final VelocityContext context = new VelocityContext();
                context.put("anweisungen", c.getAnweisungen());
                context.put("ereignisse", c.getEreignisse());
                context.put("informationen", c.getInformationen());
                context.put("kontext", c.getName());

                final Template template = Velocity.getTemplate("modell.vm");

                try (final FileWriter writer = new FileWriter(c.getId() + ".html"))
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
