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
package org.toxos.activiti.assertion;

import java.util.Locale;
import java.util.MissingResourceException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link LogMessageProvider}.
 * 
 * @author Tiese Barrell
 * 
 */
public class LogMessageProviderTest {

    private LogMessageProvider classUnderTest;

    @Test
    public void testLogMessageProviderUsesLocale() {
        setupConfiguration(new Locale("vi", "VN"));
        classUnderTest = new LogMessageProvider();
        Assert.assertEquals("Message 1 (vi, vn)", classUnderTest.getMessageByKey("key1"));

        setupConfiguration(new Locale("uk", "UA"));
        classUnderTest = new LogMessageProvider();
        Assert.assertEquals("Message 1 (uk, ua)", classUnderTest.getMessageByKey("key1"));
    }

    @Test
    public void testLogMessageProviderDoesntSwitchWhenSystemDefaultLocaleSwitches() {
        setupConfiguration(new Locale("vi", "VN"));
        classUnderTest = new LogMessageProvider();
        Assert.assertEquals("Message 1 (vi, vn)", classUnderTest.getMessageByKey("key1"));

        Locale.setDefault(new Locale("uk", "UA"));
        Assert.assertEquals("Message 1 (vi, vn)", classUnderTest.getMessageByKey("key1"));
    }

    @Test
    public void testGetMessageByKey() {
        setupConfiguration(new Locale("vi", "VN"));
        classUnderTest = new LogMessageProvider();
        String message = classUnderTest.getMessageByKey("key1");
        Assert.assertNotNull(message);
        Assert.assertEquals("Message 1 (vi, vn)", message);

        message = classUnderTest.getMessageByKey("key2");
        Assert.assertNotNull(message);
        Assert.assertEquals("Message 2 (vi, vn)", message);

        message = classUnderTest.getMessageByKey("key3");
        Assert.assertNotNull(message);
        Assert.assertEquals("Message 3 (vi, vn)", message);

    }

    @Test(expected = MissingResourceException.class)
    public void testGetMessageByKeyForMissingKey() {
        setupConfiguration(new Locale("vi", "VN"));
        classUnderTest = new LogMessageProvider();
        classUnderTest.getMessageByKey("key4");
    }

    @Test
    public void testGetMessageByKeyWithFallback() {
        setupConfiguration(new Locale("uk", "UA"));
        classUnderTest = new LogMessageProvider();
        String message = classUnderTest.getMessageByKey("key1");
        Assert.assertNotNull(message);
        Assert.assertEquals("Message 1 (uk, ua)", message);

        message = classUnderTest.getMessageByKey("key2");
        Assert.assertNotNull(message);
        Assert.assertEquals("Message 2 (uk, ua)", message);

        // Request by a key not in the bundle, but in the default bundle
        message = classUnderTest.getMessageByKey(LogMessage.PROCESS_1.getBundleKey());
        Assert.assertNotNull(message);
    }

    private void setupConfiguration(final Locale locale) {
        final ProcessAssertConfiguration configuration = new DefaultProcessAssertConfiguration(locale);
        ProcessAssert.setConfiguration(configuration);
    }

}
