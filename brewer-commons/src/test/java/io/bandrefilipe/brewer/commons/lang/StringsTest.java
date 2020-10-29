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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author bandrefilipe
 * @since 2020-10-22
 */
class StringsTest {

    @Test
    void testEmptyString() {
        assertEquals("", Strings.EMPTY);
    }

    @Test
    void testReplaceAllNonWordCharacters() {
        // Arrange
        final var matchingInput = "~!@#$%^&*()+`-={}|[]\\:\";'<>?,./áâàåãäæéêèëðíîìïóôòøõöúûùüçñýÁÂÀÅÃÄÆÉÊÈËÐÍÎÌÏÓÔÒØÕÖÚÛÙÜÇÑÝ";
        final var expectedMatching = "";

        final var mismatchingInput = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";
        final var expectedMismatching = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";

        // Act
        final var actualMatching = Strings.replaceAllNonWordCharacters(matchingInput, "");
        final var actualMismatching = Strings.replaceAllNonWordCharacters(mismatchingInput, "");

        // Assert
        assertEquals(expectedMatching, actualMatching);
        assertEquals(expectedMismatching, actualMismatching);
    }

    @Test
    void testTrimToEmpty() {
        assertEquals("", Strings.trimToEmpty(null));
        assertEquals("", Strings.trimToEmpty(""));
        assertEquals("", Strings.trimToEmpty("     "));
        assertEquals("abc", Strings.trimToEmpty("abc"));
        assertEquals("abc", Strings.trimToEmpty("    abc    "));
    }

    @Test
    void testTrimToNull() {
        assertNull(Strings.trimToNull(null));
        assertNull(Strings.trimToNull(""));
        assertNull(Strings.trimToNull("     "));

        assertEquals("abc", Strings.trimToEmpty("abc"));
        assertEquals("abc", Strings.trimToEmpty("    abc    "));
    }

    @Test
    void testToRootUpperCase() {
        // Arrange
        final var alphabetInput    = "abcdefghijklmnopqrstuvwxyz";
        final var expectedAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        final var specialCharactersInput    = "áâàåãäæéêèëðíîìïóôòøõöúûùüçñý";
        final var expectedSpecialCharacters = "ÁÂÀÅÃÄÆÉÊÈËÐÍÎÌÏÓÔÒØÕÖÚÛÙÜÇÑÝ";

        // Act
        final var actualAlphabet = Strings.toRootUpperCase(alphabetInput);
        final var actualSpecialCharacters = Strings.toRootUpperCase(specialCharactersInput);

        // Assert
        assertNull(Strings.toRootUpperCase(null));

        assertEquals(expectedAlphabet, actualAlphabet);
        assertEquals(expectedSpecialCharacters, actualSpecialCharacters);
    }
}
