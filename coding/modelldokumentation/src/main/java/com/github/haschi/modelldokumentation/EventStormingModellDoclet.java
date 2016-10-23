package com.github.haschi.modelldokumentation;

import com.sun.javadoc.RootDoc;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EventStormingModellDoclet
{
    private static final Logger logger = Logger.getLogger(EventStormingModellDoclet.class.getName());

    public static boolean start(final RootDoc root)
    {
        final ContextMap contextMap = ContextMap.create(root);

        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        try
        {
            Velocity.init();

        } catch (final Exception e)
        {
            logger.log(Level.SEVERE, e.getMessage());
            return false;
        }

        try
        {
            contextMap.writeIndex(new HtmlWriter("index.vm", "index.html"));
            for (final BoundedContext boundedContext : contextMap.contexts())
            {
                boundedContext.writeModel(new HtmlWriter("modell.vm", boundedContext.getId() + ".html"));

            }
        } catch (final CanNotCreateDocument canNotCreateDocument)
        {
            canNotCreateDocument.printStackTrace();
            return false;
        }

        return true;
    }

}
