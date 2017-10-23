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
package org.toxos.processassertions.api.internal;

/**
 * Base exception for exceptions within Process Assertions.
 *
 * @author Tiese Barrell
 */
public class ProcessAssertionsException extends Exception {

    /**
     * Constructs a new {@link ProcessAssertionsException} with the provided message.
     *
     * @param message the message
     */
    public ProcessAssertionsException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link ConfigurationException} with the provided message and cause.
     *
     * @param message the message
     * @param cause the cause
     */
    public ProcessAssertionsException(String message, Throwable cause) {
        super(message, cause);
    }
}
