/*
 * Copyright (c) 2020 André Barranco
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.bandrefilipe.brewer.commons.lang;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author bandrefilipe
 * @since 2020-10-22
 */
class StringsTest {

    private String nonWordCharacters;
    private final TreeSet<String> nonWordCharactersSet = new TreeSet<>();

    private String wordCharacters;
    private final TreeSet<String> wordCharactersSet = new TreeSet<>();

    private String mixedCharacters;
    private final TreeSet<String> mixedCharactersSet = new TreeSet<>();

    private final String lowerCaseAlphabet = "abcdefghijklmnopqrstuvwxyz";
    private final String expectedUpperCaseAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final String lowerCaseSpecialCharacters = "áâàåãäæéêèëðíîìïóôòøõöúûùüçñý";
    private final String expectedUpperCaseSpecialCharacters = "ÁÂÀÅÃÄÆÉÊÈËÐÍÎÌÏÓÔÒØÕÖÚÛÙÜÇÑÝ";

    @BeforeEach
    void setup() {
        setupNonWordCharacters();
        setupWordCharacters();
        setupMixedCharacters();
    }

    @AfterEach
    void tearDown() {
        nonWordCharactersSet.clear();
        wordCharactersSet.clear();
        mixedCharactersSet.clear();
    }

    private void setupNonWordCharacters() {
        nonWordCharacters = "~!@#$%^&*()+`-={}|[]\\:\";'<>?,./áâàåãäæéêèëðíîìïóôòøõöúûùüçñýÁÂÀÅÃÄÆÉÊÈËÐÍÎÌÏÓÔÒØÕÖÚÛÙÜÇÑÝ";
        for (final var character : nonWordCharacters.toCharArray()) {
            nonWordCharactersSet.add(String.valueOf(character));
        }
    }

    private void setupWordCharacters() {
        wordCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";
        for (final var character : wordCharacters.toCharArray()) {
            wordCharactersSet.add(String.valueOf(character));
        }
    }

    private void setupMixedCharacters() {
        mixedCharactersSet.addAll(nonWordCharactersSet);
        mixedCharactersSet.addAll(wordCharactersSet);

        final StringBuilder builder = new StringBuilder();
        mixedCharactersSet.forEach(builder::append);
        mixedCharacters = builder.toString();
    }

    @Test
    void testEmptyString() {
        assertThat(Strings.EMPTY)
                .isEmpty();
    }

    @Test
    void replacesEverythingIfInputIsAllNonWordCharacters() {
        final var replacement = "";
        assertThat(Strings.replaceAllNonWordCharacters(nonWordCharacters, replacement))
                .isEmpty();
    }

    @Test
    void doNotReplaceAnythingIfInputIsAllValidWordCharacters() {
        final var replacement = "";
        assertThat(Strings.replaceAllNonWordCharacters(wordCharacters, replacement))
                .isEqualTo(wordCharacters);
    }

    @Test
    void replacesOnlyTheNonWordCharactersIfInputIsMixed() {
        final var replacement = "";
        final var result = Strings.replaceAllNonWordCharacters(mixedCharacters, replacement);
        for (final var character : result.split("")) {
            assertTrue(wordCharactersSet.contains(character));
            assertFalse(nonWordCharacters.contains(character));
        }
    }

    @Test
    void throwsExceptionWhenReplacingNonWordCharactersIfInputIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> Strings.replaceAllNonWordCharacters(null, "replacement")
        );
    }

    @Test
    void throwsExceptionWhenReplacingNonWordCharactersIfReplacementIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> Strings.replaceAllNonWordCharacters(nonWordCharacters, null)
        );
    }

    @Test
    void trimsToEmpty() {
        assertEquals("", Strings.trimToEmpty(null));
        assertEquals("", Strings.trimToEmpty(""));
        assertEquals("", Strings.trimToEmpty("     "));
        assertEquals("abc", Strings.trimToEmpty("abc"));
        assertEquals("abc", Strings.trimToEmpty("    abc    "));
    }

    @Test
    void trimsToNull() {
        assertNull(Strings.trimToNull(null));
        assertNull(Strings.trimToNull(""));
        assertNull(Strings.trimToNull("     "));
        assertEquals("abc", Strings.trimToEmpty("abc"));
        assertEquals("abc", Strings.trimToEmpty("    abc    "));
    }

    @Test
    void toRootUpperCaseReturnsNullIfArgumentIsNull() {
        assertNull(Strings.toRootUpperCase(null));
    }

    @Test
    void toRootUpperCaseConvertsAlphabet() {
        assertThat(Strings.toRootUpperCase(lowerCaseAlphabet))
                .isEqualTo(expectedUpperCaseAlphabet);
    }

    @Test
    void toRootUpperCaseConvertsSpecialCharacters() {
        assertThat(Strings.toRootUpperCase(lowerCaseSpecialCharacters))
                .isEqualTo(expectedUpperCaseSpecialCharacters);
    }
}
