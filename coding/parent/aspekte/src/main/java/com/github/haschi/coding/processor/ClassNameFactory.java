package com.github.haschi.coding.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

class ClassNameFactory {

    private static final String infrastructurePackageName = "com.github.haschi.dominium.infrastructure";
    private final TypeElement aggregate;

    ClassNameFactory(final TypeElement aggregate) {
        super();

        this.aggregate = aggregate;
    }

    ClassName getEventTypeName(final TypeMirror eventTypeMirror) {
        return ClassName.get(
            this.getTargetPackageName(),
            ((ClassName)ClassName.get(eventTypeMirror)).simpleName() + "Message");
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

    public TypeName getProxyInterface(final AggregateRootClass arc) {
        TypeName identifierType = arc.getAggregateIdentitfier().type();
        TypeName eventType = getEventInterfaceName();
        ClassName raw = ClassName.get(infrastructurePackageName, "AggregateRootProxy");

        return ParameterizedTypeName.get(raw, eventType, identifierType);
    }

    public ClassName getRepositoryType() {
        return ClassName.get(this.getTargetPackageName(), this.aggregate.getSimpleName().toString() + "Repository");
    }

    public TypeName getEventStoreType() {
        return ClassName.get(this.getTargetPackageName(), this.aggregate.getSimpleName().toString() + "EventStore");
    }
}
