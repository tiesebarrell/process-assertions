/*******************************************************************************
 * Copyright 2013 Tiese Barrell
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
package org.toxos.activiti.assertion.example;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.toxos.activiti.assertion.DefaultProcessAssertConfiguration;
import org.toxos.activiti.assertion.ProcessAssert;

public class MyProcessTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("example/activiti.cfg.xml");

    @Before
    public void before() {
        // set the configuration with the ActivitiRule
        ProcessAssert.setConfiguration(new DefaultProcessAssertConfiguration(activitiRule));
    }

    @Test
    @Deployment(resources = "example/MyProcess.bpmn")
    public void testMyProcess() throws Exception {
        final ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess");

        // assert the process is still running
        ProcessAssert.assertProcessActive(processInstance);

        // assert the process is waiting for a UserTask to be completed
        ProcessAssert.assertTaskUncompleted(activitiRule, processInstance, "usertask1");

        // complete the task
        final Task userTask1 = activitiRule.getTaskService().createTaskQuery().processInstanceId(processInstance.getProcessInstanceId())
                .taskDefinitionKey("usertask1").singleResult();
        activitiRule.getTaskService().complete(userTask1.getId());

        // assert the process is now ended
        ProcessAssert.assertProcessEnded(processInstance);

    }
}
