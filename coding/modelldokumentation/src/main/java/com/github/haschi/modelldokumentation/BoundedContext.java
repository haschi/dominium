package com.github.haschi.modelldokumentation;

import com.sun.javadoc.PackageDoc;
import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BoundedContext
{
    BoundedContext(final PackageDoc p)
    {
        this.packageName = p.name();

        this.name = new ContextName(p);

        this.modell.put("com.github.haschi.modeling.de.Anweisung", this.anweisungen);
        this.modell.put("com.github.haschi.modeling.de.Ereignis", this.ereignisse);
        this.modell.put("com.github.haschi.modeling.de.Information", this.informationen);
    }

    void writeModel(final HtmlWriter htmlWriter) throws CanNotCreateDocument
    {
        final VelocityContext context = new VelocityContext();
        context.put("anweisungen", getAnweisungen());
        context.put("ereignisse", getEreignisse());
        context.put("informationen", getInformationen());
        context.put("kontext", getName());

        htmlWriter.writeHtmlFile(context);
    }

    String getPackageName()
    {
        return this.packageName;
    }

    private final String packageName;
    private final ContextName name;
    private final List<String> anweisungen = new ArrayList<>();
    private final List<String> ereignisse = new ArrayList<>();
    private final List<String> informationen = new ArrayList<>();

    Map<String, List<String>> getModell()
    {
        return this.modell;
    }

    private final Map<String, List<String>> modell = new HashMap<>();

    public String getName()
    {
        return this.name.toString();
    }

    private List<String> getAnweisungen()
    {
        return this.anweisungen;
    }

    private List<String> getEreignisse()
    {
        return this.ereignisse;
    }

    private List<String> getInformationen()
    {
        return this.informationen;
    }

    public String getId()
    {
        final String[] path = this.packageName.split("\\.");
        return path[path.length - 1];
    }
}
