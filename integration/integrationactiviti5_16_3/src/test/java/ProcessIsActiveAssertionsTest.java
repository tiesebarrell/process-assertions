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

import static org.toxos.processassertions.api.ProcessAssert.assertProcessActive;
import static org.toxos.processassertions.api.ProcessAssert.assertTaskUncompleted;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.toxos.processassertions.activiti.ProcessAssertActivitiConfiguration;
import org.toxos.processassertions.api.ProcessAssert;
import org.toxos.processassertions.test.configuration.activiti.ActivitiTestConfiguration;

/**
 * Tests for assertions that test a process is active.
 * 
 * @author Tiese Barrell
 * 
 */
@ContextConfiguration(classes = {ActivitiTestConfiguration.class})
public class ProcessIsActiveAssertionsTest extends AbstractProcessAssertTest {

    /**
     * The BPMN process for the single usertask process.
     */
    private static final String BPMN_SINGLE_USER_TASK = "diagrams/TestProcessSingleUserTask.bpmn";

    /**
     * The process' key.
     */
    private static final String PROCESS_KEY = "testProcessSingleUserTask";

    @Before
    public void before() {
        super.before();
        ProcessAssert.setConfiguration(new ProcessAssertActivitiConfiguration(activitiRule));
    }

    @Test
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessActiveForObject() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_KEY);
        assertProcessActive(processInstance.getProcessInstanceId());
        final Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
        assertTaskUncompleted(task.getId());
    }

}
