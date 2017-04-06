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

/**
 * Assertions for end events.
 * 
 * @author Tiese Barrell
 * 
 */
public interface EndEventAssertable {

    /**
     * Asserts the process is ended and in an exclusive end event.
     * 
     * @param processInstanceId
     *            the process instance's id to check for
     * @param endEventId
     *            the id of the end event to check for
     */
    void processEndedAndInExclusiveEndEvent(final String processInstanceId, final String endEventId);

    /**
     * Asserts the process is ended and in has reached a precise collection of end events.
     * 
     * @param processInstanceId
     *            the process instance's id to check for
     * @param endEventIds
     *            the ids of the end events to check for
     */
    void processEndedAndInEndEvents(final String processInstanceId, final String... endEventIds);

}
