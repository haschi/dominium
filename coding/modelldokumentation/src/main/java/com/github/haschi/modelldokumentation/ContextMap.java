package com.github.haschi.modelldokumentation;

import java.util.ArrayList;
import java.util.List;

public class ContextMap
{
    private final List<BoundedContext> context = new ArrayList<>();

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
