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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.theInstance;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.toxos.activiti.assertion.internal.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link IsEmptyCollection}.
 * 
 * @author Tiese Barrell
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class IsEmptyCollectionTest {

    private IsEmptyCollection classUnderTest;

    private Collection<String> collection;

    @Mock
    private Description descriptionMock;

    @Before
    public void before() throws Exception {
        classUnderTest = new IsEmptyCollection();
        collection = new ArrayList<>(1);
        collection.add("TestString");
    }

    @Test
    public void testMatchesSafely() {
        assertThat(classUnderTest.matchesSafely(new ArrayList<>()), is(true));
        assertThat(classUnderTest.matchesSafely(new HashSet<>()), is(true));
        assertThat(classUnderTest.matchesSafely(collection), is(false));
    }

    @Test
    public void testDescribeTo() {
        classUnderTest.describeTo(descriptionMock);
        verify(descriptionMock, times(1)).appendText("empty");
    }

    @Test
    public void testEmpty() {
        assertThat(IsEmptyCollection.empty(), is(notNullValue()));
        assertThat(IsEmptyCollection.empty(), instanceOf(IsEmptyCollection.class));
        assertThat(IsEmptyCollection.empty(), not(theInstance(IsEmptyCollection.empty())));
    }
}
