package org.bondarenko.core.filtering;

public enum Type {
    NEWSPAPER("NEWSPAPER"),
    MAGAZINE("MAGAZINE"),
    SCIENTIFIC_JOURNAL("SCIENTIFIC_JOURNAL"),
    YEARBOOK("YEARBOOK");

    private final String type;

    Type(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
