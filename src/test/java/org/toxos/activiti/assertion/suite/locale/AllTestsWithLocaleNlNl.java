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
package org.toxos.activiti.assertion.suite.locale;

import java.util.Locale;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.toxos.activiti.assertion.DefaultProcessAssertConfiguration;
import org.toxos.activiti.assertion.ProcessAssert;

/**
 * Test suite for the nl-NL locale.
 */
@RunWith(Suite.class)
@SuiteClasses({ AllLocaleSpecificTests.class })
public class AllTestsWithLocaleNlNl {

    @BeforeClass
    public static void beforeClass() {
        ProcessAssert.setConfiguration(new DefaultProcessAssertConfiguration(new Locale("nl", "NL")));
    }

    @AfterClass
    public static void afterClass() {
        ProcessAssert.setConfiguration(new DefaultProcessAssertConfiguration());
    }

}
