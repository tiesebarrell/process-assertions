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
package org.toxos.activiti.assertion.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.toxos.activiti.assertion.ProcessIsActiveAssertionsTest;
import org.toxos.activiti.assertion.ProcessIsEndedAndInEndEventsAssertionsTest;
import org.toxos.activiti.assertion.ProcessIsEndedAndInExclusiveEndEventAssertionsTest;
import org.toxos.activiti.assertion.ProcessIsEndedAssertionsTest;
import org.toxos.activiti.assertion.TaskIsUncompletedByProcessInstanceAndTaskDefinitionKeyAssertionsTest;
import org.toxos.activiti.assertion.TaskIsUncompletedByTaskAssertionsTest;

/**
 * Test suite for process assertion tests.
 * 
 * @author Tiese Barrell
 */
@RunWith(Suite.class)
@SuiteClasses({ ProcessIsActiveAssertionsTest.class, ProcessIsEndedAssertionsTest.class, TaskIsUncompletedByTaskAssertionsTest.class,
        TaskIsUncompletedByProcessInstanceAndTaskDefinitionKeyAssertionsTest.class, ProcessIsEndedAndInExclusiveEndEventAssertionsTest.class,
        ProcessIsEndedAndInEndEventsAssertionsTest.class })
public class AssertionTests {

}
