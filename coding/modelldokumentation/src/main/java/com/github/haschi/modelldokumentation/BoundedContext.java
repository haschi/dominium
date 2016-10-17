package com.github.haschi.modelldokumentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoundedContext
{
    public String getPackageName()
    {
        return this.packageName;
    }

    private final String packageName;
    private final String name;
    private final List<String> anweisungen = new ArrayList<>();
    private final List<String> ereignisse = new ArrayList<>();
    private final List<String> informationen = new ArrayList<>();

    public Map<String, List<String>> getModell()
    {
        return this.modell;
    }

    private final Map<String, List<String>> modell = new HashMap<>();

    public BoundedContext(final String packageName, final String name)
    {
        this.packageName = packageName;
        this.name = name;

        this.modell.put("com.github.haschi.modeling.de.Anweisung", this.anweisungen);
        this.modell.put("com.github.haschi.modeling.de.Ereignis", this.ereignisse);
        this.modell.put("com.github.haschi.modeling.de.Information", this.informationen);
    }

    public String getName()
    {
        return this.name;
    }

    public List<String> getAnweisungen()
    {
        return this.anweisungen;
    }

    public List<String> getEreignisse()
    {
        return this.ereignisse;
    }

    public List<String> getInformationen()
    {
        return this.informationen;
    }
}
