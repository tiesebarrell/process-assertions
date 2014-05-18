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
import org.toxos.activiti.assertion.internal.AssertFactoryImplTest;
import org.toxos.activiti.assertion.internal.ProcessInstanceAssertProcessIsActiveTest;
import org.toxos.activiti.assertion.internal.ProcessInstanceAssertProcessIsEndedTest;
import org.toxos.activiti.assertion.internal.TaskInstanceAssertTaskIsUncompletedByDefinitionIdTest;
import org.toxos.activiti.assertion.internal.TaskInstanceAssertTaskIsUncompletedByIdTest;

/**
 * Test suite for internal tests.
 * 
 * @author Tiese Barrell
 */
@RunWith(Suite.class)
@SuiteClasses({ AssertFactoryImplTest.class, ProcessInstanceAssertProcessIsActiveTest.class, ProcessInstanceAssertProcessIsEndedTest.class,
        TaskInstanceAssertTaskIsUncompletedByIdTest.class, TaskInstanceAssertTaskIsUncompletedByDefinitionIdTest.class })
public class InternalTests {

}
