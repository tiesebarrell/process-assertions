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
package org.toxos.processassertions.flowable;

import org.flowable.engine.common.impl.history.HistoryLevel;
import org.flowable.engine.history.HistoricDetail;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricVariableUpdate;
import org.hamcrest.CoreMatchers;
import org.toxos.processassertions.api.LogMessage;
import org.toxos.processassertions.api.internal.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * Provides assertions for historic variable instances.
 *
 * @author Tiese Barrell
 */
final class HistoricVariableInstanceAssert extends AbstractProcessAssertable implements HistoricVariableInstanceAssertable {

    HistoricVariableInstanceAssert(ApiCallback callback, final ProcessAssertFlowableConfiguration configuration) {
        super(callback, configuration);
    }

    @Override
    public void historicProcessVariableLatestValueEquals(final String processInstanceId, final String processVariableName,
            final Object expectedValue) {

        // Assert the history level is set to full
        callback.trace(LogMessage.CONFIGURATION_1, HistoryLevel.FULL.name());
        checkHistoryLevelIsFull();

        // Assert there is a historic process instance by the provided id
        callback.trace(LogMessage.PROCESS_13, processInstanceId);
        final HistoricProcessInstance historicProcessInstance = getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstanceId)
                .singleResult();
        Assert.assertThat(historicProcessInstance, is(notNullValue()));

        // Assert there is a variable by the provided name
        callback.trace(LogMessage.VARIABLE_1, processVariableName, processInstanceId);
        final List<HistoricVariableUpdate> variableUpdates = getDescendingVariableUpdates(processInstanceId, processVariableName);
        Assert.assertThat(variableUpdates, CoreMatchers.not(IsEmptyCollection.empty()));

        // Assert the latest value of the variable is equal to the expected value
        callback.trace(LogMessage.VARIABLE_2, processVariableName, expectedValue);
        final HistoricVariableUpdate latestValue = variableUpdates.get(0);
        Assert.assertThat(latestValue.getValue(), is(expectedValue));
    }

    private void checkHistoryLevelIsFull() {
        if (!historyLevelIsFull()) {
            callback.fail(ConfigurationException.class, LogMessage.ERROR_CONFIGURATION_1, HistoryLevel.FULL.name(), getConfiguredHistoryLevel().name());
        }
    }

    /**
     * Gets a list of {@link HistoricVariableUpdate}s in descending order of update time for the provided process
     * instance id and the provided name for the process variable.
     *
     * @param processInstanceId   the process instance's id to get the variables for
     * @param processVariableName the name of the process variable to get the updates for
     * @return a list of variable updates, in descending order
     */
    protected final List<HistoricVariableUpdate> getDescendingVariableUpdates(final String processInstanceId, final String processVariableName) {

        final List<HistoricVariableUpdate> result = new ArrayList<HistoricVariableUpdate>();

        final List<HistoricDetail> historicDetails = getHistoryService().createHistoricDetailQuery().variableUpdates().processInstanceId(processInstanceId)
                .orderByVariableName().asc().orderByTime().desc().list();

        boolean reachedTargetVariable = false;

        if (historicDetails != null && !historicDetails.isEmpty()) {
            for (final HistoricDetail historicDetail : historicDetails) {

                if (historicDetail != null && historicDetail instanceof HistoricVariableUpdate) {
                    final HistoricVariableUpdate historicVariableUpdate = (HistoricVariableUpdate) historicDetail;
                    final boolean isForTarget = isHistoricVariableUpdateForTargetVariable(processVariableName, historicVariableUpdate);

                    if (isForTarget && !reachedTargetVariable) {
                        reachedTargetVariable = true;
                        result.add(historicVariableUpdate);
                    } else if (isForTarget) {
                        result.add(historicVariableUpdate);
                    } else if (reachedTargetVariable) {
                        break;
                    }
                }
            }
        }

        return result;
    }

    private boolean isHistoricVariableUpdateForTargetVariable(final String processVariableName, final HistoricVariableUpdate historicVariableUpdate) {
        return processVariableName.equals(historicVariableUpdate.getVariableName());
    }

    private final boolean historyLevelIsFull() {
        return HistoryLevel.FULL.equals(getConfiguredHistoryLevel());
    }
}
