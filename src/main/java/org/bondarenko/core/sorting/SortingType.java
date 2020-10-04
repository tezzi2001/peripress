package org.bondarenko.core.sorting;

public enum SortingType {
    ASCENDING("ASCENDING"),
    DESCENDING("DESCENDING");

    private final String type;

    SortingType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
