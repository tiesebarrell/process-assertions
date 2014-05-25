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
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link EndEventAssert#processEndedAndInExclusiveEndEvent(String, String)}.
 * 
 * @author Tiese Barrell
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class EndEventAssertProcessEndedAndInExclusiveEndEventTest extends EndEventAssertTestBase {

    private final String targetEndEventId = "targetEndEvent";
    private final String otherEndEventId = "otherEndEvent";

    private List<HistoricActivityInstance> historicActivityInstances;

    @Before
    public void beforeTest() throws Exception {
        historicActivityInstances = new ArrayList<>(1);
        historicActivityInstances.add(historicActivityInstanceMock1);

        when(historicActivityInstanceQueryMock.list()).thenReturn(historicActivityInstances);
        when(historicActivityInstanceMock1.getActivityId()).thenReturn(targetEndEventId);
        when(historicActivityInstanceMock2.getActivityId()).thenReturn(otherEndEventId);
    }

    @Test
    public void testProcessEndedAndInExclusiveEndEvent() throws Exception {
        classUnderTest.processEndedAndInExclusiveEndEvent(processInstanceId, targetEndEventId);
    }

    @Test(expected = AssertionError.class)
    public void testProcessEndedAndInExclusiveEndEvent_NoHistoricActivityInstancesFound() throws Exception {
        when(historicActivityInstanceQueryMock.list()).thenReturn(new ArrayList<HistoricActivityInstance>());
        classUnderTest.processEndedAndInExclusiveEndEvent(processInstanceId, targetEndEventId);
    }

    @Test(expected = AssertionError.class)
    public void testProcessEndedAndInExclusiveEndEvent_MultipleHistoricActivityInstancesFound() throws Exception {
        historicActivityInstances.add(historicActivityInstanceMock2);
        classUnderTest.processEndedAndInExclusiveEndEvent(processInstanceId, targetEndEventId);
    }

    @Test(expected = AssertionError.class)
    public void testProcessEndedAndInExclusiveEndEvent_MismatchedHistoricActivityInstanceId() throws Exception {
        when(historicActivityInstanceMock1.getActivityId()).thenReturn(otherEndEventId);
        classUnderTest.processEndedAndInExclusiveEndEvent(processInstanceId, targetEndEventId);
    }

    @Test(expected = AssertionError.class)
    public void testProcessEndedAndInExclusiveEndEvent_HistoricProcessInstanceNotFound() throws Exception {
        when(historicProcessInstanceQueryMock.singleResult()).thenReturn(null);
        classUnderTest.processEndedAndInExclusiveEndEvent(processInstanceId, targetEndEventId);
    }

}
