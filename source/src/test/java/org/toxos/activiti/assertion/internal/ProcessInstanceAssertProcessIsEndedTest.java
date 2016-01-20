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

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link ProcessInstanceAssert#processIsEnded(String)}.
 * 
 * @author Tiese Barrell
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class ProcessInstanceAssertProcessIsEndedTest extends ProcessInstanceAssertTestBase {

    @Before
    public void beforeTest() throws Exception {
        when(processInstanceQueryMock.singleResult()).thenReturn(null);
        when(historicProcessInstanceMock.getEndTime()).thenReturn(new Date());
    }

    @Test
    public void testProcessIsEnded() throws Exception {
        classUnderTest.processIsEnded(processInstanceId);
    }

    @Test(expected = AssertionError.class)
    public void testProcessIsEnded_RuntimeProcessInstance() throws Exception {
        when(processInstanceQueryMock.singleResult()).thenReturn(processInstanceMock);
        classUnderTest.processIsEnded(processInstanceId);
    }

    @Test(expected = AssertionError.class)
    public void testProcessIsEnded_HistoricProcessInstanceNull() throws Exception {
        when(historicProcessInstanceQueryMock.singleResult()).thenReturn(null);
        classUnderTest.processIsEnded(processInstanceId);
    }

    @Test(expected = AssertionError.class)
    public void testProcessIsEnded_HistoricProcessInstanceNotEnded() throws Exception {
        when(historicProcessInstanceMock.getEndTime()).thenReturn(null);
        classUnderTest.processIsEnded(processInstanceId);
    }

}
