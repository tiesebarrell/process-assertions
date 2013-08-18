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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.test.ActivitiRule;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Assert;

/**
 * Provides assertions for end events.
 */
final class EndEventAssert extends AbstractProcessAssert {

	static void processEndedAndInExclusiveEndEvent(final ActivitiRule rule, final String processInstanceId,
			final String endEventId) {

		// Assert the process instance is ended
		ProcessInstanceAssert.processIsEnded(rule, processInstanceId);

		// Assert that there is exactly one historic activity instance for end
		// events and that it has the correct id
		trace(LogMessage.PROCESS_10, processInstanceId, endEventId);
		final List<HistoricActivityInstance> historicActivityInstances = rule.getHistoryService()
				.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType("endEvent")
				.finished().list();

		Assert.assertEquals(1, historicActivityInstances.size());
		Assert.assertEquals(endEventId, historicActivityInstances.get(0).getActivityId());
	}

	static void processEndedAndInEndEvents(final ActivitiRule rule, final String processInstanceId,
			final String... endEventIds) {

		// Assert the process instance is ended
		ProcessInstanceAssert.processIsEnded(rule, processInstanceId);

		// Assert that there are the exact amount of historic activity instances
		// for end events and that ids match exactly
		trace(LogMessage.PROCESS_12, endEventIds.length, processInstanceId, ArrayUtils.toString(endEventIds));

		final List<HistoricActivityInstance> historicActivityInstances = rule.getHistoryService()
				.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType("endEvent")
				.finished().list();

		final List<String> historicEndEventIds = new ArrayList<String>();
		for (final HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
			historicEndEventIds.add(historicActivityInstance.getActivityId());
		}

		// Catch any exceptions so a detailed log message can be shown. The
		// context of expected and actual end event ids is only available here.
		try {
			Assert.assertEquals(endEventIds.length, historicActivityInstances.size());

			Assert.assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(endEventIds), historicEndEventIds));
		} catch (final AssertionError ae) {
			debug(LogMessage.ERROR_PROCESS_5, endEventIds.length, historicEndEventIds.size(),
					ArrayUtils.toString(endEventIds), ArrayUtils.toString(historicEndEventIds.toArray()));
			// rethrow to ensure handled properly at higher level
			throw ae;
		}

	}
}
