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

import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricFormProperty;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.junit.Before;
import org.mockito.Mock;

/**
 * Base class for {@link HistoricVariableInstanceAssert} tests.
 * 
 * @author Tiese Barrell
 * 
 */
public class HistoricVariableInstanceAssertTestBase extends AssertableTestBase {

    protected HistoricVariableInstanceAssertable classUnderTest;

    protected List<HistoricDetail> historicDetails;

    @Mock
    protected HistoricVariableUpdate historicVariableUpdateMock1;

    @Mock
    protected HistoricVariableUpdate historicVariableUpdateMock2;

    @Mock
    protected HistoricVariableUpdate historicVariableUpdateMock3;

    @Mock
    protected HistoricVariableUpdate historicVariableUpdateMock4;

    @Mock
    protected HistoricVariableUpdate historicVariableUpdateMock5;

    @Mock
    protected HistoricVariableUpdate historicVariableUpdateMock6;

    @Mock
    protected HistoricFormProperty historicFormPropertyMock;

    @Before
    public void beforeTaskInstanceAssertTest() throws Exception {
        classUnderTest = new HistoricVariableInstanceAssert(processAssertConfigurationMock);

        historicDetails = new ArrayList<>(6);

        when(historicDetailQueryMock.list()).thenReturn(historicDetails);

        historicDetails.add(historicVariableUpdateMock6);
        historicDetails.add(historicVariableUpdateMock5);
        historicDetails.add(historicVariableUpdateMock4);
        historicDetails.add(null);// Add a null value for test purposes
        historicDetails.add(historicVariableUpdateMock3);
        historicDetails.add(historicVariableUpdateMock2);
        historicDetails.add(historicFormPropertyMock);// Add a different type for test purposes
        historicDetails.add(historicVariableUpdateMock1);
    }
}
