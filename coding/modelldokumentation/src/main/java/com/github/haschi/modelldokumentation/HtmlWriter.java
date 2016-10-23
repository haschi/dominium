package com.github.haschi.modelldokumentation;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.FileWriter;

public class HtmlWriter
{
    private final String name;
    private final String s;

    public HtmlWriter(final String name, final String s)
    {
        this.name = name;
        this.s = s;
    }

    void writeHtmlFile(final VelocityContext context) throws CanNotCreateDocument
    {
        try
        {
            final Template template = Velocity.getTemplate(getName());
            try (final FileWriter writer = new FileWriter(getS()))
            {
                template.merge(context, writer);
            }
        } catch (final Exception e)
        {
            throw new CanNotCreateDocument(e);
        }
    }

    public String getName()
    {
        return this.name;
    }

    public String getS()
    {
        return this.s;
    }
}
