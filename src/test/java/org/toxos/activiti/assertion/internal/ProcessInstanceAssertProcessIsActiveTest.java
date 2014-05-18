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
 * Tests for {@link ProcessInstanceAssert#processIsActive(String)}.
 * 
 * @author Tiese Barrell
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class ProcessInstanceAssertProcessIsActiveTest extends ProcessInstanceAssertTestBase {

    @Before
    public void beforeTest() throws Exception {
        when(processInstanceQueryMock.singleResult()).thenReturn(processInstanceMock);
        when(processInstanceMock.isEnded()).thenReturn(false);
        when(processInstanceMock.isSuspended()).thenReturn(false);
        when(historicProcessInstanceMock.getEndTime()).thenReturn(null);
    }

    @Test
    public void testProcessIsActive() throws Exception {
        classUnderTest.processIsActive(processInstanceId);
    }

    @Test(expected = AssertionError.class)
    public void testProcessIsActive_NoProcessInstance() throws Exception {
        when(processInstanceQueryMock.singleResult()).thenReturn(null);
        classUnderTest.processIsActive(processInstanceId);
    }

    @Test(expected = AssertionError.class)
    public void testProcessIsActive_Ended() throws Exception {
        when(processInstanceMock.isEnded()).thenReturn(true);
        classUnderTest.processIsActive(processInstanceId);
    }

    @Test(expected = AssertionError.class)
    public void testProcessIsActive_Suspended() throws Exception {
        when(processInstanceMock.isSuspended()).thenReturn(true);
        classUnderTest.processIsActive(processInstanceId);
    }

    @Test(expected = AssertionError.class)
    public void testProcessIsActive_NoHistoricProcessInstance() throws Exception {
        when(historicProcessInstanceQueryMock.singleResult()).thenReturn(null);
        classUnderTest.processIsActive(processInstanceId);
    }

    @Test(expected = AssertionError.class)
    public void testProcessIsActive_HistoricProcessInstanceEnded() throws Exception {
        when(historicProcessInstanceMock.getEndTime()).thenReturn(new Date());
        classUnderTest.processIsActive(processInstanceId);
    }

}
