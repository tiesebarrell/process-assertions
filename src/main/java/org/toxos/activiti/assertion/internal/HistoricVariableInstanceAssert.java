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

import java.util.List;

import org.activiti.engine.history.HistoricVariableUpdate;
import org.junit.Assert;
import org.toxos.activiti.assertion.ProcessAssertConfiguration;

/**
 * Provides assertions for historic variable instances.
 */
final class HistoricVariableInstanceAssert extends ProcessAssertableBase implements HistoricVariableInstanceAssertable {

    protected HistoricVariableInstanceAssert(ProcessAssertConfiguration configuration) {
        super(configuration);
    }

    public boolean historicProcessVariableLatestValueEquals(final String processInstanceId, final String processVariableName, final Object expectedValue) {

        if (historyLevelIsFull()) {
            Assert.fail("To check for latest historic values of process variables, the history level of the Activiti ProcessEngine must be set to full.");
        }

        boolean result = false;

        try {
            final List<HistoricVariableUpdate> variableUpdates = getDescendingVariableUpdates(processInstanceId, processVariableName);

            if (variableUpdates.size() > 0) {
                final HistoricVariableUpdate latest = variableUpdates.get(0);
                Assert.assertNotNull(latest);
                Assert.assertEquals(processVariableName, latest.getVariableName());
                Assert.assertEquals(expectedValue, latest.getValue());
                result = true;
            } else {
                result = false;
            }

        } catch (final AssertionError ae) {
            result = false;
        }

        return result;
    }
}
