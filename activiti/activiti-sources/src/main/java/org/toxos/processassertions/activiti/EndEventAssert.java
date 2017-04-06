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

import org.activiti.engine.history.HistoricActivityInstance;
import org.hamcrest.CoreMatchers;
import org.toxos.processassertions.api.LogMessage;
import org.toxos.processassertions.api.internal.ApiCallback;
import org.toxos.processassertions.api.internal.Assert;
import org.toxos.processassertions.api.internal.AssertUtils;
import org.toxos.processassertions.api.internal.EndEventAssertable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

/**
 * Provides assertions for end events.
 *
 * @author Tiese Barrell
 */
final class EndEventAssert extends AbstractProcessAssertable implements EndEventAssertable {

    EndEventAssert(ApiCallback callback) {
        super(callback);
    }

    @Override public void processEndedAndInExclusiveEndEvent(final String processInstanceId, final String endEventId) {

        // Assert the process instance is ended
        new ProcessInstanceAssert(callback).processIsEnded(processInstanceId);

        // Assert that there is exactly one historic activity instance for end
        // events and that it has the correct id
        callback.trace(LogMessage.PROCESS_10, processInstanceId, endEventId);
        final List<HistoricActivityInstance> historicActivityInstances = getHistoryService().createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).activityType("endEvent").finished().list();

        Assert.assertThat(historicActivityInstances.size(), is(1));
        Assert.assertThat(historicActivityInstances.get(0).getActivityId(), is(endEventId));
    }

    @Override public void processEndedAndInEndEvents(final String processInstanceId, final String... endEventIds) {

        // Assert the process instance is ended
        new ProcessInstanceAssert(callback).processIsEnded(processInstanceId);

        // Assert that there are the exact amount of historic activity instances
        // for end events and that ids match exactly
        callback.trace(LogMessage.PROCESS_12, endEventIds.length, processInstanceId, AssertUtils.arrayToString(endEventIds));

        final List<HistoricActivityInstance> historicActivityInstances = getHistoryService().createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).activityType("endEvent").finished().list();

        final List<String> historicEndEventIds = new ArrayList<String>();
        for (final HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            historicEndEventIds.add(historicActivityInstance.getActivityId());
        }

        // Catch any exceptions so a detailed log message can be shown. The
        // context of expected and actual end event ids is only available here.
        try {
            Assert.assertThat(historicActivityInstances.size(), is(endEventIds.length));

            Assert.assertThat(historicEndEventIds, CoreMatchers.is(Assert.equalCollection(Arrays.asList(endEventIds))));
        } catch (final AssertionError ae) {
            callback.debug(LogMessage.ERROR_PROCESS_5, endEventIds.length, historicEndEventIds.size(), AssertUtils.arrayToString(endEventIds),
                    AssertUtils.arrayToString(historicEndEventIds.toArray()));
            // rethrow to ensure handled properly at higher level
            throw ae;
        }

    }
}
