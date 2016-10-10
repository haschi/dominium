package com.github.haschi.haushaltsbuch.api.ereignis;

import com.github.haschi.modeling.de.Ereignis;

import java.util.UUID;

@Ereignis
public interface JournalWurdeAngelegt
        extends HaushaltsbuchEreignis
{

    UUID aktuelleHaushaltsbuchId();
}
