package com.github.haschi.haushaltsbuch.domaene.testsupport;

import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAbgelehnt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.ImmutableBuchungWurdeAbgelehnt;
import cucumber.api.Transformer;

public final class BuchungWurdeAbgelehntTransformer extends Transformer<BuchungWurdeAbgelehnt> {

    @Override
    public BuchungWurdeAbgelehnt transform(final String grund) {
        return ImmutableBuchungWurdeAbgelehnt.builder()
            .grund(grund)
            .build();
    }
}
