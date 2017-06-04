package com.github.haschi.haushaltsbuch.api;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Kontobezeichnung
{

    private static final Pattern gültigerName = Pattern.compile(
            "[\\p{IsLatin}]{1,2}|[\\p{Alpha}][\\p{Alpha}\\p{Space}]{1,126}[\\p{Alpha}]",
            Pattern.UNICODE_CHARACTER_CLASS);

    private final String wert;

    private Kontobezeichnung(final String kontobezeichnung)
    {
        super();

        if (!gültigerName.matcher(kontobezeichnung).matches())
        {
            throw new KontobezeichnungWarUngültig();
        }

        this.wert = kontobezeichnung;
    }

    public static Kontobezeichnung of(final String kontobezeichnung)
    {
        return new Kontobezeichnung(kontobezeichnung);
    }

    @Override
    public boolean equals(final Object objekt)
    {
        if (this == objekt)
        {
            return true;
        }

        if (!(objekt instanceof Kontobezeichnung))
        {
            return false;
        }

        final Kontobezeichnung anderes = (Kontobezeichnung) objekt;

        return Objects.equals(this.wert, anderes.wert);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.wert);
    }

    @Override
    public String toString()
    {
        return this.wert;
    }
}
