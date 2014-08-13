package org.toxos.activiti.assertion.internal;

import java.util.Collection;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class EqualCollectionMatcher extends TypeSafeDiagnosingMatcher<Collection<String>> {

    private final Collection<String> expectedValues;

    public EqualCollectionMatcher(final Collection<String> expectedValues) {
        this.expectedValues = expectedValues;
    }

    @Override
    public void describeTo(Description description) {
        // TODO Auto-generated method stub
    }

    @Override
    protected boolean matchesSafely(Collection<String> item, Description mismatchDescription) {
        return AssertUtils.isEqualCollection(expectedValues, item);
    }

}
