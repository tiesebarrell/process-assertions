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

import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

/**
 * Tests for log message availability in bundle.
 * 
 * @author Tiese Barrell
 * 
 */
public class LogMessageLocaleTest extends AbstractLogMessageTest {

	@Test
	public void testAllKeysDefined() throws Exception {
		final List<LogMessage> missingEntries = checkForMissingEntries();

		if (!missingEntries.isEmpty()) {
			fail(buildAssertionErrorMessage(missingEntries));
		}
	}

	private String buildAssertionErrorMessage(final Collection<LogMessage> missingEntries) {
		final StringBuilder builder = new StringBuilder();

		builder.append("There are entries missing in the LogMessages bundle for locale ")
				.append(Locale.getDefault().toString()).append(": \n");
		for (final LogMessage logMessage : missingEntries) {
			builder.append(logMessage.getBundleKey()).append("\n");
		}

		return builder.toString();
	}

}
