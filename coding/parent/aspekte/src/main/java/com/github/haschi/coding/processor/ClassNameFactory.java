package com.github.haschi.coding.processor;

import com.squareup.javapoet.ClassName;

import javax.lang.model.element.Name;

public class ClassNameFactory {

    private final String targetPackageName;
    private final Name aggragteRootName;

    public ClassNameFactory(final String targetPackageName, final Name aggragteRootName) {
        this.targetPackageName = targetPackageName;
        this.aggragteRootName = aggragteRootName;
    }

    ClassName getAggregateRootProxyType() {
        return ClassName.get(
                    getTargetPackageName(),
                    getAggragteRootName().toString() + "Proxy");
    }

    ClassName getEventInterfaceName() {
        return ClassName.get(
            getTargetPackageName(),
            getAggragteRootName().toString() + "Event");
    }

    public String getTargetPackageName() {
        return targetPackageName;
    }

    public Name getAggragteRootName() {
        return aggragteRootName;
    }
}
