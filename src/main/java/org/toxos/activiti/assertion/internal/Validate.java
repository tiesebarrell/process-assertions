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
package org.toxos.activiti.assertion.internal;

/**
 * Validates input provided.
 * 
 * @author Tiese Barrell
 *
 */
public final class Validate {

    /**
     * Validates that the provided argument is not {@code null}; otherwise throw an exception.
     *
     * @param object
     *            the object to check
     * @throws NullPointerException
     *             if the object is {@code null}
     */
    public static final void notNull(final Object object) {
        if (object == null) {
            throw new NullPointerException("Validated object is null");
        }
    }
}