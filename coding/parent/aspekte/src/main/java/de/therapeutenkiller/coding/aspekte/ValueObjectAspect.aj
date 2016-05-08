package de.therapeutenkiller.coding.aspekte;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public aspect ValueObjectAspect {
    public interface ValueObjectInterface {}
    declare parents : (@ValueObject * && !de.therapeutenkiller.coding.aspekte..*) implements ValueObjectInterface;


    public String ValueObjectInterface.toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public boolean ValueObjectInterface.equals(final Object that) {

        final String[] exclude = this.getExcludeFields();

        return (that == this)
            || ((that instanceof ValueObjectInterface)
            && (((ValueObjectInterface) that).canEqual(this)
            && EqualsBuilder.reflectionEquals(this, that, exclude)));
    }

    private String[] ValueObjectInterface.getExcludeFields() {
        Class<?> klasse = this.getClass();

        while (!(klasse.equals(Object.class))) {
            final ValueObject annotation = klasse.getAnnotation(ValueObject.class);

            if (annotation != null) {
                return annotation.exclude();
            }

            klasse = klasse.getSuperclass();
        }

        throw new IllegalStateException("Internal Aspect Failure");
    }

    public final int ValueObjectInterface.hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, this.getExcludeFields());
    }

    // s. http://www.artima.com/lejava/articles/equality.html
    private boolean ValueObjectInterface.canEqual(@DarfNullSein final Object other) {
        return (other != null) && (this.getClass() == other.getClass());
    }
}
