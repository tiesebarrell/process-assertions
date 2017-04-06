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
 * Assertions for historic variable instances.
 * 
 * @author Tiese Barrell
 * 
 */
public interface HistoricVariableInstanceAssertable {

    /**
     * Asserts the process variable has the expected value as its latest value.
     * 
     * @param processInstanceId
     *            the process instance's id to check for
     * @param processVariableName
     *            the name of the process variable to check
     * @param expectedValue
     *            the expected value for the process variable
     */
    void historicProcessVariableLatestValueEquals(final String processInstanceId, final String processVariableName, final Object expectedValue);

}
