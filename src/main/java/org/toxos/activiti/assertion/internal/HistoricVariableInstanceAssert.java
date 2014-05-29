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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.toxos.activiti.assertion.internal.Assert.assertThat;
import static org.toxos.activiti.assertion.internal.IsEmptyCollection.empty;

import java.util.List;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.impl.history.HistoryLevel;
import org.toxos.activiti.assertion.LogMessage;
import org.toxos.activiti.assertion.ProcessAssertConfiguration;

/**
 * Provides assertions for historic variable instances.
 * 
 * @author Tiese Barrell
 */
final class HistoricVariableInstanceAssert extends ProcessAssertableBase implements HistoricVariableInstanceAssertable {

    protected HistoricVariableInstanceAssert(ProcessAssertConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void historicProcessVariableLatestValueEquals(final String processInstanceId, final String processVariableName, final Object expectedValue) {

        // Assert the history level is set to full
        trace(LogMessage.CONFIGURATION_1, HistoryLevel.FULL.name());
        checkHistoryLevelIsFull();

        // Assert there is a historic process instance by the provided id
        trace(LogMessage.PROCESS_13, processInstanceId);
        final HistoricProcessInstance historicProcessInstance = getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstanceId)
                .singleResult();
        assertThat(historicProcessInstance, is(notNullValue()));

        // Assert there is a variable by the provided name
        trace(LogMessage.VARIABLE_1, processVariableName, processInstanceId);
        final List<HistoricVariableUpdate> variableUpdates = getDescendingVariableUpdates(processInstanceId, processVariableName);
        assertThat(variableUpdates, not(empty()));

        // Assert the latest value of the variable is equal to the expected value
        trace(LogMessage.VARIABLE_2, processVariableName, expectedValue);
        final HistoricVariableUpdate latestValue = variableUpdates.get(0);
        assertThat(latestValue.getValue(), is(expectedValue));
    }

    private void checkHistoryLevelIsFull() {
        if (!historyLevelIsFull(getConfiguration().getProcessEngineConfiguration())) {
            fail(LogMessage.ERROR_CONFIGURATION_1, HistoryLevel.FULL.name(), getConfiguration().getProcessEngineConfiguration().getHistoryLevel().name());
        }
    }
}
