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
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.toxos.activiti.assertion.ProcessAssert;

/**
 * Example test for MyProcess intended to generate a failure to illustrate
 * ProcessAssert's failure descriptions in the IDE.
 * 
 */
public class MyProcessFailureTest {

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule("example/activiti.cfg.xml");

	@Test
	@Deployment(resources = "example/MyProcess.bpmn")
	@Ignore
	public void testStartProcess() throws Exception {
		final ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess");
		ProcessAssert.assertProcessEnded(activitiRule, processInstance);
	}

}
