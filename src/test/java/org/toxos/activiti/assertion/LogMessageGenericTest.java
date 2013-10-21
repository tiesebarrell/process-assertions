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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Locale;

import org.junit.Test;

/**
 * Tests for generic elements of log messages.
 * 
 * @author Tiese Barrell
 * 
 */
public class LogMessageGenericTest extends AbstractLogMessageTest {

	@Test
	public void testGetBundleKey() throws Exception {
		for (final LogMessage logMessage : LogMessage.values()) {
			final String expected = logMessage.name().replaceAll("_", ".").toLowerCase();
			assertEquals(expected, logMessage.getBundleKey());
		}
	}

	@Test
	public void testInvalidLocaleHasMissingEntries() throws Exception {
		Locale.setDefault(new Locale("xx", "YY"));

		final List<LogMessage> missingEntries = checkForMissingEntries();

		if (missingEntries.isEmpty()) {
			fail("Expected invalid locale 'xx', 'YY' to have missing entries");
		}

		assertEquals(LogMessage.values().length, missingEntries.size());
	}

}
