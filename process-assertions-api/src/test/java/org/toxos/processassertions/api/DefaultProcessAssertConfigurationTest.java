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
