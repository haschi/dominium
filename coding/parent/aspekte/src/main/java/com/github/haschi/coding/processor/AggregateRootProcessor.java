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
import javax.lang.model.element.VariableElement;
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
    public synchronized void init(final ProcessingEnvironment environment) {
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

            final ClassNameFactory classNameFactory = new ClassNameFactory(type);
            final String targetPackageName = classNameFactory.getTargetPackageName();

            final ClassName eventInterfaceName = classNameFactory.getEventInterfaceName();
            final ClassName aggregateRootProxyType = classNameFactory.getAggregateRootProxyType();

            final TypeSpec eventInterface = buildEventInterface(classNameFactory);

            try {
                writeTypeTo(classNameFactory, eventInterface, this.filer);
            } catch (IOException e) {
                this.error(type, e.getMessage());
                return true;
            }

            final AggregateRootClass arc = new AggregateRootClass(type);

            /////////////// BEGIN Aggregat-Proxy ////////////////////////////////


            final Builder aggregatProxy = TypeSpec.classBuilder(classNameFactory.getAggregateRootProxyType().simpleName())
                .addAnnotation(suppressWarningsAnnotationSpec())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(classNameFactory.getAggregateRootType());

            final AggregateRootProxyBuilder aggregateRootProxyBuilder = new AggregateRootProxyBuilder(
                classNameFactory,
                arc);


            aggregatProxy.addFields(aggregateRootProxyFields(classNameFactory));
            aggregatProxy.addMethods(aggregateRootProxyBuilder.aggregateRootProxyMethods());
            aggregatProxy.addMethods(aggregateRootProxyEventHandlerMethodSpecs(classNameFactory, arc));
            aggregatProxy.addMethods(aggregateRootProxyMessageHandler(classNameFactory, arc));

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
            final AggregateIdentifierGenerator identifier = arc.getAggregateIdentitfier();
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
        }

        return true;
    }

    private List<MethodSpec> aggregateRootProxyMessageHandler(final ClassNameFactory classNameFactory,
                                                              final AggregateRootClass arc) {
        final List<MethodSpec> messageHandler = new ArrayList<>();
        for (ExecutableElement handler : arc.getEventHandler()) {
            messageHandler.add(applyEventMethodSpec(handler, classNameFactory));
        }
        return messageHandler;
    }

    private List<MethodSpec> aggregateRootProxyEventHandlerMethodSpecs(final ClassNameFactory classNameFactory,
                                                                       final AggregateRootClass arc) {
        final List<MethodSpec> eventHandler = new ArrayList<>();
        for (ExecutableElement handler : arc.getEventHandler()) {
            eventHandler.add(eventHandlerMethodSpec(handler, classNameFactory));
        }
        return eventHandler;
    }

    private MethodSpec applyEventMethodSpec(final ExecutableElement handler, final ClassNameFactory factory) {

        // Muss noch erzeugt werden: Soll nicht ImmutableXXXMessage sein, sondern XXXEvent!
        final TypeMirror handlerParameterType = handler.getParameters().get(0).asType();
        final ClassName handlerParameterTypeName = (ClassName)ClassName.get(handlerParameterType);
        final TypeName eventTypeName = ClassName.get(
            factory.getTargetPackageName(),
            handlerParameterTypeName.simpleName() + "Message");


        return MethodSpec.methodBuilder("verarbeiten")
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .returns(void.class)
                        .addParameter(ParameterSpec.builder(eventTypeName, "ereignis", Modifier.FINAL).build())
                        .addStatement("super.$N($N.ereignis())", handler.getSimpleName().toString(), "ereignis")
                        .build();
    }

    private MethodSpec eventHandlerMethodSpec(final ExecutableElement handler,
                                              final ClassNameFactory factory) {

        final TypeMirror handlerParameterType = handler.getParameters().get(0).asType();
        final ClassName handlerParameterTypeName = (ClassName)ClassName.get(handlerParameterType);

        final List<ParameterSpec> parameters = new ArrayList<>();

        for (final VariableElement parameter : handler.getParameters()) {
            final TypeName parameterType = ClassName.get(parameter.asType());
            final ParameterSpec ps = ParameterSpec
                .builder(parameterType, parameter.getSimpleName().toString(), Modifier.FINAL)
                .build();

            parameters.add(ps);
        }

        final TypeName immutableEventTypeName = ClassName.get(
            factory.getTargetPackageName(),
            "Immutable" + handlerParameterTypeName.simpleName() + "Message");

        return MethodSpec.methodBuilder(handler.getSimpleName().toString())
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .returns(void.class)
            .addParameters(parameters)
            .addStatement("super.$N($N)", handler.getSimpleName(), parameters.get(0).name)
            .addStatement("this.$N.add($T.of($N))", "changes",
                immutableEventTypeName,
                parameters.get(0).name)
            .build();
    }

    private List<FieldSpec> aggregateRootProxyFields(final ClassNameFactory classNameFactory) {
        final List<FieldSpec> fields = new ArrayList<>();
        fields.add(versionFieldSpec());
        fields.add(changesFieldSpec(classNameFactory));
        return fields;
    }

    private FieldSpec changesFieldSpec(final ClassNameFactory factory) {
        return FieldSpec.builder(
            ParameterizedTypeName.get(ClassName.get("java.util", "List"), factory.getEventInterfaceName()),
            "changes",
            Modifier.PRIVATE,
            Modifier.FINAL)
            .initializer("new $T<>()", ClassName.get("java.util", "ArrayList"))
            .build();
    }

    private FieldSpec versionFieldSpec() {
        return FieldSpec.builder(
            ClassName.get("com.github.haschi.dominium.modell", "Version"),
            "version",
            Modifier.PRIVATE, Modifier.FINAL)
            .build();
    }

    private AnnotationSpec suppressWarningsAnnotationSpec() {
        return AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "$S", "all").build();
    }

    private void writeTypeTo(final ClassNameFactory classNameFactory, final TypeSpec eventInterface, final Filer filer) throws
        IOException {
        final JavaFile javaFile = JavaFile.builder(classNameFactory.getTargetPackageName(), eventInterface)
            .addFileComment("Generated Code - %s", ZonedDateTime.now().toString())
            .build();

        javaFile.writeTo(filer);
    }

    private TypeSpec buildEventInterface(final ClassNameFactory classNameFactory) {
        final ParameterSpec proxyParameter = ParameterSpec.builder(classNameFactory.getAggregateRootProxyType(), "proxy").build();
        final MethodSpec anwendenAufMethod = MethodSpec.methodBuilder("anwendenAuf")
            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
            .addParameter(proxyParameter)
            .returns(void.class)
            .build();

        return TypeSpec.interfaceBuilder(classNameFactory.getEventInterfaceName())
            .addModifiers(Modifier.PUBLIC)
            .addMethod(anwendenAufMethod)
            .build();
    }

    private void error(final Element annotatedElement, final String message, final Object... args) {
        this.messager.printMessage(Kind.ERROR, String.format(message, args), annotatedElement);
    }
}
