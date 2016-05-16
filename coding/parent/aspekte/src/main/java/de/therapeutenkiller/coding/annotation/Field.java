package de.therapeutenkiller.coding.annotation;

public class Field {

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    private final String type;
    private final String name;

    public Field(final String type, final String name) {
        super();

        this.type = type;
        this.name = name;
    }
}
