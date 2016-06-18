package com.github.haschi.coding.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;

class AggregateIdentifierGenerator {

    private final VariableElement aggregateIdentifier;

    AggregateIdentifierGenerator(final VariableElement aggregateIdentifier) {
        super();

        this.aggregateIdentifier = aggregateIdentifier;
    }

    FieldSpec getFieldSpec() {
        return FieldSpec.builder(
            this.type(),
            this.name().toString(),
            Modifier.PRIVATE,
            Modifier.FINAL).build();
    }

    ParameterSpec getParameterSpec() {
        return ParameterSpec.builder(
            this.type(),
            this.name().toString(), Modifier.FINAL)
            .build();
    }

    public Name name() {
        return this.aggregateIdentifier.getSimpleName();
    }

    public TypeName type() {
        return ClassName.get(this.aggregateIdentifier.asType());
    }
}
