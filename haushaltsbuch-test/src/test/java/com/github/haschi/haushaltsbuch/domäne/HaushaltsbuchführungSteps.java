package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;

import java.util.UUID;

public class HaushaltsbuchführungSteps implements AbstractHaushaltsbuchführungSteps
{
    @Override
    public void beginnen()
    {
    }

    @Override
    public void hauptbuchAngelegt(final UUID haushaltsbuch, final UUID hauptbuch)
    {
    }

    @Override
    public UUID aktuellesHaushaltsbuch()
    {
        return null;
    }

    @Override
    public UUID aktuellesHauptbuch()
    {
        return null;
    }
}
