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

import static org.toxos.activiti.assertion.ProcessAssert.assertProcessVariableLatestValueEquals;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.toxos.activiti.assertion.process.SingleUserTaskProcessConstant;
import org.toxos.activiti.assertion.process.StraightThroughProcessConstant;

/**
 * Tests for assertions that test a process variable's latest instance has the expected value.
 * 
 * @author Tiese Barrell
 * 
 */
@ContextConfiguration("classpath:application-context.xml")
public class ProcessVariableLatestValueEqualsAssertionsTest extends AbstractProcessAssertTest {

    private final String processVariableName1 = "processVariableName1";
    private final String processVariableName2 = "processVariableName2";
    private final String processVariableName3 = "processVariableName3";

    private final String expectedValue1 = "expectedValue1";
    private final String expectedValue2 = "expectedValue2";

    @Test
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessVariableLatestValueEqualsForProcessInstanceObject() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue(),
                createStartVariables());
        assertProcessVariableLatestValueEquals(processInstance, processVariableName1, expectedValue1);
        assertProcessVariableLatestValueEquals(processInstance, processVariableName2, expectedValue2);

        runtimeService.setVariable(processInstance.getProcessInstanceId(), processVariableName1, expectedValue2);

        assertProcessVariableLatestValueEquals(processInstance, processVariableName1, expectedValue2);
        assertProcessVariableLatestValueEquals(processInstance, processVariableName2, expectedValue2);

        runtimeService.setVariable(processInstance.getProcessInstanceId(), processVariableName2, expectedValue1);

        assertProcessVariableLatestValueEquals(processInstance, processVariableName1, expectedValue2);
        assertProcessVariableLatestValueEquals(processInstance, processVariableName2, expectedValue1);

        runtimeService.setVariable(processInstance.getProcessInstanceId(), processVariableName1, null);

        assertProcessVariableLatestValueEquals(processInstance, processVariableName1, null);
        assertProcessVariableLatestValueEquals(processInstance, processVariableName2, expectedValue1);
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessVariableLatestValueEqualsFailureForWrongValueAndProcessInstanceObject() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue(),
                createStartVariables());
        assertProcessVariableLatestValueEquals(processInstance, processVariableName1, expectedValue2);
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessVariableLatestValueEqualsFailureForUnusedVariableAndProcessInstanceObject() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue(),
                createStartVariables());
        assertProcessVariableLatestValueEquals(processInstance, processVariableName3, expectedValue2);
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessVariableLatestValueEqualsFailureForNullExpectedValueAndProcessInstanceObject() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        assertProcessVariableLatestValueEquals(processInstance, processVariableName1, null);
    }

    @Test(expected = NullPointerException.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessVariableLatestValueEqualsFailureForNullProcessInstanceObject() throws Exception {
        runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        final ProcessInstance nullProcessInstance = null;
        assertProcessVariableLatestValueEquals(nullProcessInstance, processVariableName1, expectedValue1);
    }

    @Test(expected = NullPointerException.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessVariableLatestValueEqualsFailureForNullProcessVariableNameAndProcessInstanceObject() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        final String nullProcessVariableName = null;
        assertProcessVariableLatestValueEquals(processInstance, nullProcessVariableName, expectedValue1);
    }

    // processInstanceId tests

    @Test
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessVariableLatestValueEqualsForProcessInstanceId() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue(),
                createStartVariables());
        assertProcessVariableLatestValueEquals(processInstance.getProcessInstanceId(), processVariableName1, expectedValue1);
        assertProcessVariableLatestValueEquals(processInstance.getProcessInstanceId(), processVariableName2, expectedValue2);

        runtimeService.setVariable(processInstance.getProcessInstanceId(), processVariableName1, expectedValue2);

        assertProcessVariableLatestValueEquals(processInstance.getProcessInstanceId(), processVariableName1, expectedValue2);
        assertProcessVariableLatestValueEquals(processInstance.getProcessInstanceId(), processVariableName2, expectedValue2);

        runtimeService.setVariable(processInstance.getProcessInstanceId(), processVariableName2, expectedValue1);

        assertProcessVariableLatestValueEquals(processInstance.getProcessInstanceId(), processVariableName1, expectedValue2);
        assertProcessVariableLatestValueEquals(processInstance.getProcessInstanceId(), processVariableName2, expectedValue1);

        runtimeService.setVariable(processInstance.getProcessInstanceId(), processVariableName1, null);

        assertProcessVariableLatestValueEquals(processInstance.getProcessInstanceId(), processVariableName1, null);
        assertProcessVariableLatestValueEquals(processInstance.getProcessInstanceId(), processVariableName2, expectedValue1);
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessVariableLatestValueEqualsFailureForWrongValueAndProcessInstanceId() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue(),
                createStartVariables());
        assertProcessVariableLatestValueEquals(processInstance.getProcessInstanceId(), processVariableName1, expectedValue2);
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessVariableLatestValueEqualsFailureForUnusedVariableAndProcessInstanceId() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue(),
                createStartVariables());
        assertProcessVariableLatestValueEquals(processInstance.getProcessInstanceId(), processVariableName3, expectedValue2);
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessVariableLatestValueEqualsFailureForNullExpectedValueAndProcessInstanceId() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        assertProcessVariableLatestValueEquals(processInstance.getProcessInstanceId(), processVariableName1, null);
    }

    @Test(expected = NullPointerException.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessVariableLatestValueEqualsFailureForNullProcessInstanceId() throws Exception {
        runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        final String nullProcessInstanceId = null;
        assertProcessVariableLatestValueEquals(nullProcessInstanceId, processVariableName1, expectedValue1);
    }

    @Test(expected = NullPointerException.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessVariableLatestValueEqualsFailureForNullProcessVariableNameAndProcessInstanceId() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        final String nullProcessVariableName = null;
        assertProcessVariableLatestValueEquals(processInstance.getProcessInstanceId(), nullProcessVariableName, expectedValue1);
    }

    private Map<String, Object> createStartVariables() {
        final Map<String, Object> variables = new HashMap<String, Object>(2);
        variables.put(processVariableName1, expectedValue1);
        variables.put(processVariableName2, expectedValue2);
        return variables;
    }

}
