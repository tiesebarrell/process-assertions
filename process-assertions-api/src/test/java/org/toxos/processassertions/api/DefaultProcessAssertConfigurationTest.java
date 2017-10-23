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

import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.toxos.processassertions.api.internal.Assert.assertThat;

/**
 * Test cases for {@link DefaultProcessAssertConfiguration}.
 *
 * @author Tiese Barrell
 */
public class DefaultProcessAssertConfigurationTest {

    @Test
    public void defaultConstructorHasDefaultLocale() {
        assertThat(new TestProcessAssertConfiguration().getLocale(), is(SupportedLocale.DEFAULT.getLocale()));
    }

    @Test
    public void localeConstructorHasSpecifiedLocale() {
        assertThat(new TestProcessAssertConfiguration(SupportedLocale.DEFAULT).getLocale(), is(new Locale("en", "us")));
    }

    @Test
    public void settingLocaleChangesToSpecifiedLocale() {
        final DefaultProcessAssertConfiguration classUnderTest = new TestProcessAssertConfiguration();
        assertThat(classUnderTest.getLocale(), is(SupportedLocale.DEFAULT.getLocale()));
        classUnderTest.setLocale(SupportedLocale.DUTCH_NL);
        assertThat(classUnderTest.getLocale(), is(new Locale("nl", "NL")));
    }

}
