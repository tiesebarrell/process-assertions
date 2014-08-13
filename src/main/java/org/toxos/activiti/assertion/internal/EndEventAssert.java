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
import static org.toxos.activiti.assertion.internal.Assert.assertThat;
import static org.toxos.activiti.assertion.internal.Assert.equalList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.activiti.engine.history.HistoricActivityInstance;
import org.toxos.activiti.assertion.LogMessage;
import org.toxos.activiti.assertion.ProcessAssertConfiguration;

/**
 * Provides assertions for end events.
 * 
 * @author Tiese Barrell
 */
final class EndEventAssert extends ProcessAssertableBase implements EndEventAssertable {

    protected EndEventAssert(ProcessAssertConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void processEndedAndInExclusiveEndEvent(final String processInstanceId, final String endEventId) {

        // Assert the process instance is ended
        new ProcessInstanceAssert(getConfiguration()).processIsEnded(processInstanceId);

        // Assert that there is exactly one historic activity instance for end
        // events and that it has the correct id
        trace(LogMessage.PROCESS_10, processInstanceId, endEventId);
        final List<HistoricActivityInstance> historicActivityInstances = getHistoryService().createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).activityType("endEvent").finished().list();

        assertThat(historicActivityInstances.size(), is(1));
        assertThat(historicActivityInstances.get(0).getActivityId(), is(endEventId));
    }

    @Override
    public void processEndedAndInEndEvents(final String processInstanceId, final String... endEventIds) {

        // Assert the process instance is ended
        new ProcessInstanceAssert(getConfiguration()).processIsEnded(processInstanceId);

        // Assert that there are the exact amount of historic activity instances
        // for end events and that ids match exactly
        trace(LogMessage.PROCESS_12, endEventIds.length, processInstanceId, AssertUtils.arrayToString(endEventIds));

        final List<HistoricActivityInstance> historicActivityInstances = getHistoryService().createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).activityType("endEvent").finished().list();

        final List<String> historicEndEventIds = new ArrayList<String>();
        for (final HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            historicEndEventIds.add(historicActivityInstance.getActivityId());
        }

        // Catch any exceptions so a detailed log message can be shown. The
        // context of expected and actual end event ids is only available here.
        try {
            assertThat(historicActivityInstances.size(), is(endEventIds.length));

            assertThat(historicEndEventIds, is(equalList(Arrays.asList(endEventIds))));
        } catch (final AssertionError ae) {
            debug(LogMessage.ERROR_PROCESS_5, endEventIds.length, historicEndEventIds.size(), AssertUtils.arrayToString(endEventIds),
                    AssertUtils.arrayToString(historicEndEventIds.toArray()));
            // rethrow to ensure handled properly at higher level
            throw ae;
        }

    }
}
