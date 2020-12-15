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
package io.bandrefilipe.brewer.persistence.model;

import io.bandrefilipe.brewer.persistence.model.BeerEntity.Origin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.TreeSet;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author bandrefilipe
 * @since 2020-10-10
 */
class BeerEntityOriginTest {

    private final TreeSet<String> validCodes = new TreeSet<>();
    private final TreeSet<String> invalidCodes = new TreeSet<>();
    private final HashMap<String, Origin> expectedOriginByCodeMap = new HashMap<>();

    @BeforeEach
    void setup() {
        setupValidCodes();
        setupInvalidCodes();
        setupExpectedOriginByCodeMap();
    }

    @AfterEach
    void tearDown() {
        validCodes.clear();
        invalidCodes.clear();
        expectedOriginByCodeMap.clear();
    }

    private void setupValidCodes() {
        validCodes.addAll(asList(
                "D", "I"
        ));
    }

    private void setupInvalidCodes() {
        invalidCodes.addAll(asList(
                "d", "i", "A", "Z"
        ));
    }

    private void setupExpectedOriginByCodeMap() {
        expectedOriginByCodeMap.put("D", Origin.DOMESTIC);
        expectedOriginByCodeMap.put("I", Origin.IMPORTED);
    }

    @Test
    void getsEnumByCodeIfArgumentIsValid() {
        for (final var code : validCodes) {
            final var expected = expectedOriginByCodeMap.get(code);
            final var actual = Origin.getByCode(code);
            assertNotNull(actual);
            assertSame(expected, actual);
        }
    }

    @Test
    void gettingEnumByCodeReturnsNullIfArgumentIsNull() {
        assertNull(Origin.getByCode((String) null));
    }

    @Test
    void throwsExceptionWhenGettingEnumByCodeIfCodeIsInvalid() {
        for (final var code : invalidCodes) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> Origin.getByCode(code)
            );
        }
    }

    @Test
    void allEnumsAreTested() {
        final var testedFlavors = expectedOriginByCodeMap.values();
        assertTrue(Stream.of(Origin.values())
                .allMatch(testedFlavors::contains)
        );
    }
}
