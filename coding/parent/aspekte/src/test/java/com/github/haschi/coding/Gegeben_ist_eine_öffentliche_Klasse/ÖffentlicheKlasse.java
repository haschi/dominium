package com.github.haschi.coding.Gegeben_ist_eine_öffentliche_Klasse;

public final class ÖffentlicheKlasse
{
    public ÖffentlicheKlasse(final Object o)
    {
        super();
    }

    ÖffentlicheKlasse(final Object o1, final Object o2)
    {
        super();
    }

    private ÖffentlicheKlasse(final Object o1, final Object o2, final Object o3)
    {
        super();
    }

    public static final class Builder
    {
        public ÖffentlicheKlasse erzeugePerPrivatenKonstruktor()
        {
            return new ÖffentlicheKlasse(1, 2, 3);
        }
    }
}
