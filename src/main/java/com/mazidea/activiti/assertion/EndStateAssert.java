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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;

/**
 * Provides assertions for process instances.
 */
public final class EndStateAssert {

  private EndStateAssert() {
    super();
  }

  private static boolean processEndedAndInExclusiveEndState(final ActivitiRule rule, final String processInstanceId,
      final String endStateKey) {

    boolean result = false;

    ProcessInstanceAssert.processIsEnded(rule, processInstanceId);

    try {
      // Assert there is no running process instance.
      final ProcessInstance processInstance = rule.getRuntimeService().createProcessInstanceQuery()
          .processInstanceId(processInstanceId).singleResult();

      Assert.assertNull(processInstance);

      // Assert there is a historic process instance and it is ended
      final HistoricProcessInstance historicProcessInstance = rule.getHistoryService()
          .createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

      Assert.assertNotNull(historicProcessInstance);

      Assert.assertNotNull(historicProcessInstance.getEndTime());

      // Assert in the provided endstate
      final HistoricActivityInstance historicActivityInstance = rule.getHistoryService()
          .createHistoricActivityInstanceQuery().activityType("endEvent").singleResult();

      Assert.assertEquals(endStateKey, historicActivityInstance.getActivityId());

      result = true;
    } catch (final AssertionError ae) {
      result = false;
    }

    return result;
  }

  private static boolean processEndedAndInEndStates(final ActivitiRule rule, final String processInstanceId,
      final String... endStateKeys) {
    boolean result = false;

    ProcessInstanceAssert.processIsEnded(rule, processInstanceId);

    try {
      // Assert there is no running process instance.
      final ProcessInstance processInstance = rule.getRuntimeService().createProcessInstanceQuery()
          .processInstanceId(processInstanceId).singleResult();

      Assert.assertNull(processInstance);

      // Assert there is a historic process instance and it is ended
      final HistoricProcessInstance historicProcessInstance = rule.getHistoryService()
          .createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

      Assert.assertNotNull(historicProcessInstance);

      Assert.assertNotNull(historicProcessInstance.getEndTime());

      // Assert in the provided endstates
      final List<HistoricActivityInstance> historicActivityInstances = rule.getHistoryService()
          .createHistoricActivityInstanceQuery().activityType("endEvent").list();

      Assert.assertEquals(endStateKeys.length, historicActivityInstances.size());

      final List<String> historicEndStateKeys = new ArrayList<String>();
      for (final HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
        historicEndStateKeys.add(historicActivityInstance.getActivityId());
      }

      Assert.assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(endStateKeys), historicEndStateKeys));

      result = true;
    } catch (final AssertionError ae) {
      result = false;
    }

    return result;
  }
}
