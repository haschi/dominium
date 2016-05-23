package com.github.haschi.dominium.persistenz.tools

import com.github.haschi.dominium.persistenz.Ereignisstrom
import groovy.transform.CompileStatic

@CompileStatic
class TestEreignisstrom extends Ereignisstrom<String, TestEreignisMetaDaten> {
    TestEreignisstrom(String streamName) {
        super(streamName)
    }

    @Override
    public long getVersion() {
        return super.version
    }

    @Override
    String getIdentitätsmerkmal() {
        return super.identitätsmerkmal
    }

    @Override
    protected <A> TestDomänenereignisUmschlag<A> umschlagErzeugen(final A ereignis) {
        return new TestDomänenereignisUmschlag(ereignis, this.version, this.identitätsmerkmal);
    }
}
