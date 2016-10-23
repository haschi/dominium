package com.github.haschi.modelldokumentation;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.FileWriter;

class HtmlWriter
{
    private final String template;
    private final String outputFileName;

    HtmlWriter(final String template, final String outputFileName)
    {
        this.template = template;
        this.outputFileName = outputFileName;
    }

    void writeHtmlFile(final VelocityContext context) throws CanNotCreateDocument
    {
        try
        {
            final Template template = Velocity.getTemplate(this.template);
            try (final FileWriter writer = new FileWriter(this.outputFileName))
            {
                template.merge(context, writer);
            }
        } catch (final Exception e)
        {
            throw new CanNotCreateDocument(e);
        }
    }

}
