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

/**
 * Base class for {@link EndEventAssert} tests.
 * 
 * @author Tiese Barrell
 * 
 */
public class EndEventAssertTestBase extends AssertableTestBase {

    protected EndEventAssertable classUnderTest;

    @Before
    public void beforeTaskInstanceAssertTest() throws Exception {
        classUnderTest = new EndEventAssert(processAssertConfigurationMock);
        when(historicProcessInstanceMock.getEndTime()).thenReturn(new Date());
    }
}
