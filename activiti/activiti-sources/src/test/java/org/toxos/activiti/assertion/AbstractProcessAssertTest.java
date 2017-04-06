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
package org.toxos.activiti.assertion;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.toxos.processassertions.activiti.ProcessAssertActivitiConfiguration;
import org.toxos.processassertions.api.ProcessAssert;

/**
 * Abstract base class for test classes that test {@link ProcessAssert}.
 *
 * @author Tiese Barrell
 */
@RunWith(SpringJUnit4ClassRunner.class) public abstract class AbstractProcessAssertTest implements TestConstants {

    @Autowired @Rule public ActivitiRule activitiRule;

    @Autowired protected RuntimeService runtimeService;

    @Autowired protected TaskService taskService;

    @Before public void before() {
        ProcessAssert.setConfiguration(new ProcessAssertActivitiConfiguration(activitiRule));
    }

}
