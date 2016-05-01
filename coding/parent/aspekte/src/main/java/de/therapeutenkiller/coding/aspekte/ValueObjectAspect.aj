package de.therapeutenkiller.coding.aspekte;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public aspect ValueObjectAspect {
    private interface ValueObjectInterface {}
    declare parents : (@de.therapeutenkiller.coding.aspekte.ValueObject *) implements ValueObjectInterface;

    // public String ValueObjectInterface.toString() {
    //    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    //}

    public final boolean ValueObjectInterface.equals(@DarfNullSein final Object that) {
        return (that == this)
            || ((that instanceof ValueObjectInterface)
            && (this.gleicht((ValueObjectInterface) that)));
    }

    private boolean ValueObjectInterface.gleicht(final ValueObjectInterface that) {

        final ValueObject annotation = this.getClass().getAnnotation(ValueObject.class);
        final String[] exclude = annotation.exclude();

        return that.canEqual(this) && EqualsBuilder.reflectionEquals(this, that, exclude);
    }

    public final int ValueObjectInterface.hashCode() {
        final ValueObject annotation = this.getClass().getAnnotation(ValueObject.class);
        final String[] exclude = annotation.exclude();

        return HashCodeBuilder.reflectionHashCode(this, exclude);
    }

    // s. http://www.artima.com/lejava/articles/equality.html
    private boolean ValueObjectInterface.canEqual(@DarfNullSein final Object other) {
        return (other != null) && (this.getClass() == other.getClass());
    }
}
