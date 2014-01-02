/*******************************************************************************
 * Copyright 2013 Tiese Barrell
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
package org.toxos.activiti.assertion;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * Abstract base class for log message tests.
 * 
 * @author Tiese Barrell
 * 
 */
public abstract class AbstractLogMessageTest {

    protected List<LogMessage> checkForMissingEntries() throws Exception {
        final List<LogMessage> missingEntries = new ArrayList<LogMessage>();

        final Properties properties = new Properties();

        final String resourceBundlePathForLocale = getResourceBundlePathForLocale();
        System.out.println("Loading bundle from path '" + resourceBundlePathForLocale + "'");

        final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceBundlePathForLocale);
        properties.load(is);

        for (final LogMessage logMessage : LogMessage.values()) {

            final String entry = properties.getProperty(logMessage.getBundleKey());
            if (StringUtils.isBlank(entry)) {
                missingEntries.add(logMessage);
            }
        }
        return missingEntries;
    }

    private String getResourceBundlePathForLocale() {
        final String localeSpecificPath = getLocaleSpecificPath();
        return StringUtils.replace(Constants.LOG_MESSAGES_BUNDLE_NAME, ".", "/") + localeSpecificPath + ".properties";
    }

    private String getLocaleSpecificPath() {
        String result = "";
        if (ProcessAssert.getConfiguration() != null && !DefaultProcessAssertConfiguration.DEFAULT_LOCALE.equals(ProcessAssert.getConfiguration().getLocale())) {
            result = "_" + ProcessAssert.getConfiguration().getLocale().toString();
        }
        return result;
    }

}
