package org.bondarenko.core.sorting;

public enum SortingOption {
    BY_NAME("BY_NAME"),
    BY_PRICE("BY_PRICE"),
    BY_SUBSCRIPTIONS("BY_SUBSCRIPTIONS"),
    NO_OPTION("NO_OPTION");

    private final String option;

    SortingOption(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return option;
    }
}
