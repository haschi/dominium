package com.github.haschi.dominium.persistenz.jpa

import org.spockframework.runtime.extension.IMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.SpecInfo

class StartContainer implements IMethodInterceptor{
    private final DeltaspikeExtension deltaspikeExtension
    private final SpecInfo spec

    StartContainer(SpecInfo spec, DeltaspikeExtension deltaspikeExtension) {
        this.spec = spec
        this.deltaspikeExtension = deltaspikeExtension
    }

    @Override
    void intercept(IMethodInvocation iMethodInvocation) throws Throwable {
        //iMethodInvocation.proceed()
        //iMethodInvocation.instance
        // this.deltaspikeExtension.init()
        this.deltaspikeExtension.start(spec, iMethodInvocation)
    }
}
