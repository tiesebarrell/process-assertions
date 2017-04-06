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

    @Test public void defaultConstructorHasDefaultLocale() {
        assertThat(new TestProcessAssertConfiguration().getLocale(), is(Constants.DEFAULT_LOCALE));
    }

    @Test public void localeConstructorHasSpecifiedLocale() {
        assertThat(new TestProcessAssertConfiguration(new Locale("uk", "ua")).getLocale(), is(new Locale("uk", "ua")));
    }

    @Test public void settingLocaleChangesToSpecifiedLocale() {
        final DefaultProcessAssertConfiguration classUnderTest = new TestProcessAssertConfiguration();
        assertThat(classUnderTest.getLocale(), is(Constants.DEFAULT_LOCALE));
        classUnderTest.setLocale(new Locale("uk", "ua"));
        assertThat(classUnderTest.getLocale(), is(new Locale("uk", "ua")));
    }

}
