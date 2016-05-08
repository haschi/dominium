package de.therapeutenkiller.coding.annotation;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;
import java.io.Writer;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

@SupportedAnnotationTypes("de.therapeutenkiller.coding.annotation.Builder")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BuilderProcessor extends AbstractProcessor {

    @Override
    public final boolean process(final Set<? extends TypeElement> set, final RoundEnvironment roundEnvironment) {
        for (final Element element : roundEnvironment.getElementsAnnotatedWith(Builder.class)) {
            final String message = String.format("Processing Class %s", element.getSimpleName(), element);
            this.processingEnv.getMessager().printMessage(Kind.NOTE, message);

            String fqClassName = null;
            String className = null;
            String packageName = null;

            if (element.getKind() == ElementKind.CLASS) {
                TypeElement classElement = (TypeElement) element;
                PackageElement packageElement = (PackageElement) element.getEnclosingElement();

                fqClassName = classElement.getQualifiedName().toString();
                className = classElement.getSimpleName().toString();
                packageName = packageElement.getQualifiedName().toString();

                if (fqClassName != null) {
                    try {

                    Properties properties = new Properties();
                    URL url = this.getClass().getClassLoader().getResource("velocity.properties");
                    properties.load(url.openStream());


                    VelocityEngine ve = new VelocityEngine(properties);
                    processingEnv.getMessager().printMessage(Kind.NOTE, "3", element);

                    ve.init();
                    processingEnv.getMessager().printMessage(Kind.NOTE, "2", element);
                    VelocityContext vc = new VelocityContext();
                    vc.put("className", className);
                    vc.put("packageName", packageName);
                        vc.put("fqClassName", fqClassName);

                        vc.put("felder", new Fields(classElement));

                    Template vt = ve.getTemplate("builder.vm", "UTF-8");
                    processingEnv.getMessager().printMessage(Kind.NOTE, "1", element);
                    JavaFileObject jfo = null;

                        jfo = processingEnv.getFiler().createSourceFile(fqClassName+"Builder");
                        Writer writer = jfo.openWriter();
                        vt.merge(vc, writer);
                        writer.close();

                    } catch (Exception e) {
                        processingEnv.getMessager().printMessage(Kind.ERROR, e.getMessage(), element);
                        return false;
                    }
                }
            }
        }

        this.processingEnv.getMessager().printMessage(Kind.NOTE, "Fertig mit Processing.");
        return true;
    }
}
