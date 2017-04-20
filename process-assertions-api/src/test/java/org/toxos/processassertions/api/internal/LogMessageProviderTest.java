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
package org.toxos.processassertions.api.internal;

import org.junit.Test;
import org.toxos.processassertions.api.SupportedLocale;

import java.util.Locale;
import java.util.MissingResourceException;

import static org.hamcrest.CoreMatchers.is;
import static org.toxos.processassertions.api.internal.Assert.assertThat;

/**
 * Test cases for {@link LogMessageProvider}.
 * 
 * @author Tiese Barrell
 * 
 */
public class LogMessageProviderTest {

    public static final String TEST_MESSAGES = "org.toxos.processassertions.messages.TestMessages";
    private LogMessageProvider classUnderTest;

    @Test
    public void testLogMessageProviderUsesLocale() {
        classUnderTest = new LogMessageProvider(TEST_MESSAGES, SupportedLocale.ENGLISH_US.getLocale());
        assertThat(classUnderTest.getMessageByKey("key1"), is("Message 1 (en, US)"));

        classUnderTest = new LogMessageProvider(TEST_MESSAGES, SupportedLocale.DUTCH_NL.getLocale());
        assertThat(classUnderTest.getMessageByKey("key1"), is("Message 1 (nl, NL)"));
    }

    @Test
    public void testLogMessageProviderDoesntSwitchWhenSystemDefaultLocaleSwitches() {
        classUnderTest = new LogMessageProvider(TEST_MESSAGES, SupportedLocale.ENGLISH_US.getLocale());
        assertThat(classUnderTest.getMessageByKey("key1"), is("Message 1 (en, US)"));

        final Locale defaultLocale = Locale.getDefault();
        Locale.setDefault(new Locale("nl", "NL"));

        assertThat(classUnderTest.getMessageByKey("key1"), is("Message 1 (en, US)"));

        Locale.setDefault(defaultLocale);
    }

    @Test(expected = MissingResourceException.class)
    public void testGetMessageByKeyForMissingKey() {
        classUnderTest = new LogMessageProvider(TEST_MESSAGES, SupportedLocale.ENGLISH_US.getLocale());
        classUnderTest.getMessageByKey("key4");
    }

    @Test
    public void testGetMessageByKeyWithFallback() {
        classUnderTest = new LogMessageProvider(TEST_MESSAGES, SupportedLocale.DUTCH_NL.getLocale());
        assertThat(classUnderTest.getMessageByKey("key1"), is("Message 1 (nl, NL)"));
        assertThat(classUnderTest.getMessageByKey("key2"), is("Message 2 (nl, NL)"));
        assertThat(classUnderTest.getMessageByKey("key3"), is("Message 3 (en, US)"));
    }

}
