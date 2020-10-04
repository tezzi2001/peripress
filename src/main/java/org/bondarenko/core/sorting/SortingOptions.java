package org.bondarenko.core.sorting;

public class SortingOptions {
    private final boolean isDescending;
    private final SortingOption sortingOption;

    public SortingOptions(boolean isDescending, SortingOption sortingOption) {
        this.isDescending = isDescending;
        this.sortingOption = sortingOption;
    }

    public SortingOption getSortingOption() {
        return sortingOption;
    }

    public boolean isDescending() {
        return isDescending;
    }
}
