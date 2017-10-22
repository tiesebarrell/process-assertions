/*******************************************************************************
 * Copyright 2017 Tiese Barrell
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

import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricDetailQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.impl.history.HistoryLevel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.toxos.processassertions.api.LogMessage;
import org.toxos.processassertions.api.internal.ApiCallback;
import org.toxos.processassertions.api.internal.ConfigurationException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Test cases for {@link HistoricVariableInstanceAssert}.
 *
 * @author Tiese Barrell
 */
@RunWith(MockitoJUnitRunner.class)
public class HistoricVariableInstanceAssertTest {

    private HistoricVariableInstanceAssert classUnderTest;

    @Mock
    private ApiCallback apiCallbackMock;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ProcessAssertActivitiConfiguration configurationMock;

    @Mock
    private HistoricProcessInstance historicProcessInstanceMock1;

    @Mock
    private HistoricDetailQuery historicDetailQueryMock;

    @Mock
    private HistoricVariableUpdate historicVariableUpdateMock1;
    @Mock
    private HistoricVariableUpdate historicVariableUpdateMock2;
    @Mock
    private HistoricVariableUpdate historicVariableUpdateMock3;
    @Mock
    private HistoricVariableUpdate historicVariableUpdateMock4;

    private final String processInstanceId = "process-instance-123";

    private final String processVariableName1 = "process-variable-name-123";
    private final String processVariableName2 = "process-variable-name-234";
    private final String processVariableName3 = "process-variable-name-345";


    private final String processVariableValue1 = "process-variable-value-123";
    private final String processVariableValue2 = "process-variable-value-234";
    private final String processVariableValue3 = "process-variable-value-345";
    private final String processVariableValue4 = "process-variable-value-456";


    private List<HistoricDetail> historicDetails;

    @Before
    public void before() {
        historicDetails = new ArrayList<>();
        historicDetails.add(historicVariableUpdateMock3);
        historicDetails.add(historicVariableUpdateMock1);
        historicDetails.add(historicVariableUpdateMock2);
        historicDetails.add(historicVariableUpdateMock4);

        when(historicVariableUpdateMock1.getVariableName()).thenReturn(processVariableName1);
        when(historicVariableUpdateMock2.getVariableName()).thenReturn(processVariableName1);
        when(historicVariableUpdateMock3.getVariableName()).thenReturn(processVariableName2);
        when(historicVariableUpdateMock4.getVariableName()).thenReturn(processVariableName3);

        when(historicVariableUpdateMock1.getValue()).thenReturn(processVariableValue1);
        when(historicVariableUpdateMock2.getValue()).thenReturn(processVariableValue2);
        when(historicVariableUpdateMock3.getValue()).thenReturn(processVariableValue3);
        when(historicVariableUpdateMock4.getValue()).thenReturn(processVariableValue4);

        when(configurationMock
                .getProcessEngine()
                .getHistoryService()
                .createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult())
                .thenReturn(historicProcessInstanceMock1);

        when(configurationMock
                .getProcessEngine()
                .getHistoryService()
                .createHistoricDetailQuery())
                .thenReturn(historicDetailQueryMock);

        when(historicDetailQueryMock.variableUpdates()).thenReturn(historicDetailQueryMock);
        when(historicDetailQueryMock.processInstanceId(processInstanceId)).thenReturn(historicDetailQueryMock);
        when(historicDetailQueryMock.orderByVariableName()).thenReturn(historicDetailQueryMock);
        when(historicDetailQueryMock.asc()).thenReturn(historicDetailQueryMock);
        when(historicDetailQueryMock.orderByTime()).thenReturn(historicDetailQueryMock);
        when(historicDetailQueryMock.desc()).thenReturn(historicDetailQueryMock);
        when(historicDetailQueryMock.list()).thenReturn(historicDetails);

        when(configurationMock
                .getConfiguredHistoryLevel())
                .thenReturn(HistoryLevel.FULL);

        classUnderTest = new HistoricVariableInstanceAssert(apiCallbackMock, configurationMock);
    }

    @Test
    public void historicProcessVariableLatestValueEquals() {
        classUnderTest.historicProcessVariableLatestValueEquals(processInstanceId, processVariableName1, processVariableValue1);
        verify(apiCallbackMock, times(1)).trace(LogMessage.CONFIGURATION_1, HistoryLevel.FULL.name());
        verify(apiCallbackMock, times(1)).trace(LogMessage.PROCESS_13, processInstanceId);
        verify(apiCallbackMock, times(1)).trace(LogMessage.VARIABLE_1, processVariableName1, processInstanceId);
        verify(apiCallbackMock, times(1)).trace(LogMessage.VARIABLE_2, processVariableName1, processVariableValue1);
    }

    @Test
    public void historicProcessVariableLatestValueEqualsFailsForInsufficientHistoryLevel() {
        when(configurationMock
                .getConfiguredHistoryLevel())
                .thenReturn(HistoryLevel.AUDIT);
        classUnderTest.historicProcessVariableLatestValueEquals(processInstanceId, processVariableName1, processVariableValue1);
        verify(apiCallbackMock, times(1)).fail(ConfigurationException.class, LogMessage.ERROR_CONFIGURATION_1, HistoryLevel.FULL.name(), HistoryLevel.AUDIT.name());
    }

    @Test(expected = AssertionError.class)
    public void historicProcessVariableLatestValueEqualsFailsForNoHistoricProcessInstance() {
        when(configurationMock
                .getProcessEngine()
                .getHistoryService()
                .createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult())
                .thenReturn(null);
        classUnderTest.historicProcessVariableLatestValueEquals(processInstanceId, processVariableName1, processVariableValue1);
    }

    @Test(expected = AssertionError.class)
    public void historicProcessVariableLatestValueEqualsFailsForNoHistoricVariableUpdate() {
        historicDetails.clear();
        classUnderTest.historicProcessVariableLatestValueEquals(processInstanceId, processVariableName1, processVariableValue1);
    }

    @Test(expected = AssertionError.class)
    public void historicProcessVariableLatestValueEqualsFailsForUnequalValue() {
        when(historicVariableUpdateMock1.getValue()).thenReturn(processVariableValue2);
        classUnderTest.historicProcessVariableLatestValueEquals(processInstanceId, processVariableName1, processVariableValue1);
    }


}
