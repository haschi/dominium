package com.github.haschi.coding.processor;

import com.github.haschi.coding.annotation.EventHandler;
import com.github.haschi.coding.aspekte.DarfNullSein;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner8;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Beschreibt eine annotierte AggregatRoot Klasse
 */
public class AggregateRootClass {

    private final TypeElement classElement;

    AggregateRootClass(final TypeElement classElement) {
        super();

        this.classElement = classElement;
    }

    String qualifiedName() {
        return classElement.getQualifiedName().toString();
    }

    Set<ExecutableElement> getEventHandler() {
        final EventHandlerScanner eventHandlerScanner = new EventHandlerScanner();
        eventHandlerScanner.scan(classElement, 0);

        return eventHandlerScanner.eventHandler;
    }

    public List<MethodSpec> getKonstruktoren() {
        final EventHandlerScanner eventHandlerScanner = new EventHandlerScanner();
        eventHandlerScanner.scan(classElement, 0);

        List<MethodSpec> k = new ArrayList<>();

        for (ExecutableElement konstruktor : eventHandlerScanner.konstruktoren) {

            if (konstruktor.getEnclosingElement().equals(classElement)) {
                final MethodSpec.Builder builder = MethodSpec.constructorBuilder();
                final List<String> parameternames = new ArrayList<>();
                for (VariableElement parameter : konstruktor.getParameters()) {
                    final ParameterSpec.Builder pspec = ParameterSpec.builder(
                        TypeName.get(parameter.asType()),
                        parameter.getSimpleName().toString(),
                        Modifier.FINAL);
                    builder.addParameter(pspec.build());
                    parameternames.add(parameter.getSimpleName().toString());
                }

                builder.addModifiers(Modifier.PUBLIC);
                builder.addStatement("super(" + String.join(", ", parameternames) + ")");
                k.add(builder.build());
            }
        }

        return k;
    }

    private class EventHandlerScanner extends ElementScanner8<Integer, Integer> {

        public final Set<ExecutableElement> eventHandler = new HashSet<>();
        public final Set<ExecutableElement> konstruktoren = new HashSet<>();
        @Override
        @DarfNullSein
        public Integer visitExecutable(ExecutableElement element, @DarfNullSein  Integer nichts) {
            if (element.getKind() == ElementKind.METHOD) {
                final List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
                for (AnnotationMirror am : annotationMirrors) {
                    final Element element1 = am.getAnnotationType().asElement();
                    final EventHandler annotation = element.getAnnotation(EventHandler.class);
                    if (annotation != null) {
                        this.eventHandler.add(element);
                    }
                }
                nichts+=1;
            }

            if (element.getKind() == ElementKind.CONSTRUCTOR && element.getModifiers().contains(Modifier.PUBLIC)) {
                this.konstruktoren.add(element);
            }

            return super.visitExecutable(element, nichts);
        }
    }
}
