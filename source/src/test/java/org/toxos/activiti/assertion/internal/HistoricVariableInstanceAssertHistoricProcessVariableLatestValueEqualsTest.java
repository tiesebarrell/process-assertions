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

import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.impl.history.HistoryLevel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link HistoricVariableInstanceAssert#historicProcessVariableLatestValueEquals(String, String, Object)}.
 * 
 * @author Tiese Barrell
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class HistoricVariableInstanceAssertHistoricProcessVariableLatestValueEqualsTest extends HistoricVariableInstanceAssertTestBase {

    private final String targetProcessVariableName = "targetProcessVariableName";
    private final String otherProcessVariableName = "otherProcessVariableName";
    private final String yetAnotherProcessVariableName = "yetAnotherProcessVariableName";

    private final String variableValue1 = "variableValue1";
    private final int variableValue2 = 33;
    private final boolean variableValue3 = true;
    private final long variableValue4 = 42L;

    @Before
    public void beforeTest() throws Exception {
        when(historicVariableUpdateMock1.getVariableName()).thenReturn(targetProcessVariableName);
        when(historicVariableUpdateMock1.getValue()).thenReturn(variableValue1);

        when(historicVariableUpdateMock2.getVariableName()).thenReturn(targetProcessVariableName);
        when(historicVariableUpdateMock2.getValue()).thenReturn(variableValue2);

        when(historicVariableUpdateMock3.getVariableName()).thenReturn(targetProcessVariableName);
        when(historicVariableUpdateMock3.getValue()).thenReturn(variableValue4);

        when(historicVariableUpdateMock4.getVariableName()).thenReturn(otherProcessVariableName);
        when(historicVariableUpdateMock4.getValue()).thenReturn(variableValue2);

        when(historicVariableUpdateMock5.getVariableName()).thenReturn(otherProcessVariableName);
        when(historicVariableUpdateMock5.getValue()).thenReturn(variableValue3);

        when(historicVariableUpdateMock6.getVariableName()).thenReturn(yetAnotherProcessVariableName);
        when(historicVariableUpdateMock6.getValue()).thenReturn(variableValue1);
    }

    @Test
    public void testHistoricProcessVariableLatestValueEquals() throws Exception {
        classUnderTest.historicProcessVariableLatestValueEquals(processInstanceId, targetProcessVariableName, variableValue4);
        classUnderTest.historicProcessVariableLatestValueEquals(processInstanceId, otherProcessVariableName, variableValue3);
        classUnderTest.historicProcessVariableLatestValueEquals(processInstanceId, yetAnotherProcessVariableName, variableValue1);
    }

    @Test
    public void testHistoricProcessVariableLatestValueEquals_HistoricVariableUpdateValueNullEqualsNull() throws Exception {
        when(historicVariableUpdateMock3.getValue()).thenReturn(null);
        classUnderTest.historicProcessVariableLatestValueEquals(processInstanceId, targetProcessVariableName, null);
    }

    @Test(expected = AssertionError.class)
    public void testHistoricProcessVariableLatestValueEquals_HistoryLevelNotFull() throws Exception {
        when(processEngineConfigurationImplMock.getHistoryLevel()).thenReturn(HistoryLevel.AUDIT);
        classUnderTest.historicProcessVariableLatestValueEquals(processInstanceId, targetProcessVariableName, variableValue4);
    }

    @Test(expected = AssertionError.class)
    public void testHistoricProcessVariableLatestValueEquals_HistoricProcessInstanceNotFound() throws Exception {
        when(historicProcessInstanceQueryMock.singleResult()).thenReturn(null);
        classUnderTest.historicProcessVariableLatestValueEquals(processInstanceId, targetProcessVariableName, variableValue4);
    }

    @Test(expected = AssertionError.class)
    public void testHistoricProcessVariableLatestValueEquals_HistoricVariableUpdatesNull() throws Exception {
        when(historicDetailQueryMock.list()).thenReturn(null);
        classUnderTest.historicProcessVariableLatestValueEquals(processInstanceId, targetProcessVariableName, variableValue4);
    }

    @Test(expected = AssertionError.class)
    public void testHistoricProcessVariableLatestValueEquals_HistoricVariableUpdatesEmpty() throws Exception {
        when(historicDetailQueryMock.list()).thenReturn(new ArrayList<HistoricDetail>());
        classUnderTest.historicProcessVariableLatestValueEquals(processInstanceId, targetProcessVariableName, variableValue4);
    }

    @Test(expected = AssertionError.class)
    public void testHistoricProcessVariableLatestValueEquals_HistoricVariableUpdateValueNull() throws Exception {
        when(historicVariableUpdateMock3.getValue()).thenReturn(null);
        classUnderTest.historicProcessVariableLatestValueEquals(processInstanceId, targetProcessVariableName, variableValue4);
    }

    @Test(expected = AssertionError.class)
    public void testHistoricProcessVariableLatestValueEquals_HistoricVariableUpdateValueOtherValue() throws Exception {
        when(historicVariableUpdateMock3.getValue()).thenReturn(variableValue1);
        classUnderTest.historicProcessVariableLatestValueEquals(processInstanceId, targetProcessVariableName, variableValue4);
    }

}
