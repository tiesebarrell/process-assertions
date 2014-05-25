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

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.history.HistoricActivityInstance;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link EndEventAssert#processEndedAndInEndEvents(String, String...)}.
 * 
 * @author Tiese Barrell
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class EndEventAssertProcessEndedAndInEndEventsTest extends EndEventAssertTestBase {

    private final String targetEndEventId = "targetEndEvent";
    private final String otherEndEventId = "otherEndEvent";
    private final String unwantedEndEventId = "unwantedEndEvent";

    private List<HistoricActivityInstance> historicActivityInstances;

    @Mock
    private HistoricActivityInstance historicActivityInstanceMock3;

    @Before
    public void beforeTest() throws Exception {
        historicActivityInstances = new ArrayList<>(1);
        historicActivityInstances.add(historicActivityInstanceMock1);
        historicActivityInstances.add(historicActivityInstanceMock2);

        when(historicActivityInstanceQueryMock.list()).thenReturn(historicActivityInstances);
        when(historicActivityInstanceMock1.getActivityId()).thenReturn(targetEndEventId);
        when(historicActivityInstanceMock2.getActivityId()).thenReturn(otherEndEventId);
        when(historicActivityInstanceMock3.getActivityId()).thenReturn(unwantedEndEventId);
    }

    @Test
    public void testProcessEndedAndInExclusiveEndEvent() throws Exception {
        classUnderTest.processEndedAndInEndEvents(processInstanceId, targetEndEventId, otherEndEventId);
    }

    @Test(expected = AssertionError.class)
    public void testProcessEndedAndInExclusiveEndEvent_TooFewHistoricActivityInstancesFound() throws Exception {
        historicActivityInstances.remove(historicActivityInstanceMock2);
        classUnderTest.processEndedAndInEndEvents(processInstanceId, targetEndEventId, otherEndEventId);
    }

    @Test(expected = AssertionError.class)
    public void testProcessEndedAndInExclusiveEndEvent_TooManyHistoricActivityInstancesFound() throws Exception {
        historicActivityInstances.add(historicActivityInstanceMock3);
        classUnderTest.processEndedAndInEndEvents(processInstanceId, targetEndEventId, otherEndEventId);
    }

    @Test(expected = AssertionError.class)
    public void testProcessEndedAndInExclusiveEndEvent_UnequalCollection() throws Exception {
        historicActivityInstances.remove(historicActivityInstanceMock2);
        historicActivityInstances.add(historicActivityInstanceMock3);
        classUnderTest.processEndedAndInEndEvents(processInstanceId, targetEndEventId, otherEndEventId);
    }

    @Test(expected = AssertionError.class)
    public void testProcessEndedAndInExclusiveEndEvent_HistoricProcessInstanceNotFound() throws Exception {
        when(historicProcessInstanceQueryMock.singleResult()).thenReturn(null);
        classUnderTest.processEndedAndInEndEvents(processInstanceId, targetEndEventId, otherEndEventId);
    }

}
