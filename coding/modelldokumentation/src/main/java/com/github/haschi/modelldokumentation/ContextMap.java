package com.github.haschi.modelldokumentation;

import com.sun.javadoc.RootDoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ContextMap
{
    private static final Logger logger = Logger.getLogger(ContextMap.class.getName());
    private final List<BoundedContext> context = new ArrayList<>();

    static ContextMap create(final RootDoc root)
    {
        final ContextMap cm = new ContextMap();

        final List<BoundedContext> boundedContexts = Arrays.stream(root.specifiedPackages())
                .filter(ContextName::istAbgegrenzterKontext)
                .map(BoundedContext::new)
                .collect(Collectors.toList());

        for (final BoundedContext boundedContext : boundedContexts)
        {
            cm.add(boundedContext);
            logger.log(
                    Level.INFO,
                    String.format("Kontext: %s in %s", boundedContext.getName(), boundedContext.getPackageName()));
        }

        return cm;
    }

    public void add(final BoundedContext boundedContext)
    {
        this.context.add(boundedContext);
    }

    public BoundedContext getModel(final String packageName)
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

    public List<BoundedContext> contexts()
    {
        return this.context;
    }
}
