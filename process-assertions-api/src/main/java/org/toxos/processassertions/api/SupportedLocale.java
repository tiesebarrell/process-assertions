/*******************************************************************************
 * Copyright 2017 Tiese Barrell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
    SPANISH_ES("es", "ES"),
    CZECH_CZ("cs", "CZ"),
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
