package com.github.haschi.coding.processor;

import com.github.haschi.coding.annotation.AggregateRoot;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic.Kind;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"com.github.haschi.coding.annotation.AggregateRoot"})
public class AggregateRootProcessor extends AbstractProcessor {

    private Messager messager;
    private Elements elementUtils;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment environment) {
        super.init(environment);
        this.messager = environment.getMessager();
        this.elementUtils = environment.getElementUtils();
        this.filer = environment.getFiler();
    }
    @Override
    public boolean process(final Set<? extends TypeElement> set, final RoundEnvironment roundEnvironment) {
        this.messager.printMessage(Kind.NOTE, "==== AggregateRootProcessor generating Proxies ======");
        //        for (final Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(AggregateRoot.class)) {
        //            if (annotatedElement.getKind() != ElementKind.CLASS) {
        //                this.error(annotatedElement, "Only classes can be annotated with %s",
        //                    AggregateRoot.class.getSimpleName());
        //
        //                return true;
        //            }
        //
        //            return true;
        //
        //            final ClassName aggregatmanager = ClassName.get(
        //                "com.github.haschi.dominium.infrastruktur",
        //                "AggregatManager");
        //
        //            final ClassName zielinterface = ClassName.get(
        //                "com.github.haschi.dominium",
        //                annotatedElement.getSimpleName() + "EventTarget");
        //            final ParameterizedTypeName aggregatManagerX = ParameterizedTypeName.get(aggregatmanager, zielinterface);
        //
        //            final TypeElement typeElement = (TypeElement)annotatedElement;
        //            final AggregateRootClass root = new AggregateRootClass(typeElement);
        //            final Set<ExecutableElement> eventHandler = root.getEventHandler();
        //            for (ExecutableElement element : eventHandler) {
        //                messager.printMessage(Kind.NOTE, String.format("Eventhandler %s%n", element.toString()), element);
        //            }
        //
        //            final TypeElement typeElement1 = this.elementUtils.getTypeElement(((TypeElement) annotatedElement)
        //                .getQualifiedName());
        //
        //            //            final MethodSpec falls = MethodSpec.methodBuilder("falls")
        //            //                .addModifiers(Modifier.PUBLIC)
        //            //                .returns(void.class)
        //            //                .addStatement("$T.out.println($S)", System.class, "Hello Proxy!")
        //            //                .build();
        //
        //            final AnnotationSpec suppressAllWarnings = AnnotationSpec.builder(SuppressWarnings.class)
        //                .addMember("value", "$S", "all")
        //                .build();
        //
        //            final Builder aggregatProxyBuilder = TypeSpec.classBuilder(typeElement.getSimpleName() + "Proxy")
        //                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        //                .superclass(TypeName.get(typeElement.asType()))
        //                .addAnnotation(suppressAllWarnings);
        //
        //            for (MethodSpec konstruktor : root.getKonstruktoren()) {
        //                aggregatProxyBuilder.addMethod(konstruktor);
        //            }
        //
        //            for (final ExecutableElement e : eventHandler) {
        //
        //                final TypeMirror typeMirror = e.getParameters().get(0).asType();
        //                final ParameterSpec eventParameter = ParameterSpec.builder(
        //                    TypeName.get(typeMirror), "event")
        //                    .addModifiers(Modifier.FINAL)
        //                    .build();
        //
        //                final MethodSpec methodSpec = MethodSpec.methodBuilder(e.getSimpleName().toString())
        //                    .addAnnotation(Override.class)
        //                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        //                    .returns(void.class)
        //                    .addParameter(eventParameter)
        //                    .addStatement("super.falls($N)", "event")
        //                    .build();
        //
        //                aggregatProxyBuilder.addMethod(methodSpec);
        //            }
        //
        //            final FieldSpec änderungenField = FieldSpec.builder(aggregatManagerX, "änderungen", Modifier.PRIVATE).build();
        //            aggregatProxyBuilder.addField(änderungenField);
        //
        //            final MethodSpec änderungen = MethodSpec.methodBuilder("getÄnderungen")
        //                .addModifiers(Modifier.PUBLIC)
        //                .addStatement("return this.$N", "änderungen")
        //                .returns(aggregatManagerX)
        //                .build();
        //
        //            aggregatProxyBuilder.addMethod(änderungen);
        //
        //            final TypeSpec aggregatProxy =aggregatProxyBuilder.build();
        //
        //
        //            final JavaFile javaFile = JavaFile.builder("com.github.haschi.dominium", aggregatProxy).build();
        //            try {
        //                javaFile.writeTo(this.filer);
        //            } catch (IOException e) {
        //                this.error(typeElement, e.getMessage());
        //                return true;
        //            }
        //        }

        return false;
    }

    private void error(final Element annotatedElement, final String message, final Object... args) {
        this.messager.printMessage(Kind.ERROR, String.format(message, args), annotatedElement);
    }
}
