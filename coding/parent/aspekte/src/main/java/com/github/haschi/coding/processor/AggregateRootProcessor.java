package com.github.haschi.coding.processor;

import com.github.haschi.coding.annotation.AggregateRoot;
import com.github.haschi.coding.annotation.CommandHandler;
import com.github.haschi.coding.annotation.TargetAggregateIdentifier;
import com.github.haschi.coding.aspekte.DarfNullSein;
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
import com.squareup.javapoet.TypeVariableName;

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
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor8;
import javax.lang.model.util.TypeKindVisitor8;
import javax.tools.Diagnostic.Kind;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({
    "com.github.haschi.coding.annotation.AggregateRoot",
    "com.github.haschi.coding.annotation.CommandHandler"})
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
            if (processAggregateRoot(annotatedElement)) return true;
        }

        for (final Element commandHandlerElement : roundEnvironment.getElementsAnnotatedWith(CommandHandler.class)) {
            this.messager.printMessage(Kind.NOTE, "======= Generating command handler =======");
            if (commandHandlerElement.getKind() != ElementKind.METHOD) {
                this.error(
                    commandHandlerElement,
                    "Nur Parameter dürfen mit %s annotiert sein.",
                    CommandHandler.class.getSimpleName());

                return true;
            }

            ExecutableElement method = (ExecutableElement)commandHandlerElement;
            final VariableElement commandParameter = method.getParameters().get(0);
            final TypeName commandTypeName = ClassName.get(commandParameter.asType());
            final String commandPackage = ((ClassName) commandTypeName).packageName();
            final String applicationServiceName = ((ClassName) commandTypeName).simpleName() + "Service";

            assert method.getEnclosingElement().getKind() == ElementKind.CLASS;
            TypeElement aggregate = (TypeElement) method.getEnclosingElement();
            final ClassNameFactory classNameFactory = new ClassNameFactory(aggregate);

            final FieldSpec repositoryField = FieldSpec.builder(
                classNameFactory.getRepositoryType(),
                "repository",
                Modifier.PRIVATE,
                Modifier.FINAL).build();

            final ParameterSpec repositoryParameter = ParameterSpec.builder(
                classNameFactory.getRepositoryType(),
                "repository",
                Modifier.FINAL).build();

            final ParameterSpec commandParameterx = ParameterSpec.builder(
                commandTypeName, "command", Modifier.FINAL)
                .build();

            final TypeMirror commandTypeMirrir = commandParameter.asType();
            final CommandVisitor commandVisitor = new CommandVisitor();

            final CommandIdentifier identifier = commandTypeMirrir.accept(commandVisitor, new CommandIdentifier());

            TypeSpec applicationService = TypeSpec.classBuilder(applicationServiceName)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(
                    ClassName.get("com.github.haschi.dominium.infrastructure", "ApplicationService"),
                    commandTypeName))
                .addField(repositoryField)
                .addMethod(MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(repositoryParameter)
                    .addStatement("this.$N = $N", repositoryField, repositoryParameter)
                    .build())
                .addMethod(MethodSpec.methodBuilder("ausführen")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class)
                    .addException(ClassName.get("com.github.haschi.dominium.infrastructure",
                        "KonkurrierenderZugriff"))
                    .addParameter(commandParameterx)
                    .addStatement(
                        "final $T $N = this.$N.getById($N.$N())",
                        classNameFactory.getAggregateRootProxyType(),
                        "aggregat",
                        repositoryField,
                        commandParameterx,
                        identifier.get().getSimpleName())
                    .addStatement(
                        "$N.$N($N)",
                        "aggregat",
                        method.getSimpleName(),
                        commandParameterx)
                    .addStatement(
                        "this.$N.save($N)",
                        repositoryField,
                        "aggregat")
                    .build())
                .build();

            try {
                writeTypeTo(classNameFactory, applicationService, this.filer);
            } catch (IOException e) {
                this.error(commandHandlerElement, e.getMessage());
                return true;
            }
        }

        return false;
    }

    private class CommandIdentifier {

        private ExecutableElement element;

        void set(ExecutableElement element) {

            this.element = element;
        }

        final ExecutableElement get() {
            return element;
        }
    }

    private class TargetAggregateIdentifierSuche extends SimpleElementVisitor8<CommandIdentifier, CommandIdentifier> {

        TargetAggregateIdentifierSuche(CommandIdentifier identifier) {
            super(identifier);
        }

        @Override
        public CommandIdentifier visitExecutable(final ExecutableElement executableElement,
                                                 final CommandIdentifier commandIdentifier) {
            final TargetAggregateIdentifier annotation = executableElement.getAnnotation(TargetAggregateIdentifier
                .class);
            if (annotation != null) {
                commandIdentifier.set(executableElement);
            }

            return super.visitExecutable(executableElement, commandIdentifier);
        }

        @Override
        public CommandIdentifier visitVariable(final VariableElement variableElement,
                                               final CommandIdentifier commandIdentifier) {
            return super.visitVariable(variableElement, commandIdentifier);
        }

        @Override
        public CommandIdentifier visitType(final TypeElement typeElement,
                                           final CommandIdentifier commandIdentifier) {

            CommandIdentifier result = commandIdentifier;
            for(final Element element : typeElement.getEnclosedElements()) {
                result = element.accept(this, commandIdentifier);
            }

            return result;
        }
    }

    private class CommandVisitor extends TypeKindVisitor8<CommandIdentifier, CommandIdentifier> {

        @Override
        @DarfNullSein
        public CommandIdentifier visitDeclared(final DeclaredType declaredType,
                                               final CommandIdentifier commandIdentifier) {


            TargetAggregateIdentifierSuche v = new TargetAggregateIdentifierSuche(commandIdentifier);

            final TypeElement typeElement = (TypeElement) declaredType.asElement();
            return typeElement.accept(v, commandIdentifier);
        }
    }

    private boolean processAggregateRoot(final Element annotatedElement) {
        if (annotatedElement.getKind() != ElementKind.CLASS) {
            this.error(
                annotatedElement,
                "Only classes can be annotated with %s",
                AggregateRoot.class.getSimpleName());

            return true;
        }

        TypeElement type = (TypeElement)annotatedElement;
        final AggregateRootClass arc = new AggregateRootClass(type);
        final ClassNameFactory classNameFactory = new ClassNameFactory(type);

        final TypeSpec repository = buildRepository(classNameFactory, arc);

        try {
            writeTypeTo(classNameFactory, repository, this.filer);
        } catch (IOException e) {
            this.error(type, e.getMessage());
            return true;
        }


        /////////////// BEGIN Event Interface //////////////////////
        final TypeSpec eventInterface = buildEventInterface(classNameFactory);

        try {
            writeTypeTo(classNameFactory, eventInterface, this.filer);
        } catch (IOException e) {
            this.error(type, e.getMessage());
            return true;
        }

        /////////////// BEGIN Aggregat-Proxy ////////////////////////////////
        final TypeSpec aggregatProxy = buildAggregateRootProxy(classNameFactory, arc);

        try {
            writeTypeTo(classNameFactory, aggregatProxy, filer);
        } catch (IOException e) {
            this.error(type, e.getMessage());
            return true;
        }

        /////////////// BEGINN Alle Events ////////////////////
        final List<TypeSpec> eventTypes = new ArrayList<>();
        for (final TypeMirror eventTypeMirror : arc.getEventTypes()) {
            TypeSpec eventType = buildEventType(classNameFactory, eventTypeMirror);
            eventTypes.add(eventType);
        }

        for (final TypeSpec t : eventTypes) {
            try {
                writeTypeTo(classNameFactory, t, filer);
            } catch (IOException e) {
                this.error(type, e.getMessage());
                return true;
            }
        }
        return false;
    }

    private TypeSpec buildRepository(final ClassNameFactory classNameFactory, final AggregateRootClass arc) {
        final ClassName repository = classNameFactory.getRepositoryType();

        final TypeName genericRepository = ParameterizedTypeName.get(
            ClassName.get("com.github.haschi.dominium.infrastructure", "Repository"),
            classNameFactory.getEventInterfaceName(),
            arc.getAggregateIdentitfier().type(),
            classNameFactory.getAggregateRootProxyType());

        final ParameterSpec storage = ParameterSpec.builder(
                classNameFactory.getEventStoreType(),
                "storage",
                Modifier.FINAL)
            .build();

        final ParameterSpec identitätsmerkmal = ParameterSpec.builder(
            arc.getAggregateIdentitfier().type(),
            "id",
            Modifier.FINAL).build();

        final ParameterSpec version = ParameterSpec.builder(
            ClassName.get("com.github.haschi.dominium.modell", "Version"),
            "version",
            Modifier.FINAL).build();

        return TypeSpec.classBuilder(repository)
            .superclass(genericRepository)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addMethod(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(storage)
                .addStatement("super($N)", storage).build())
            .addMethod(MethodSpec.methodBuilder("create")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .returns(classNameFactory.getAggregateRootProxyType())
                .addParameter(identitätsmerkmal)
                .addParameter(version)
                .addStatement("return new $T($N, $N)",
                    classNameFactory.getAggregateRootProxyType(),
                    identitätsmerkmal,
                    version)
                .build())
            .build();
    }

    private TypeSpec buildEventType(final ClassNameFactory factory, final TypeMirror eventTypeMirror) {

        final ClassName eventTypeName = factory.getEventTypeName(eventTypeMirror);

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
            .addParameter( factory.getAggregateRootProxyType(), "aggregat", Modifier.FINAL)
            .addStatement("$N.verarbeiten(this)", "aggregat")
            .build();

        return TypeSpec.classBuilder(eventTypeName)
            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
            .addSuperinterface(factory.getEventInterfaceName())
            .addAnnotation(immutable)
            .addMethod(getEventMethod)
            .addMethod(applyMethod)
            .build();
    }

    private TypeSpec buildAggregateRootProxy(final ClassNameFactory classNameFactory,
                                             final AggregateRootClass arc) {
        final Builder aggregatProxy = TypeSpec.classBuilder(classNameFactory.getAggregateRootProxyType().simpleName())
            .addAnnotation(suppressWarningsAnnotationSpec())
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .superclass(classNameFactory.getAggregateRootType())
            .addSuperinterface(classNameFactory.getProxyInterface(arc));

        final AggregateRootProxyBuilder aggregateRootProxyBuilder = new AggregateRootProxyBuilder(
            classNameFactory,
            arc);

        aggregatProxy.addFields(aggregateRootProxyFields(classNameFactory));
        aggregatProxy.addMethods(aggregateRootProxyBuilder.aggregateRootProxyMethods());
        aggregatProxy.addMethods(aggregateRootProxyEventHandlerMethodSpecs(classNameFactory, arc));
        aggregatProxy.addMethods(aggregateRootProxyMessageHandler(classNameFactory, arc));

        return aggregatProxy.build();
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
            .indent("    ")
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
