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
package org.anemonos.activiti.assertion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for log message availability in bundle.
 * 
 * @author Tiese Barrell
 * 
 */
public class LogMessageTest {

	// TODO split this into Locale-specific and generic tests so it is not run
	// for each Locale in the test suites.

	private Locale originalLocale;

	@Before
	public void setup() {
		originalLocale = Locale.getDefault();
	}

	@After
	public void tearDown() {
		Locale.setDefault(originalLocale);
	}

	// Locale specific test
	@Test
	public void testAllKeysDefined() throws Exception {
		final List<LogMessage> missingEntries = checkForMissingEntries();

		if (!missingEntries.isEmpty()) {
			fail(buildAssertionErrorMessage(missingEntries));
		}
	}

	// Generic test
	@Test
	public void testGetBundleKey() throws Exception {
		for (final LogMessage logMessage : LogMessage.values()) {
			final String expected = logMessage.name().replaceAll("_", ".").toLowerCase();
			assertEquals(expected, logMessage.getBundleKey());
		}
	}

	// Generic test
	@Test
	public void testInvalidLocaleHasMissingEntries() throws Exception {
		Locale.setDefault(new Locale("xx", "YY"));

		final List<LogMessage> missingEntries = checkForMissingEntries();

		if (missingEntries.isEmpty()) {
			fail("Expected invalid locale 'xx', 'YY' to have missing entries");
		}

		assertEquals(LogMessage.values().length, missingEntries.size());
	}

	private List<LogMessage> checkForMissingEntries() throws Exception {
		final List<LogMessage> missingEntries = new ArrayList<LogMessage>();

		final Properties properties = new Properties();

		final String resourceBundlePathForLocale = getResourceBundlePathForLocale();

		final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceBundlePathForLocale);
		properties.load(is);

		for (final LogMessage logMessage : LogMessage.values()) {

			String entry = properties.getProperty(logMessage.getBundleKey());
			if (StringUtils.isBlank(entry)) {
				missingEntries.add(logMessage);
			}
		}
		return missingEntries;
	}

	private String getResourceBundlePathForLocale() {
		final String localeSpecificPath = getLocaleSpecificPath();
		return StringUtils.replace(Constants.LOG_MESSAGES_BUNDLE_NAME, ".", "/") + localeSpecificPath + ".properties";
	}

	private String getLocaleSpecificPath() {
		final Locale fallBackLocale = new Locale("en", "US");
		String result = "";
		if (!Locale.getDefault().equals(fallBackLocale)) {
			result = "_" + Locale.getDefault().toString();
		}
		return result;
	}

	private String buildAssertionErrorMessage(final Collection<LogMessage> missingEntries) {
		final StringBuilder builder = new StringBuilder();

		builder.append("There are entries missing in the LogMessages bundle for locale ").append(Locale.getDefault().toString()).append(": \n");
		for (final LogMessage logMessage : missingEntries) {
			builder.append(logMessage.getBundleKey()).append("\n");
		}

		return builder.toString();
	}

}
