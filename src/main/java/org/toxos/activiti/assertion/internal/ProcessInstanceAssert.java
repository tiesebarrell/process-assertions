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

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Assert;
import org.toxos.activiti.assertion.LogMessage;
import org.toxos.activiti.assertion.ProcessAssertConfiguration;

/**
 * Provides assertions for process instances.
 * 
 * @author Tiese Barrell
 */
final class ProcessInstanceAssert extends ProcessAssertableBase implements ProcessInstanceAssertable {

    protected ProcessInstanceAssert(ProcessAssertConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void processIsActive(final String processInstanceId) {

        // Assert there is a running process instance
        trace(LogMessage.PROCESS_2, processInstanceId);
        final ProcessInstance processInstance = getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        Assert.assertNotNull(processInstance);

        trace(LogMessage.PROCESS_7, processInstanceId);
        Assert.assertFalse(processInstance.isEnded());

        trace(LogMessage.PROCESS_8, processInstanceId);
        Assert.assertFalse(processInstance.isSuspended());

        // Assert that the historic process instance is not ended
        trace(LogMessage.PROCESS_3, processInstanceId);
        final HistoricProcessInstance historicProcessInstance = getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstanceId)
                .singleResult();

        historicProcessInstanceNotEnded(historicProcessInstance);

    }

    @Override
    public void processIsEnded(final String processInstanceId) {

        // Assert there is no running process instance
        trace(LogMessage.PROCESS_6, processInstanceId);
        final ProcessInstance processInstance = getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

        Assert.assertNull(processInstance);

        // Assert there is a historic process instance and it is ended
        trace(LogMessage.PROCESS_4, processInstanceId);
        final HistoricProcessInstance historicProcessInstance = getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstanceId)
                .singleResult();

        historicProcessInstanceEnded(historicProcessInstance);

    }

    private static void historicProcessInstanceNotEnded(final HistoricProcessInstance historicProcessInstance) {
        Assert.assertNotNull(historicProcessInstance);
        Assert.assertNull(historicProcessInstance.getEndTime());
    }

    private static void historicProcessInstanceEnded(final HistoricProcessInstance historicProcessInstance) {
        Assert.assertNotNull(historicProcessInstance);
        Assert.assertNotNull(historicProcessInstance.getEndTime());
    }
}
