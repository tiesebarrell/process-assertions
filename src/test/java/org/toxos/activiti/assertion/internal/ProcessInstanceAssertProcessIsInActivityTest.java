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

import org.activiti.engine.runtime.Execution;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link ProcessInstanceAssert#processIsInActivity(String, String)}.
 * 
 * @author Tiese Barrell
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class ProcessInstanceAssertProcessIsInActivityTest extends ProcessInstanceAssertTestBase {

    @Mock
    private Execution execution1;

    @Mock
    private Execution execution2;

    private List<Execution> executions;

    @Before
    public void beforeTest() throws Exception {
        when(processInstanceQueryMock.singleResult()).thenReturn(processInstanceMock);
        when(processInstanceMock.isEnded()).thenReturn(false);
        when(processInstanceMock.isSuspended()).thenReturn(false);
        when(historicProcessInstanceMock.getEndTime()).thenReturn(null);

        executions = new ArrayList<Execution>();

        when(executionQueryMock.list()).thenReturn(executions);
    }

    @Test
    public void testProcessIsInActivity() throws Exception {
        executions.add(execution1);
        classUnderTest.processIsInActivity(processInstanceId, activityId);
    }

    @Test
    public void testProcessIsInActivity_MultipleExecutions() throws Exception {
        executions.add(execution1);
        executions.add(execution2);
        classUnderTest.processIsInActivity(processInstanceId, activityId);
    }

    @Test(expected = AssertionError.class)
    public void testProcessIsInActivity_NoExecutions() throws Exception {
        classUnderTest.processIsInActivity(processInstanceId, activityId);
    }

    @Test(expected = AssertionError.class)
    public void testProcessIsInActivity_ProcessInstanceNotActive() throws Exception {
        when(processInstanceQueryMock.singleResult()).thenReturn(null);
        classUnderTest.processIsActive(processInstanceId);
    }

}
