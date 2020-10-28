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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * @author bandrefilipe
 * @since 2020-10-22
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Strings {

    public static final String EMPTY = "";

    private static final Pattern NON_WORD_CHARACTER_PATTERN = Pattern.compile("\\W"); // Anything that isn't a word character (including punctuation).

    public static String replaceAllNonWordCharacters(final CharSequence input,
                                                     final String replacement) {
        return NON_WORD_CHARACTER_PATTERN
                .matcher(input)
                .replaceAll(replacement);
    }

    public static String trimToEmpty(final String str) {
        return StringUtils.trimToEmpty(str);
    }

    public static String trimToNull(final String str) {
        return StringUtils.trimToNull(str);
    }

    public static String toRootUpperCase(final String str) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase(Locale.ROOT);
    }
}
