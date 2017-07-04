package org.toxos.processassertions.api.internal;

import org.junit.Test;

/**
 * Test cases for {@link ApiCallbackImpl}.
 */
public class ApiCallbackImplTest {

    @Test(expected = NullPointerException.class)
    public void constructorFailureForNullConfiguration() {
        new ApiCallbackImpl(null);
    }

}