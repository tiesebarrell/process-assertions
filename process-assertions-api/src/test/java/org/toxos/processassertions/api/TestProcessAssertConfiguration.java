package org.toxos.processassertions.api;

import org.toxos.processassertions.api.internal.AssertFactory;

import java.util.Locale;

/**
 * Created by tiesebarrell on 05/04/2017.
 */
public class TestProcessAssertConfiguration extends DefaultProcessAssertConfiguration {

    private AssertFactory assertFactory;

    public TestProcessAssertConfiguration(Locale locale) {
        super(locale);
    }

    public TestProcessAssertConfiguration() {
        super();
    }

    TestProcessAssertConfiguration(final AssertFactory assertFactory) {
        super();
        this.assertFactory = assertFactory;
    }

    @Override public AssertFactory getAssertFactory() {
        return assertFactory;
    }
}
