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
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.test.ActivitiRule;

/**
 * Provides utilities for process assertions.
 */
final class AssertUtils {

	private AssertUtils() {
		super();
	}

	/**
	 * Determines whether the history level of the process engine is set to
	 * {@link HistoryLevel.FULL}.
	 * 
	 * @param rule
	 *            the {@link ActivitiRule} to access the process engine's
	 *            services
	 * @return true if the history level is set to full, false otherwise
	 */
	static boolean historyLevelIsFull(final ActivitiRule rule) {

		boolean result = false;

		final ProcessEngine engine = rule.getProcessEngine();

		if (engine instanceof ProcessEngineImpl) {
			final ProcessEngineImpl engineImpl = (ProcessEngineImpl) engine;
			result = HistoryLevel.FULL.equals(engineImpl.getProcessEngineConfiguration().getHistoryLevel());
		}

		return result;
	}

	/**
	 * Gets a list of {@link HistoricVariableUpdate}s in descending order of
	 * update time for the provided process instance id and the provided name
	 * for the process variable.
	 * 
	 * @param rule
	 *            the {@link ActivitiRule} to access the process engine's
	 *            services
	 * @param processInstanceId
	 *            the process instance's id to get the variables for
	 * @param processVariableName
	 *            the name of the process variable to get the updates for
	 * @return a list of variable updates, in descending order
	 */
	static List<HistoricVariableUpdate> getDescendingVariableUpdates(final ActivitiRule rule, final String processInstanceId,
			final String processVariableName) {

		final List<HistoricVariableUpdate> result = new ArrayList<HistoricVariableUpdate>();

		final List<HistoricDetail> historicDetails = rule.getHistoryService().createHistoricDetailQuery().variableUpdates()
				.processInstanceId(processInstanceId).orderByVariableName().asc().orderByTime().desc().list();

		boolean reachedTargetVariable = false;

		for (final HistoricDetail historicDetail : historicDetails) {

			if (historicDetail instanceof HistoricVariableUpdate) {
				final HistoricVariableUpdate historicVariableUpdate = (HistoricVariableUpdate) historicDetail;

				if (processVariableName.equals(historicVariableUpdate.getVariableName()) && !reachedTargetVariable) {
					reachedTargetVariable = true;
					result.add(historicVariableUpdate);
				} else if (processVariableName.equals(historicVariableUpdate.getVariableName())) {
					result.add(historicVariableUpdate);
				} else if (reachedTargetVariable) {
					break;
				}
			}
		}

		return result;
	}

}
