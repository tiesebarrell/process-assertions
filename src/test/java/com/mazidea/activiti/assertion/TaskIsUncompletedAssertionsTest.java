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
package com.mazidea.activiti.assertion;

import static com.mazidea.activiti.assertion.ProcessAssert.assertTaskUncompleted;

import java.util.Collections;

import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * Tests for assertions that test a task is uncompleted.
 * 
 * @author Tiese Barrell
 * 
 */
@ContextConfiguration("classpath:application-context.xml")
public class TaskIsUncompletedAssertionsTest extends AbstractProcessAssertTest {

  private static final String SINGLE_TASK = "singleTask";

  @Test
  @Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_TASK_BPMN)
  public void testTaskUncompletedForTaskObjectOnly() throws Exception {
    runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_TASK);
    Task task = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(SINGLE_TASK).singleResult();
    assertTaskUncompleted(activitiRule, task);
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_TASK_BPMN)
  public void testTaskUncompletedFailureForTaskObjectOnlyWhenCompleted() throws Exception {
    runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_TASK);
    Task task = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(SINGLE_TASK).singleResult();
    taskService.complete(task.getId(), Collections.<String, Object> emptyMap());
    assertTaskUncompleted(activitiRule, task);
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
  public void testTaskUncompletedFailureForTaskObjectOnly() throws Exception {
    runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
    Task task = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(SINGLE_TASK).singleResult();
    assertTaskUncompleted(activitiRule, task);
  }

  // @Test
  // @Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_TASK_BPMN)
  // public void testTaskUncompletedForObject() throws Exception {
  // ProcessInstance processInstance =
  // runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_TASK);
  // assertTaskUncompleted(activitiRule, processInstance, "singleTask");
  // }
  //
  // @Test
  // @Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_TASK_BPMN)
  // public void testProcessActiveForId() throws Exception {
  // ProcessInstance processInstance =
  // runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_TASK);
  // assertProcessActive(activitiRule, processInstance.getId());
  // }
  //
  // @Test(expected = AssertionError.class)
  // @Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
  // public void testProcessActiveFailureForObject() throws Exception {
  // ProcessInstance processInstance =
  // runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
  // assertProcessActive(activitiRule, processInstance);
  // }
  //
  // @Test(expected = AssertionError.class)
  // @Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
  // public void testProcessActiveFailureForId() throws Exception {
  // ProcessInstance processInstance =
  // runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
  // assertProcessActive(activitiRule, processInstance.getId());
  // }

}
