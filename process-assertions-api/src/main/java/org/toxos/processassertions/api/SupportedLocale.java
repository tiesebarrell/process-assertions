package org.toxos.processassertions.api;

import java.util.Locale;

/**
 * Created by tiesebarrell on 20/04/2017.
 */
public enum SupportedLocale {

    ENGLISH_US("en", "US"),
    DUTCH_NL("nl", "NL"),
    DEFAULT("en", "US");

    private final String language;
    private final String country;

    private SupportedLocale(final String language, final String country) {
        this.language = language;
        this.country = country;
    }

    public Locale getLocale() {
        return new Locale(language, country);
    }

}
