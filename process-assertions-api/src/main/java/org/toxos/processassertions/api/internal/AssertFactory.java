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
 * Factory for internal process assert classes.
 * 
 * @author Tiese Barrell
 * 
 */
public interface AssertFactory {

    /**
     * Gets an instance of {@link ProcessInstanceAssertable}.
     *
     * @param callback
     *            the callback to be used
     * @return a ProcessInstanceAssertable
     */
    ProcessInstanceAssertable getProcessInstanceAssertable(final ApiCallback callback);

    /**
     * Gets an instance of {@link EndEventAssertable}.
     *
     * @param callback
     *            the callback to be used
     * @return a EndEventAssertable
     */
    EndEventAssertable getEndEventAssertable(final ApiCallback callback);

    /**
     * Gets an instance of {@link TaskInstanceAssertable}.
     *
     * @param callback
     *            the callback to be used
     * @return a TaskInstanceAssertable
     */
    TaskInstanceAssertable getTaskInstanceAssertable(final ApiCallback callback);

    /**
     * Gets an instance of {@link HistoricVariableInstanceAssertable}.
     *
     * @param callback
     *            the callback to be used
     * @return a HistoricVariableInstanceAssertable
     */
    HistoricVariableInstanceAssertable getHistoricVariableInstanceAssertable(final ApiCallback callback);

}
