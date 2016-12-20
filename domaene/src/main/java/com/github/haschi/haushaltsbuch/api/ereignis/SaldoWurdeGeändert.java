package com.github.haschi.haushaltsbuch.api.ereignis;

import com.github.haschi.haushaltsbuch.domaene.aggregat.Saldo;
import com.github.haschi.modeling.de.Ereignis;

@Ereignis
public interface SaldoWurdeGeändert
{
    String kontobezeichnung();

    Saldo neuerSaldo();
}
