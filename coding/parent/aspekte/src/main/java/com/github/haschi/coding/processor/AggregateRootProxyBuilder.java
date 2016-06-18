package com.github.haschi.coding.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;

class AggregateRootProxyBuilder {

    private final ClassNameFactory classNameFactory;
    private final AggregateRootClass aggregate;

    AggregateRootProxyBuilder(final ClassNameFactory classNameFactory, final AggregateRootClass arc) {
        super();

        this.classNameFactory = classNameFactory;
        this.aggregate = arc;
    }

    private MethodSpec hashCodeMethodSpec() {
        final AggregateIdentifierGenerator identifier = this.aggregate.getAggregateIdentitfier();
        return MethodSpec.methodBuilder("hashCode")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .returns(TypeName.INT)
            .addStatement("return new $T(17, 37).append(this.$N).toHashCode()",
                ClassName.get("org.apache.commons.lang3.builder", "HashCodeBuilder"),
                identifier.name())
            .build();
    }

    private MethodSpec equalsMethodSpec() {
        final AggregateIdentifierGenerator identifier = this.aggregate.getAggregateIdentitfier();

        return MethodSpec.methodBuilder("equals")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .returns(TypeName.BOOLEAN)
            .addParameter(Object.class, "anderes", Modifier.FINAL)
            .beginControlFlow("if (this == $N)", "anderes")
            .addStatement("return true")
            .endControlFlow()
            .beginControlFlow("if (!($N instanceof $T))", "anderes",
                this.classNameFactory.getAggregateRootProxyType())
            .addStatement("return false")
            .endControlFlow()
            .addStatement("final $T that = ($T) $N",
                this.classNameFactory.getAggregateRootProxyType(),
                this.classNameFactory.getAggregateRootProxyType(),
                "anderes")
            .addStatement("return new $T().append(this.$N, that.$N).isEquals()",
                ClassName.get("org.apache.commons.lang3.builder", "EqualsBuilder"),
                identifier.name(),
                identifier.name())
            .build();
    }

    private MethodSpec restoreMethodSpec() {
        return MethodSpec.methodBuilder("wiederherstellen")
            .addModifiers(Modifier.PUBLIC)
            .returns(void.class)
            .addParameter(
                ParameterizedTypeName.get(ClassName.get("java.util", "List"),
                    this.classNameFactory.getEventInterfaceName()),
                "ereignisse",
                Modifier.FINAL)
            .addStatement("$N.forEach(e -> e.anwendenAuf(this))", "ereignisse")
            .build();
    }

    private MethodSpec markChangesAsCommittedMethodSpec() {
        return MethodSpec.methodBuilder("markChangesAsCommitted")
            .addModifiers(Modifier.PUBLIC)
            .returns(void.class)
            .addStatement("this.$N.clear()", "changes")
            .build();
    }

    private MethodSpec getUncommittedChangesMethodSpec() {
        return MethodSpec.methodBuilder("getUncommittedChanges")
            .addModifiers(Modifier.PUBLIC)
            .returns(ParameterizedTypeName.get(ClassName.get("java.util", "List"),
                this.classNameFactory.getEventInterfaceName()))
            .addStatement("return this.$N", "changes")
            .build();
    }

    private static MethodSpec getVersionMethodSpec() {
        return MethodSpec.methodBuilder("getVersion")
            .addModifiers(Modifier.PUBLIC)
            .returns(ClassName.get("com.github.haschi.dominium.modell", "Version"))
            .addStatement("return this.$N", "version")
            .build();
    }

    private MethodSpec getIdMethodSpec() {

        final AggregateIdentifierGenerator identifier = this.aggregate.getAggregateIdentitfier();

        return MethodSpec.methodBuilder("getId")
            .addModifiers(Modifier.PUBLIC)
            .returns(identifier.type())
            .addStatement("return this.$N", identifier.name())
            .build();
    }

    private MethodSpec constructorMethodSpec() {

        final AggregateIdentifierGenerator identifier = this.aggregate.getAggregateIdentitfier();
        return MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            .addParameter(identifier.getParameterSpec())
            .addParameter(ClassName.get("com.github.haschi.dominium.modell", "Version"), "version", Modifier.FINAL)
            .addStatement("super($N)", identifier.name())
            .addStatement("this.$N = $N", "version", "version")
            .build();
    }

    List<MethodSpec> aggregateRootProxyMethods() {
        final List<MethodSpec> methods = new ArrayList<>();

        methods.add(this.constructorMethodSpec());
        methods.add(this.getIdMethodSpec());
        methods.add(getVersionMethodSpec());
        methods.add(this.getUncommittedChangesMethodSpec());
        methods.add(this.markChangesAsCommittedMethodSpec());
        methods.add(this.restoreMethodSpec());
        methods.add(this.equalsMethodSpec());
        methods.add(this.hashCodeMethodSpec());

        return methods;
    }
}
