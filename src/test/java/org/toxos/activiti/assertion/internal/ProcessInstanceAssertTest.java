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

import org.activiti.engine.EngineServices;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.junit.Before;
import org.mockito.Mock;
import org.toxos.activiti.assertion.ProcessAssertConfiguration;

/**
 * Base class for {@link ProcessInstanceAssert} tests.
 * 
 * @author Tiese Barrell
 * 
 */
public class ProcessInstanceAssertTest {

    protected ProcessInstanceAssertable classUnderTest;

    @Mock
    protected ProcessAssertConfiguration processAssertConfigurationMock;

    @Mock
    protected EngineServices engineServicesMock;

    @Mock
    protected RuntimeService runtimeServiceMock;

    @Mock
    protected HistoryService historyServiceMock;

    @Mock
    protected ProcessInstanceQuery processInstanceQueryMock;

    @Mock
    protected HistoricProcessInstanceQuery historicProcessInstanceQueryMock;

    @Mock
    protected ProcessInstance processInstanceMock;

    @Mock
    protected HistoricProcessInstance historicProcessInstanceMock;

    protected final String processInstanceId = "123";

    @Before
    public void before() throws Exception {
        classUnderTest = new ProcessInstanceAssert(processAssertConfigurationMock);

        when(processAssertConfigurationMock.getEngineServices()).thenReturn(engineServicesMock);
        when(engineServicesMock.getRuntimeService()).thenReturn(runtimeServiceMock);
        when(engineServicesMock.getHistoryService()).thenReturn(historyServiceMock);

        when(runtimeServiceMock.createProcessInstanceQuery()).thenReturn(processInstanceQueryMock);
        when(processInstanceQueryMock.processInstanceId(processInstanceId)).thenReturn(processInstanceQueryMock);

        when(historyServiceMock.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQueryMock);
        when(historicProcessInstanceQueryMock.processInstanceId(processInstanceId)).thenReturn(historicProcessInstanceQueryMock);
        when(historicProcessInstanceQueryMock.singleResult()).thenReturn(historicProcessInstanceMock);
    }

}
