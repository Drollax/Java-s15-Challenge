package com.library.constants;

public enum Categories {
    FICTION("Fiction"),
    NON_FICTION("Non-Fiction"),
    SCIENCE("Science"),
    HISTORY("History"),
    MAGAZINE("Magazine"),
    JOURNAL("Journal");

    private final String displayName;

    Categories(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;    }
}
