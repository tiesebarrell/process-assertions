/*******************************************************************************
 * Copyright 2017 Tiese Barrell
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
package org.toxos.processassertions.api;

import org.toxos.processassertions.api.internal.AssertFactory;

/**
 * Created by tiesebarrell on 05/04/2017.
 */
public class TestProcessAssertConfiguration extends DefaultProcessAssertConfiguration {

    private AssertFactory assertFactory;

    public TestProcessAssertConfiguration(SupportedLocale locale) {
        super(locale);
    }

    public TestProcessAssertConfiguration() {
        super();
    }

    TestProcessAssertConfiguration(final AssertFactory assertFactory) {
        super();
        this.assertFactory = assertFactory;
    }

    @Override public AssertFactory getAssertFactory() {
        return assertFactory;
    }
}
