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
package org.toxos.processassertions.activiti;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.toxos.processassertions.api.LogMessage;
import org.toxos.processassertions.api.internal.ApiCallback;
import org.toxos.processassertions.api.internal.Assert;
import org.toxos.processassertions.api.internal.ProcessInstanceAssertable;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;

/**
 * Provides assertions for process instances.
 *
 * @author Tiese Barrell
 */
final class ProcessInstanceAssert extends AbstractProcessAssertable implements ProcessInstanceAssertable {

    ProcessInstanceAssert(ApiCallback callback, final ProcessAssertActivitiConfiguration configuration) {
        super(callback, configuration);
    }

    @Override
    public void processIsActive(final String processInstanceId) {

        // Assert there is a running process instance
        callback.trace(LogMessage.PROCESS_2, processInstanceId);
        final ProcessInstance processInstance = getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        Assert.assertThat(processInstance, is(notNullValue()));

        callback.trace(LogMessage.PROCESS_7, processInstanceId);
        Assert.assertThat(processInstance.isEnded(), is(false));

        callback.trace(LogMessage.PROCESS_8, processInstanceId);
        Assert.assertThat(processInstance.isSuspended(), is(false));

        // Assert that the historic process instance is not ended
        callback.trace(LogMessage.PROCESS_3, processInstanceId);
        final HistoricProcessInstance historicProcessInstance = getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstanceId)
                .singleResult();

        historicProcessInstanceNotEnded(historicProcessInstance);
    }

    @Override
    public void processIsEnded(final String processInstanceId) {

        // Assert there is no running process instance
        callback.trace(LogMessage.PROCESS_6, processInstanceId);
        final ProcessInstance processInstance = getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

        Assert.assertThat(processInstance, is(nullValue()));

        // Assert there is a historic process instance and it is ended
        callback.trace(LogMessage.PROCESS_4, processInstanceId);
        final HistoricProcessInstance historicProcessInstance = getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        historicProcessInstanceEnded(historicProcessInstance);
    }

    @Override
    public void processIsInActivity(final String processInstanceId, final String activityId) {

        // Assert there is a running process instance
        processIsActive(processInstanceId);

        // Assert there is at least one execution in the activity
        callback.trace(LogMessage.PROCESS_14, processInstanceId, activityId);

        final List<Execution> executions = getRuntimeService().createExecutionQuery().processInstanceId(processInstanceId).activityId(activityId).list();
        Assert.assertThat(executions.isEmpty(), is(false));
    }

    private static void historicProcessInstanceNotEnded(final HistoricProcessInstance historicProcessInstance) {
        Assert.assertThat(historicProcessInstance, is(notNullValue()));
        Assert.assertThat(historicProcessInstance.getEndTime(), is(nullValue()));
    }

    private static void historicProcessInstanceEnded(final HistoricProcessInstance historicProcessInstance) {
        Assert.assertThat(historicProcessInstance, is(notNullValue()));
        Assert.assertThat(historicProcessInstance.getEndTime(), is(notNullValue()));
    }
}
