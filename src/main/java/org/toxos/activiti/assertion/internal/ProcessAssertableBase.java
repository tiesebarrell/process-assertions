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

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.impl.history.HistoryLevel;
import org.toxos.activiti.assertion.AbstractProcessAssert;
import org.toxos.activiti.assertion.ProcessAssertConfiguration;

/**
 * Base class for process assertables. Provides access to services from configuration.
 * 
 * @author Tiese Barrell
 * 
 */
class ProcessAssertableBase extends AbstractProcessAssert {

    private final ProcessAssertConfiguration configuration;

    protected ProcessAssertableBase(final ProcessAssertConfiguration configuration) {
        super();
        this.configuration = configuration;
    }

    /**
     * Gets the {@link RuntimeService} from the configuration.
     * 
     * @return the runtime service
     */
    protected final RuntimeService getRuntimeService() {
        return configuration.getEngineServices().getRuntimeService();
    }

    /**
     * Gets the {@link TaskService} from the configuration.
     * 
     * @return the task service
     */
    protected final TaskService getTaskService() {
        return configuration.getEngineServices().getTaskService();
    }

    /**
     * Gets the {@link HistoryService} from the configuration.
     * 
     * @return the history service
     */
    protected final HistoryService getHistoryService() {
        return configuration.getEngineServices().getHistoryService();
    }

    /**
     * Gets the {@link ProcessEngine}.
     * 
     * @return the process engine
     */
    protected final ProcessEngine getProcessEngine() {
        return ProcessEngines.getDefaultProcessEngine();
    }

    /**
     * Gets the configuration for this assertable.
     * 
     * @return the process assert configuration
     */
    protected ProcessAssertConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Determines whether the history level of the process engine is set to {@link HistoryLevel.FULL}.
     * 
     * @return true if the history level is set to full, false otherwise
     */
    protected final boolean historyLevelIsFull(final ProcessEngineConfiguration processEngineConfiguration) {
        return HistoryLevel.FULL.equals(processEngineConfiguration.getHistoryLevel());
    }

    /**
     * Gets a list of {@link HistoricVariableUpdate}s in descending order of update time for the provided process
     * instance id and the provided name for the process variable.
     * 
     * @param processInstanceId
     *            the process instance's id to get the variables for
     * @param processVariableName
     *            the name of the process variable to get the updates for
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

}
