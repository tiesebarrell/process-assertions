package org.toxos.processassertions.api;

import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test cases for {@link Constants}.
 *
 * @author Tiese Barrell
 */
public class ConstantsTest {

    @Test public void defaultLocaleIsUsEnglish() {
        assertThat(Constants.DEFAULT_LOCALE, is(new Locale("en", "us")));
    }

}
