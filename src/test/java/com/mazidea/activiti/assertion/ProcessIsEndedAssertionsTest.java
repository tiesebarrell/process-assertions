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

import static com.mazidea.activiti.assertion.ProcessAssert.assertProcessEnded;
import static org.junit.Assert.fail;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * Tests for assertions that test a process is ended.
 * 
 * @author Tiese Barrell
 * 
 */
@ContextConfiguration("classpath:application-context.xml")
public class ProcessIsEndedAssertionsTest extends AbstractProcessAssertTest {

  @Test
  @Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
  public void testProcessEndedForObject() throws Exception {
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
    assertProcessEnded(activitiRule, processInstance);
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
  public void testProcessEndedFailureForNullObject() throws Exception {
    runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
    ProcessInstance nullInstance = null;
    assertProcessEnded(activitiRule, nullInstance);
    fail("Expected exception for null process instance object");
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_USER_TASK_BPMN)
  public void testProcessEndedFailureForObject() throws Exception {
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_USER_TASK);
    assertProcessEnded(activitiRule, processInstance);
    fail("Expected exception for process instance object");
  }

  @Test
  @Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
  public void testProcessEndedForId() throws Exception {
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
    assertProcessEnded(activitiRule, processInstance.getId());
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
  public void testProcessEndedFailureForNullId() throws Exception {
    runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
    String nullId = null;
    assertProcessEnded(activitiRule, nullId);
    fail("Expected exception for null process instance id");
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_USER_TASK_BPMN)
  public void testProcessEndedFailureForId() throws Exception {
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_USER_TASK);
    assertProcessEnded(activitiRule, processInstance.getId());
    fail("Expected exception for process instance id");
  }

}
