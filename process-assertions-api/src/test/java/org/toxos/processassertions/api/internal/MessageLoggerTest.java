package org.toxos.processassertions.api.internal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.toxos.processassertions.api.SupportedLocale;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.toxos.processassertions.api.internal.Assert.assertThat;

/**
 * Test cases for {@link MessageLogger}.
 *
 * Created by tiesebarrell on 21/06/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MessageLoggerTest {

    private MessageLogger classUnderTest;

    private final String bundleBaseName = "org.toxos.processassertions.messages.TestMessages";

    private final Locale locale = SupportedLocale.DEFAULT.getLocale();

    private static final String MESSAGE_KEY = "key1";

    @Mock
    private Logger loggerMock;

    @Before
    public void before() {
        classUnderTest = new MessageLogger(bundleBaseName, locale);
    }

    @Test
    public void logGetMessageHasMessage() {
        assertThat(classUnderTest.getMessage(MESSAGE_KEY), is("Message 1 (en, US)"));
    }

    @Test
    public void logGetMessageHasMessageForOtherLocale() {
        classUnderTest = new MessageLogger(bundleBaseName, SupportedLocale.DUTCH_NL.getLocale());
        assertThat(classUnderTest.getMessage(MESSAGE_KEY), is("Message 1 (nl, NL)"));
    }

    @Test
    public void logInfo() {
        classUnderTest.logInfo(loggerMock, MESSAGE_KEY);
        verify(loggerMock, times(1)).info("Message 1 (en, US)");
    }

    @Test
    public void logDebug() {
        classUnderTest.logDebug(loggerMock, MESSAGE_KEY);
        verify(loggerMock, times(1)).debug("Message 1 (en, US)");
    }

    @Test
    public void logTrace() {
        classUnderTest.logTrace(loggerMock, MESSAGE_KEY);
        verify(loggerMock, times(1)).trace("Message 1 (en, US)");
    }

    @Test
    public void logError() {
        classUnderTest.logError(loggerMock, MESSAGE_KEY);
        verify(loggerMock, times(1)).error("Message 1 (en, US)");
    }

    @Test
    public void logInfoSubtitutesParameters() {
        classUnderTest.logInfo(loggerMock, "key4", "param1", "param2");
        verify(loggerMock, times(1)).info("Message 4 (param1, param2)");
    }

}
