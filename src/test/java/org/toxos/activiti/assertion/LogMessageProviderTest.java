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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.toxos.activiti.assertion.LogMessageProvider;

;

/**
 * Tests for {@link LogMessageProvider}..
 * 
 * @author Tiese Barrell
 * 
 */
public class LogMessageProviderTest {

	private Locale originalLocale;

	private LogMessageProvider classUnderTest;

	private static final String LOG_MESSAGES_TEST_BASENAME = "messages.LogMessagesTest";

	@Before
	public void setup() {
		originalLocale = Locale.getDefault();
	}

	@After
	public void tearDown() {
		Locale.setDefault(originalLocale);
	}

	@Test
	public void testMutableLogMessageProviderUseDefaultLocale() {
		Locale.setDefault(new Locale("x1", "y1"));
		classUnderTest = new LogMessageProvider(LOG_MESSAGES_TEST_BASENAME);
		Assert.assertEquals("Message 1 (x1, y1)", classUnderTest.getMessageByKey("key1"));

		Locale.setDefault(new Locale("x2", "y2"));
		classUnderTest = new LogMessageProvider(LOG_MESSAGES_TEST_BASENAME);
		Assert.assertEquals("Message 1 (x2, y2)", classUnderTest.getMessageByKey("key1"));
	}

	@Test
	public void testMutableLogMessageProviderSwitchesWhenRequired() {
		Locale.setDefault(new Locale("x1", "y1"));
		classUnderTest = new LogMessageProvider(LOG_MESSAGES_TEST_BASENAME);
		Assert.assertEquals("Message 1 (x1, y1)", classUnderTest.getMessageByKey("key1"));

		Locale.setDefault(new Locale("x2", "y2"));
		// don't create new LogMessageProvider
		Assert.assertEquals("Message 1 (x2, y2)", classUnderTest.getMessageByKey("key1"));
	}

	@Test
	public void testImmutableLogMessageProviderDoesntSwitchWhenLocaleSwitches() {
		Locale.setDefault(new Locale("x1", "y1"));
		classUnderTest = new LogMessageProvider(LOG_MESSAGES_TEST_BASENAME, new Locale("x1", "y1"));
		Assert.assertEquals("Message 1 (x1, y1)", classUnderTest.getMessageByKey("key1"));

		Locale.setDefault(new Locale("x2", "y2"));
		// don't create new LogMessageProvider and expect original message still
		// being resolved
		Assert.assertEquals("Message 1 (x1, y1)", classUnderTest.getMessageByKey("key1"));
	}

	@Test
	public void testGetMessageByKey() {
		classUnderTest = new LogMessageProvider(LOG_MESSAGES_TEST_BASENAME, new Locale("x1", "y1"));
		String message = classUnderTest.getMessageByKey("key1");
		Assert.assertNotNull(message);
		Assert.assertEquals("Message 1 (x1, y1)", message);

		message = classUnderTest.getMessageByKey("key2");
		Assert.assertNotNull(message);
		Assert.assertEquals("Message 2 (x1, y1)", message);

	}

	@Test(expected = MissingResourceException.class)
	public void testGetMessageByKeyForMissingKey() {
		classUnderTest = new LogMessageProvider(new Locale("x1", "y1"));
		classUnderTest.getMessageByKey("key4");
	}

	@Test
	public void testGetMessageByKeyWithFallback() {
		Locale.setDefault(new Locale("x1", "y1"));
		// Create LogMessageProvider with Locale different from default
		classUnderTest = new LogMessageProvider(LOG_MESSAGES_TEST_BASENAME, new Locale("x2", "y2"));
		String message = classUnderTest.getMessageByKey("key1");
		Assert.assertNotNull(message);
		Assert.assertEquals("Message 1 (x2, y2)", message);

		message = classUnderTest.getMessageByKey("key2");
		Assert.assertNotNull(message);
		Assert.assertEquals("Message 2 (x2, y2)", message);

		message = classUnderTest.getMessageByKey("key3");
		Assert.assertNotNull(message);
		Assert.assertEquals("Message 3 (default, default)", message);
	}

}
