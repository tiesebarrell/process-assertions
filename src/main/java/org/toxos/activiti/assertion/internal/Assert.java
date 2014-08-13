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

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;

/**
 * Asserts conditions.
 * 
 * @author Tiese Barrell
 * 
 */
public final class Assert {

    private Assert() {
        super();
    }

    /**
     * Asserts that <code>actual</code> satisfies the condition(s) specified by <code>matcher</code>. Throws an
     * {@link AssertionError} if the matcher is not satisfied.
     * 
     * @param <T>
     *            the type the matcher handles
     * @param actual
     *            the value being compared
     * @param matcher
     *            the matcher to perform assertions with
     */
    public static final <T> void assertThat(T actual, Matcher<? super T> matcher) {
        MatcherAssert.assertThat(actual, matcher);
    }

    public static final Matcher<Collection<String>> equalList(Collection<String> expected) {
        return new EqualCollectionMatcher(expected);
    }

}
