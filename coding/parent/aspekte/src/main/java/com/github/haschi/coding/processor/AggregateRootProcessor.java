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
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic.Kind;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
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

        for (final Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(AggregateRoot.class)) {
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                this.error(
                    annotatedElement,
                    "Only classes can be annotated with %s",
                    AggregateRoot.class.getSimpleName());

                return true;
            }

            /////////////// BEGIN Event Interface //////////////////////
            TypeElement type = (TypeElement)annotatedElement;
            final PackageElement packageElement = (PackageElement) type.getEnclosingElement();
            final Name sourcePackageName = packageElement.getQualifiedName();
            final String targetPackageName = sourcePackageName.toString() + ".generiert";
            final Name aggragteRootName = type.getSimpleName();

            final ClassName eventInterfaceName = ClassName.get(
                targetPackageName,
                aggragteRootName.toString() + "Event");

            final ClassName aggregateRootProxyType = ClassName.get(
                targetPackageName,
                aggragteRootName.toString() + "Proxy");

            final ParameterSpec proxyParameter = ParameterSpec.builder(aggregateRootProxyType, "proxy").build();
            final MethodSpec anwendenAufMethod = MethodSpec.methodBuilder("anwendenAuf")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addParameter(proxyParameter)
                .returns(void.class)
                .build();

            final TypeSpec eventInterface = TypeSpec.interfaceBuilder(eventInterfaceName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(anwendenAufMethod)
                .build();

            messager.printMessage(
                Kind.NOTE,
                String.format("Qualified Name: %s", sourcePackageName.toString()),
                type);

            final JavaFile javaFile = JavaFile.builder(targetPackageName, eventInterface)
                .addFileComment("Generated Code - %s", ZonedDateTime.now().toString())
                .build();
            try {
                javaFile.writeTo(this.filer);
            } catch (IOException e) {
                this.error(type, e.getMessage());
                return true;
            }

            /////////////// BEGIN Aggregat-Proxy ////////////////////////////////

            Builder aggregatProxy = TypeSpec.classBuilder(aggregateRootProxyType.simpleName())
                .addAnnotation(AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "$S", "all").build())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(ClassName.get(type))
                .addField(FieldSpec.builder(ClassName.get("com.github.haschi.dominium.modell", "Version"),
                    "version",
                    Modifier.PRIVATE, Modifier.FINAL)
                    .build())
                .addField(FieldSpec.builder(
                    ParameterizedTypeName.get(ClassName.get("java.util", "List"),eventInterfaceName),
                    "changes",
                    Modifier.PRIVATE,
                    Modifier.FINAL)
                    .initializer("new $T<>()", ClassName.get("java.util", "ArrayList"))
                    .build())
                .addField(FieldSpec.builder(
                    ClassName.get("java.util", "UUID"),
                    "id",
                    Modifier.PRIVATE,
                    Modifier.FINAL).build());

            final MethodSpec constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get("java.util", "UUID"), "id", Modifier.FINAL)
                .addParameter(ClassName.get("com.github.haschi.dominium.modell", "Version"), "version", Modifier.FINAL)
                .addStatement("super($N)", "id")
                .addStatement("this.$N = $N", "id", "id")
                .addStatement("this.$N = $N", "version", "version")
                .build();

            final MethodSpec getIdMethod = MethodSpec.methodBuilder("getId")
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get("java.util", "UUID"))
                .addStatement("return this.$N", "id")
                .build();

            final MethodSpec getVersionMethod = MethodSpec.methodBuilder("getVersion")
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get("com.github.haschi.dominium.modell", "Version"))
                .addStatement("return this.$N", "version")
                .build();

            final MethodSpec getUncommitedChangesMethod = MethodSpec.methodBuilder("getUncommitedChanges")
                .addModifiers(Modifier.PUBLIC)
                .returns(ParameterizedTypeName.get(ClassName.get("java.util", "List"),eventInterfaceName))
                .addStatement("return this.$N", "changes")
                .build();

            final MethodSpec markChangesAsCommitedMethod = MethodSpec.methodBuilder("markChangesAsCommitted")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addStatement("this.$N.clear()", "changes")
                .build();

            final MethodSpec wiederherstellenMethod = MethodSpec.methodBuilder("wiederherstellen")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(
                    ParameterizedTypeName.get(ClassName.get("java.util", "List"), eventInterfaceName),
                    "ereignisse",
                    Modifier.FINAL)
                .addStatement("$N.forEach(e -> e.anwendenAuf(this))", "ereignisse")
                .build();

            final MethodSpec equalsMethod = MethodSpec.methodBuilder("equals")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.BOOLEAN)
                .addParameter(Object.class, "anderes", Modifier.FINAL)
                .beginControlFlow("if (this == $N)", "anderes")
                .addStatement("return true")
                .endControlFlow()
                .beginControlFlow("if (!($N instanceof $T))", "anderes", aggregateRootProxyType)
                .addStatement("return false")
                .endControlFlow()
                .addStatement("final $T that = ($T) $N", aggregateRootProxyType, aggregateRootProxyType, "anderes")
                .addStatement("return new $T().append(this.$N, that.$N).isEquals()",
                    ClassName.get("org.apache.commons.lang3.builder", "EqualsBuilder"),
                    "id",
                    "id")
                .build();

            final MethodSpec hashCodeMethod = MethodSpec.methodBuilder("hashCode")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.INT)
                .addStatement("return new $T(17, 37).append(this.$N).toHashCode()",
                    ClassName.get("org.apache.commons.lang3.builder", "HashCodeBuilder"),
                    "id")
                .build();

            aggregatProxy
                .addMethod(constructor)
                .addMethod(getIdMethod)
                .addMethod(getVersionMethod)
                .addMethod(getUncommitedChangesMethod)
                .addMethod(markChangesAsCommitedMethod)
                .addMethod(wiederherstellenMethod)
                .addMethod(equalsMethod)
                .addMethod(hashCodeMethod);

            final AggregateRootClass arc = new AggregateRootClass(type);
            final Set<ExecutableElement> eventHandler = arc.getEventHandler();
            for (ExecutableElement handler : eventHandler) {

                final List<ParameterSpec> parameters = new ArrayList<>();

                for (final VariableElement parameter : handler.getParameters()) {
                    final TypeName parameterType = ClassName.get(parameter.asType());
                    final ParameterSpec ps = ParameterSpec
                        .builder(parameterType, parameter.getSimpleName().toString(), Modifier.FINAL)
                        .build();

                    parameters.add(ps);
                }

                // Muss noch erzeugt werden: Soll nicht ImmutableXXXMessage sein, sondern XXXEvent!
                final TypeMirror handlerParameterType = handler.getParameters().get(0).asType();

                final ClassName handlerParameterTypeName = (ClassName)ClassName.get(handlerParameterType);

                final TypeName immutableEventTypeName = ClassName.get(
                    targetPackageName,
                    "Immutable" + handlerParameterTypeName.simpleName() + "Message");

                final TypeName eventTypeName = ClassName.get(
                    targetPackageName,
                    handlerParameterTypeName.simpleName() + "Message");

                final MethodSpec handlerMethod = MethodSpec.methodBuilder(handler.getSimpleName().toString())
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class)
                    .addParameters(parameters)
                    .addStatement("super.$N($N)", handler.getSimpleName(), parameters.get(0).name)
                    .addStatement("this.$N.add($T.of($N))", "changes",
                        immutableEventTypeName,
                        parameters.get(0).name)
                    .build();

                aggregatProxy.addMethod(handlerMethod);

                final MethodSpec applyEventMethod = MethodSpec.methodBuilder("verarbeiten")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .returns(void.class)
                    .addParameter(ParameterSpec.builder(eventTypeName, "ereignis", Modifier.FINAL).build())
                    .addStatement("super.$N($N.ereignis())", handler.getSimpleName().toString(), "ereignis")
                    .build();

                aggregatProxy.addMethod(applyEventMethod);
            }

            final JavaFile proxyClassFile = JavaFile.builder(targetPackageName, aggregatProxy.build())
                .indent("    ")
                .addFileComment("Generated Code - %s", ZonedDateTime.now().toString())
                .build();

            try {
                proxyClassFile.writeTo(this.filer);
            } catch (IOException e) {
                this.error(type, e.getMessage());
                return true;
            }

            /////////////// BEGINN Alle Events ////////////////////

            final Set<TypeMirror> events = arc.getEvents();
            for (final TypeMirror eventTypeMirror : events) {
                final ClassName eventTypeName = ClassName.get(
                    targetPackageName,
                    ((ClassName)ClassName.get(eventTypeMirror)).simpleName() + "Message");

                ClassName immutableAnnotationType = ClassName.get("org.immutables.value", "Value", "Immutable");

                final AnnotationSpec immutable = AnnotationSpec.builder(immutableAnnotationType)
                    .addMember("builder", "false")
                    .build();

                ClassName parameterAnnotationType = ClassName.get("org.immutables.value", "Value", "Parameter");

                final AnnotationSpec parameterAnnotation = AnnotationSpec.builder(parameterAnnotationType)
                    .build();

                MethodSpec getEventMethod = MethodSpec.methodBuilder("ereignis")
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .addAnnotation(parameterAnnotation)
                    .returns(ClassName.get(eventTypeMirror))
                    .build();

                MethodSpec applyMethod = MethodSpec.methodBuilder("anwendenAuf")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .returns(void.class)
                    .addParameter(aggregateRootProxyType, "aggregat", Modifier.FINAL)
                    .addStatement("$N.verarbeiten(this)", "aggregat")
                    .build();

                TypeSpec eventType = TypeSpec.classBuilder(eventTypeName)
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .addSuperinterface(eventInterfaceName)
                    .addAnnotation(immutable)
                    .addMethod(getEventMethod)
                    .addMethod(applyMethod)
                    .build();

                final JavaFile eventClassFile = JavaFile.builder(targetPackageName, eventType)
                    .indent("    ")
                    .addFileComment("Generated Code - $S", ZonedDateTime.now().toString())
                    .build();

                try {
                    eventClassFile.writeTo(this.filer);
                } catch (IOException e) {
                    this.error(type, e.getMessage());
                    return true;
                }
            }

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
        }

        return true;
    }

    private void error(final Element annotatedElement, final String message, final Object... args) {
        this.messager.printMessage(Kind.ERROR, String.format(message, args), annotatedElement);
    }
}
