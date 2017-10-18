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
package org.toxos.processassertions.flowable;

import org.toxos.processassertions.api.internal.*;

/**
 * Implementation of {@link AssertFactory} for Flowable.
 *
 * @author Tiese Barrell
 */
public class AssertFactoryImpl implements AssertFactory {

    private final ProcessAssertFlowableConfiguration configuration;

    AssertFactoryImpl(final ProcessAssertFlowableConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public ProcessInstanceAssertable getProcessInstanceAssertable(final ApiCallback callback) {
        return new ProcessInstanceAssert(callback, configuration);
    }

    @Override
    public EndEventAssertable getEndEventAssertable(final ApiCallback callback) {
        return new EndEventAssert(callback, configuration);
    }

    @Override
    public TaskInstanceAssertable getTaskInstanceAssertable(final ApiCallback callback) {
        return new TaskInstanceAssert(callback, configuration);
    }

    @Override
    public HistoricVariableInstanceAssertable getHistoricVariableInstanceAssertable(final ApiCallback callback) {
        return new HistoricVariableInstanceAssert(callback, configuration);
    }

}
