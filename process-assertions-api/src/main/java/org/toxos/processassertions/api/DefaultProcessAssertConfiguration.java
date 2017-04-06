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
package org.toxos.processassertions.api;

import java.util.Locale;

/**
 * Base implementation for {@link ProcessAssertConfiguration} that provides defaults for unspecified configuration
 * items.
 * 
 * @author Tiese Barrell
 * 
 */
public abstract class DefaultProcessAssertConfiguration implements ProcessAssertConfiguration {

    protected Locale locale;

    protected DefaultProcessAssertConfiguration() {
        super();
        this.locale = Constants.DEFAULT_LOCALE;
    }

    public DefaultProcessAssertConfiguration(final Locale locale) {
        this();
        this.locale = locale;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

}
