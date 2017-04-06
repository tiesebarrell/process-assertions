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

import org.toxos.processassertions.api.internal.AssertFactory;

import java.util.Locale;

/**
 * Provides a configuration used when performing process assertions.
 * 
 * @author Tiese Barrell
 */
public interface ProcessAssertConfiguration {

    /**
     * Gets the {@link Locale} configured. The locale is used to determine the messages displayed to users.
     * 
     * @return the configured locale.
     */
    Locale getLocale();

    AssertFactory getAssertFactory();

}
