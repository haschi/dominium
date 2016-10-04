package com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis;

import com.github.haschi.haushaltsbuch.domaene.aggregat.Saldo;
import org.immutables.value.Value;

@Value.Immutable
public interface SaldoWurdeGeaendert
        extends HaushaltsbuchEreignis
{

    String kontoname();

    Saldo neuerSaldo();
}
