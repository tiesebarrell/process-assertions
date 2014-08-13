/*******************************************************************************
 * Copyright 2014 Tiese Barrell
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
package org.toxos.activiti.assertion.internal;

import java.util.Collection;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matcher that validates that the provided collection of Strings matches the expected collection of Strings.
 * 
 * @author Tiese Barrell
 */
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
