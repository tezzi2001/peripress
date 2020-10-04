package org.bondarenko.core.filtering;

public enum Theme {
    SCIENCE("SCIENCE"),
    NATURE("NATURE"),
    ART("ART");

    private final String theme;

    Theme(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return theme;
    }
}
