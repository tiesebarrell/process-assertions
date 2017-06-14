package org.toxos.processassertions.flowable.integration.configuration; /*******************************************************************************
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

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.test.FlowableRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.toxos.processassertions.api.ProcessAssert;
import org.toxos.processassertions.flowable.ProcessAssertFlowableConfiguration;
import org.toxos.processassertions.integration.common.process.DiagramConstants;

/**
 * Abstract base class for test classes that test {@link ProcessAssert}.
 *
 * @author Tiese Barrell
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FlowableTestConfiguration.class)
public abstract class AbstractProcessAssertTest implements DiagramConstants {

    @Autowired
    @Rule
    public FlowableRule flowableRule;

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected TaskService taskService;

    @Before
    public void before() {
        ProcessAssert.setConfiguration(new ProcessAssertFlowableConfiguration(flowableRule));
    }

}
