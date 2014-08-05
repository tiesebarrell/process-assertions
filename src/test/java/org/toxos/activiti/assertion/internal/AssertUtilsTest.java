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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.toxos.activiti.assertion.internal.Assert.assertThat;

import org.junit.Test;

/**
 * Tests for {@link AssertUtils}.
 * 
 * @author Tiese Barrell
 * 
 */
public class AssertUtilsTest {

    @Test
    public void testStringIsBlank_EmptyString() {
        assertThat(AssertUtils.stringIsBlank(""), is(true));
    }

    @Test
    public void testStringIsBlank_NullString() {
        assertThat(AssertUtils.stringIsBlank(null), is(true));
    }

    @Test
    public void testStringIsBlank_SpaceString() {
        assertThat(AssertUtils.stringIsBlank(" "), is(true));
    }

    @Test
    public void testStringIsBlank_MultipleWhitespaceString() {
        assertThat(AssertUtils.stringIsBlank("   "), is(true));
    }

    @Test
    public void testStringIsBlank_SingleCharacter() {
        assertThat(AssertUtils.stringIsBlank("a"), is(false));
    }

    @Test
    public void testStringIsBlank_NumberCharacters() {
        assertThat(AssertUtils.stringIsBlank("33"), is(false));
    }

    @Test
    public void testStringIsBlank_SingleCharacterInWhitespace() {
        assertThat(AssertUtils.stringIsBlank("   a   "), is(false));
    }

    @Test
    public void testStringIsBlank_MultipleCharacters() {
        assertThat(AssertUtils.stringIsBlank("lorum33"), is(false));
    }

    @Test
    public void testStringIsBlank_MultipleCharactersWithWhitespace() {
        assertThat(AssertUtils.stringIsBlank("lorum ipsum"), is(false));
    }

    @Test
    public void testArrayToString_SingleString() {
        assertThat(AssertUtils.arrayToString(new String[] { "aaa" }), is("{aaa}"));
    }

    @Test
    public void testArrayToString_MultipleStrings() {
        assertThat(AssertUtils.arrayToString(new String[] { "aaa", "bbb" }), is("{aaa, bbb}"));
    }

    @Test
    public void testArrayToString_SingleObject() {
        assertThat(AssertUtils.arrayToString(new TestObject[] { new TestObject("aaa") }), is("{TestObject: aaa}"));
    }

    @Test
    public void testArrayToString_MultipleObjects() {
        assertThat(AssertUtils.arrayToString(new TestObject[] { new TestObject("aaa"), new TestObject("bbb") }), is("{TestObject: aaa, TestObject: bbb}"));
    }

    @Test
    public void testArrayToString_SingleInteger() {
        assertThat(AssertUtils.arrayToString(new Integer[] { 33 }), is("{33}"));
    }

    @Test
    public void testArrayToString_MultipleIntegers() {
        assertThat(AssertUtils.arrayToString(new Integer[] { 33, 42 }), is("{33, 42}"));
    }

    @Test
    public void testArrayToString_MixedTypes() {
        assertThat(AssertUtils.arrayToString(new String[] { "aaa", "bbb" }), is("{aaa, bbb}"));
    }

    @Test
    public void testArrayToString_MixedTypesAndEmptyElements() {
        assertThat(AssertUtils.arrayToString(new Object[] { "aaa", "bbb", "", " ", null, 33, null, 42, new TestObject("aaa"), " ", new TestObject("33") }),
                is("{aaa, bbb, ,  , <null>, 33, <null>, 42, TestObject: aaa,  , TestObject: 33}"));
    }

    @Test
    public void testArrayToString_NullArray() {
        assertThat(AssertUtils.arrayToString(null), is("{}"));
    }

    @Test
    public void testArrayToString_NullInArray() {
        assertThat(AssertUtils.arrayToString(new Object[] { null }), is("{<null>}"));
    }

    @Test
    public void testArrayToString_EmptyArray() {
        assertThat(AssertUtils.arrayToString(new Object[] {}), is("{}"));
    }

    @Test
    public void testReplaceStringInString() {
        assertThat(AssertUtils.replaceStringInString("aaa", "a", "b"), is("bbb"));
        assertThat(AssertUtils.replaceStringInString("aaa", "b", "a"), is("aaa"));
        assertThat(AssertUtils.replaceStringInString("aba", "a", "b"), is("bbb"));
        assertThat(AssertUtils.replaceStringInString("aba", "b", "a"), is("aaa"));
        assertThat(AssertUtils.replaceStringInString("aaa bbb aba", "b", "a"), is("aaa aaa aaa"));
    }

    @Test
    public void testReplaceStringInString_NullText() {
        assertThat(AssertUtils.replaceStringInString(null, "a", "b"), is(nullValue()));
    }

    @Test
    public void testReplaceStringInString_EmptyText() {
        assertThat(AssertUtils.replaceStringInString("", "a", "b"), is(""));
    }

    @Test
    public void testReplaceStringInString_BlankText() {
        assertThat(AssertUtils.replaceStringInString(" ", "a", "b"), is(" "));
    }

    @Test
    public void testReplaceStringInString_NullSearchString() {
        assertThat(AssertUtils.replaceStringInString("aaa", null, "b"), is("aaa"));
    }

    @Test
    public void testReplaceStringInString_EmptySearchString() {
        assertThat(AssertUtils.replaceStringInString("aaa", "", "b"), is("aaa"));
    }

    @Test
    public void testReplaceStringInString_BlankSearchString() {
        assertThat(AssertUtils.replaceStringInString("aaa", " ", "b"), is("aaa"));
    }

    @Test
    public void testReplaceStringInString_NullReplacement() {
        assertThat(AssertUtils.replaceStringInString("aaa", "a", null), is("aaa"));
    }

    private static final class TestObject {
        private final String value;

        public TestObject(String value) {
            super();
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "TestObject: " + value;
        }

    }
}
