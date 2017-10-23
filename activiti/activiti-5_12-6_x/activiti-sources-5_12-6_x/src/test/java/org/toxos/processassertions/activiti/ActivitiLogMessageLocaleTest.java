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
package org.toxos.processassertions.activiti;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toxos.processassertions.api.SupportedLocale;
import org.toxos.processassertions.api.internal.AssertUtils;

import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.fail;

/**
 * Tests for log message availability in bundles of supported locales.
 *
 * @author Tiese Barrell
 *
 */
public class ActivitiLogMessageLocaleTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiLogMessageLocaleTest.class);

    private static final String LOG_MESSAGES = "org.toxos.processassertions.activiti.messages.LogMessages";

    @Test
    public void allLogMessagesHaveValuesForSupportedLocales() throws Exception {
        final Map<SupportedLocale, Collection<LogMessage>> missingValues = new HashMap<>();
        for (final SupportedLocale supportedLocale : SupportedLocale.values()) {
            LOGGER.debug("Checking locale " + supportedLocale.getLocale() + " for LogMessage values");
            final Properties bundle = loadBundleForLocale(supportedLocale);
            final List<LogMessage> missingValuesForLocale = checkForMissingEntries(supportedLocale, bundle);
            if (!missingValuesForLocale.isEmpty()) {
                missingValues.put(supportedLocale, missingValuesForLocale);
            }
        }
        if (!missingValues.isEmpty()) {
            fail(buildAssertionErrorMessage(missingValues));
        }
    }

    private String buildAssertionErrorMessage(final Map<SupportedLocale, Collection<LogMessage>> missingValues) {
        final StringBuilder builder = new StringBuilder();

        for(final Map.Entry<SupportedLocale, Collection<LogMessage>> entry : missingValues.entrySet()) {
            builder.append("There are entries missing in the LogMessages bundle for locale ").append(entry.getKey().getLocale())
                    .append(": \n");
            for (final LogMessage logMessage : entry.getValue()) {
                builder.append(logMessage.getBundleKey()).append(" has no value. \n");
            }
        }
        return builder.toString();
    }

    private Properties loadBundleForLocale(final SupportedLocale supportedLocale) throws Exception {
        final Properties properties = new Properties();

        final String resourceBundlePathForLocale = getResourceBundlePathForLocale(supportedLocale);
        LOGGER.info("Loading bundle from path '" + resourceBundlePathForLocale + "'");

        final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceBundlePathForLocale);
        properties.load(is);
        return properties;
    }

    private List<LogMessage> checkForMissingEntries(SupportedLocale supportedLocale, final Properties bundle) throws Exception {
        final List<LogMessage> missingEntries = new ArrayList<LogMessage>();
        LOGGER.debug("Checking values for SupportedLocale " + supportedLocale);
        for (final LogMessage logMessage : LogMessage.values()) {
            final String foundEntry = bundle.getProperty(logMessage.getBundleKey());
            final String displayEntry = foundEntry == null ? ">>> MISSING <<<" : foundEntry;
            LOGGER.debug("    Entry " + logMessage + ": " + displayEntry);
            if (AssertUtils.stringIsBlank(foundEntry)) {
                missingEntries.add(logMessage);
            }
        }
        return missingEntries;
    }

    private String getResourceBundlePathForLocale(final SupportedLocale supportedLocale) {
        final String localeSpecificPath = getLocaleSpecificPath(supportedLocale);
        return AssertUtils.replaceStringInString(LOG_MESSAGES, ".", "/") + localeSpecificPath + ".properties";
    }

    private String getLocaleSpecificPath(final SupportedLocale supportedLocale) {
        String result = "";
        if (supportedLocale != SupportedLocale.DEFAULT && supportedLocale != SupportedLocale.ENGLISH_US) {
            result = "_" + supportedLocale.getLocale().toString();
        }
        return result;
    }

}
