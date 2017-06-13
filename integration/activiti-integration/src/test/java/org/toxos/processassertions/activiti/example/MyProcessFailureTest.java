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
package org.toxos.processassertions.activiti.example;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.toxos.processassertions.activiti.ProcessAssertActivitiConfiguration;
import org.toxos.processassertions.activiti.integration.configuration.ActivitiTestConfiguration;
import org.toxos.processassertions.api.ProcessAssert;

/**
 * Example test for MyProcess intended to generate a failure to illustrate
 * ProcessAssert's failure descriptions in the IDE.
 * 
 * @author Tiese Barrell
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ActivitiTestConfiguration.class)
@Ignore
public class MyProcessFailureTest {

    @Autowired
    @Rule
    public ActivitiRule activitiRule;

    @Before
    public void before() {
        ProcessAssert.setConfiguration(new ProcessAssertActivitiConfiguration(activitiRule));
    }

    @Test
    @Deployment(resources = "example/MyProcess.bpmn")
    public void testMyProcess() throws Exception {
        final ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess");
        ProcessAssert.assertProcessEnded(processInstance.getProcessInstanceId());
    }

}
