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

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.toxos.activiti.assertion.internal.Assert.assertThat;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;

/**
 * Tests for {@link Assert}.
 * 
 * @author Tiese Barrell
 * 
 */
public class AssertTest {

    @Test
    public void testAssertThat() {
        assertThat("test", is("test"));
        assertThat("test", not("test2"));
        assertThat("test", is(not("test2")));

        assertThat(33L, is(33L));
        assertThat(33L, not(42L));

        final Object object1 = new Object();
        assertThat(object1, is(object1));
        assertThat(new Object(), not(new Object()));

        final Collection<String> collection1 = new HashSet<String>();
        collection1.add("test1");
        collection1.add("test2");
        assertThat(collection1, is(collection1));
        assertThat(collection1.size(), is(2));
        assertThat(collection1, hasItems("test1"));
        assertThat(collection1, hasItems("test2"));
    }

    @Test(expected = AssertionError.class)
    public void testAssertThat_NullActual() {
        final String nullString = null;
        assertThat("test", is(nullString));
    }

    @Test(expected = AssertionError.class)
    public void testAssertThat_NullExpected() {
        final String nullString = null;
        assertThat(nullString, is("test1"));
    }

    @Test(expected = AssertionError.class)
    public void testAssertThat_Unequal() {
        assertThat("test", is("test2"));
    }
}
