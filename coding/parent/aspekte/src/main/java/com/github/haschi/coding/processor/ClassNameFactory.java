package com.github.haschi.coding.processor;

import com.squareup.javapoet.ClassName;

import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

class ClassNameFactory {

    private final TypeElement aggregate;

    ClassNameFactory(final TypeElement aggregate) {
        super();

        this.aggregate = aggregate;
    }

    ClassName getAggregateRootType() {
        return ClassName.get(this.getAggregate());
    }

    private TypeElement getAggregate() {
        return this.aggregate;
    }

    String getTargetPackageName() {
        final PackageElement packageElement = (PackageElement) this.aggregate.getEnclosingElement();
        final Name sourcePackageName = packageElement.getQualifiedName();

        return sourcePackageName.toString() + ".generiert";
    }

    ClassName getAggregateRootProxyType() {
        return ClassName.get(
            this.getTargetPackageName(),
                    this.aggregate.getSimpleName().toString() + "Proxy");
    }

    ClassName getEventInterfaceName() {
        return ClassName.get(
            this.getTargetPackageName(),
            this.aggregate.getSimpleName().toString() + "Event");
    }
}
