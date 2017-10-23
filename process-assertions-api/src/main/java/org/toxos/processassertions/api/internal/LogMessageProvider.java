/*******************************************************************************
 * Copyright 2014 Tiese Barrell
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
package org.toxos.processassertions.api.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toxos.processassertions.api.LogMessage;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Provider for messages corresponding with {@link LogMessage}s for a specific
 * {@link Locale}.
 *
 * @author Tiese Barrell
 */
public class LogMessageProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogMessageProvider.class);

    private final String baseName;
    private final Locale locale;

    private ResourceBundle bundle;

    /**
     * Constructs a LogMessageProvider using the bundle base name and the provided {@link Locale}.
     *
     * @param bundleBaseName the base name for the messages bundle
     * @param locale         the locale to be used
     */
    LogMessageProvider(final String bundleBaseName, final Locale locale) {
        super();
        this.baseName = bundleBaseName;
        this.locale = locale;
    }

    /**
     * Gets a message by the provided key.
     * @param key the key in the bundle
     * @return the value of the entry in the bundle
     */
    public String getMessageByKey(final String key) {
        return getBundle().getString(key);
    }

    private ResourceBundle getBundle() {
        loadBundleIfRequired();
        return bundle;
    }

    private void loadBundleIfRequired() {
        if (bundleLoadIsRequired()) {
            loadBundle();
        }
    }

    private boolean bundleLoadIsRequired() {
        return bundle == null;
    }

    private void loadBundle() {
        LOGGER.trace("Loading bundle from baseName " + baseName + " for locale " + locale);
        bundle = ResourceBundle.getBundle(baseName, locale);
    }

}
