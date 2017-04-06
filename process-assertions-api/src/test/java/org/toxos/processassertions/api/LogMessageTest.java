package org.toxos.processassertions.api;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.toxos.processassertions.api.internal.Assert.assertThat;

/**
 * Test cases for {@link LogMessage}.
 *
 * @author Tiese Barrell
 */
public class LogMessageTest {

    @Test public void bundleKeyNameIs() {
        for (LogMessage logMessage : LogMessage.values()) {
            assertThat(logMessage.getBundleKey(), is(logMessage.name().toLowerCase().replaceAll("_", ".")));
        }
    }

}
