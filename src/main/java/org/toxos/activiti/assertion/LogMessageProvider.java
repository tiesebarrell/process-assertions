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
package org.toxos.activiti.assertion;

import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provider for messages corresponding with {@link LogMessage}s for a specific
 * {@link Locale}.
 * 
 * @author Tiese Barrell
 * 
 */
public class LogMessageProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogMessageProvider.class);

    private final String baseName;

    private ResourceBundle bundle;

    /**
     * Constructs a LogMessageProvider.
     */
    public LogMessageProvider() {
        super();
        this.baseName = Constants.LOG_MESSAGES_BUNDLE_NAME;
    }

    /**
     * Flushes any bundle currently cached, forcing a reload on next request.
     */
    public void flush() {
        this.bundle = null;
    }

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
        LOGGER.trace("Loading bundle from baseName " + baseName + " for Locale " + ProcessAssert.getConfiguration().getLocale());
        bundle = ResourceBundle.getBundle(baseName, ProcessAssert.getConfiguration().getLocale());
    }

}
