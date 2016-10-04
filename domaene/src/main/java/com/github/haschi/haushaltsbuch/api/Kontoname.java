package com.github.haschi.haushaltsbuch.api;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Kontoname
{

    private static final Pattern gültigerName = Pattern.compile(
            "[\\p{IsLatin}]{1,2}|[\\p{Alpha}]{1}[\\p{Alpha}\\p{Space}]{1,126}[\\p{Alpha}]{1}",
            Pattern.UNICODE_CHARACTER_CLASS);

    private final String kontoname;

    private Kontoname(final String kontoname)
    {
        super();

        if (!gültigerName.matcher(kontoname).matches())
        {
            throw new KontonameWarUngültig();
        }

        this.kontoname = kontoname;
    }

    public static Kontoname of(final String kontoname)
    {
        return new Kontoname(kontoname);
    }

    @Override
    public boolean equals(final Object objekt)
    {
        if (this == objekt)
        {
            return true;
        }

        if (!(objekt instanceof Kontoname))
        {
            return false;
        }

        final Kontoname anderes = (Kontoname) objekt;

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
