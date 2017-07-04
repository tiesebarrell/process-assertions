package org.toxos.processassertions.api;

import java.util.Locale;

/**
 * Supported locales for assertion logging.
 *
 * @author Tiese Barrell
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

    /**
     * Gets the {@link Locale} for this supported locale.
     *
     * @return the Locale
     */
    public Locale getLocale() {
        return new Locale(language, country);
    }

}
