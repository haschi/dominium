package com.github.haschi.haushaltsbuch.api;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Kontobezeichnung
{

    private static final Pattern gültigerName = Pattern.compile(
            "[\\p{IsLatin}]{1,2}|[\\p{Alpha}][\\p{Alpha}\\p{Space}]{1,126}[\\p{Alpha}]",
            Pattern.UNICODE_CHARACTER_CLASS);

    private final String kontoname;

    private Kontobezeichnung(final String kontoname)
    {
        super();

        if (!gültigerName.matcher(kontoname).matches())
        {
            throw new KontobezeichnungWarUngültig();
        }

        this.kontoname = kontoname;
    }

    public static Kontobezeichnung of(final String kontoname)
    {
        return new Kontobezeichnung(kontoname);
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

        return Objects.equals(this.kontoname, anderes.kontoname);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.kontoname);
    }

    @Override
    public String toString()
    {
        return this.kontoname;
    }
}
