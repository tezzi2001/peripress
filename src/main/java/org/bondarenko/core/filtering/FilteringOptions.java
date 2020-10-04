package org.bondarenko.core.filtering;

import java.util.List;

public class FilteringOptions {
    private final List<Theme> themes;
    private final List<Type> types;
    private final boolean isFree;

    public FilteringOptions(List<Theme> themes, List<Type> types, boolean isFree) {
        this.themes = themes;
        this.types = types;
        this.isFree = isFree;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public List<Type> getTypes() {
        return types;
    }

    public boolean isFree() {
        return isFree;
    }
}
