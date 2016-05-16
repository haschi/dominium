package de.therapeutenkiller.coding.annotation;

import de.therapeutenkiller.coding.aspekte.DarfNullSein;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Fields implements Iterable<Field>{

    private final TypeElement classElement;

    public Fields(final TypeElement classElement) {
        super();

        this.classElement = classElement;
    }

    public String alsParameter() {
        return this.getFields().stream().map(f -> "this." + f.getName()).collect(Collectors.joining(",\n"));
    }

    @Override
    public Iterator<Field> iterator() {
        final ArrayList<Field> fields = this.getFields();

        return fields.iterator();
    }

    private ArrayList<Field> getFields() {
        final ArrayList<Field> fields = new ArrayList<>();
        final FieldVisitor visitor = new FieldVisitor(fields);

        final List<? extends Element> elements = classElement.getEnclosedElements();
        for (final Element subElement : elements) {

            subElement.accept(visitor, null);
        }
        return fields;
    }

    @Override
    public void forEach(final Consumer<? super Field> consumer) {

    }

    @Override
    public Spliterator<Field> spliterator() {
        return null;
    }

    private class FieldVisitor implements javax.lang.model.element.ElementVisitor<Void, Void> {

        private final ArrayList<Field> fields;

        public FieldVisitor(final ArrayList<Field> fields) {
            super();
            this.fields = fields;
        }

        @Override @DarfNullSein
        public Void visit(final Element element, @DarfNullSein final Void aVoid) {
            return null;
        }

        @Override @DarfNullSein
        public Void visit(final Element element) {
            return null;
        }

        @Override @DarfNullSein
        public Void visitPackage(final PackageElement packageElement, @DarfNullSein final Void aVoid) {
            return null;
        }

        @Override @DarfNullSein
        public Void visitType(final TypeElement typeElement, @DarfNullSein final Void aVoid) {
            return null;
        }

        @Override @DarfNullSein
        public Void visitVariable(final VariableElement variableElement, @DarfNullSein final Void aVoid) {
            final Name simpleName = variableElement.getSimpleName();
            final TypeMirror typeMirror = variableElement.asType();

            fields.add(new Field(typeMirror.toString(), simpleName.toString()));
            return null;
        }

        @Override @DarfNullSein
        public Void visitExecutable(final ExecutableElement executableElement, @DarfNullSein final Void aVoid) {
            return null;
        }

        @Override @DarfNullSein
        public Void visitTypeParameter(final TypeParameterElement typeParameterElement, @DarfNullSein final Void aVoid) {
            return null;
        }

        @Override @DarfNullSein
        public Void visitUnknown(final Element element, @DarfNullSein final Void aVoid) {
            return null;
        }
    }
}
