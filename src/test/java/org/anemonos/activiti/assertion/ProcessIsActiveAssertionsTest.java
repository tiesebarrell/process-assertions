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
package org.anemonos.activiti.assertion;

import static org.anemonos.activiti.assertion.ProcessAssert.assertProcessActive;
import static org.junit.Assert.fail;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * Tests for assertions that test a process is active.
 * 
 * @author Tiese Barrell
 * 
 */
@ContextConfiguration("classpath:application-context.xml")
public class ProcessIsActiveAssertionsTest extends AbstractProcessAssertTest {

  @Test
  @Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_USER_TASK_BPMN)
  public void testProcessActiveForObject() throws Exception {
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_USER_TASK);
    assertProcessActive(activitiRule, processInstance);
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_USER_TASK_BPMN)
  public void testProcessActiveFailureForNullObject() throws Exception {
    runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_USER_TASK);
    ProcessInstance nullInstance = null;
    assertProcessActive(activitiRule, nullInstance);
    fail("Expected exception for null process instance object");
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
  public void testProcessActiveFailureForObject() throws Exception {
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
    assertProcessActive(activitiRule, processInstance);
    fail("Expected exception for process instance object");
  }

  @Test
  @Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_USER_TASK_BPMN)
  public void testProcessActiveForId() throws Exception {
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_USER_TASK);
    assertProcessActive(activitiRule, processInstance.getId());
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_USER_TASK_BPMN)
  public void testProcessActiveFailureForNullId() throws Exception {
    runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_USER_TASK);
    String nullId = null;
    assertProcessActive(activitiRule, nullId);
    fail("Expected exception for null process instance id");
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
  public void testProcessActiveFailureForId() throws Exception {
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
    assertProcessActive(activitiRule, processInstance.getId());
    fail("Expected exception for process instance id");
  }

}
