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

import io.bandrefilipe.brewer.persistence.model.BeerEntity.Flavor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author bandrefilipe
 * @since 2020-10-12
 */
class BeerEntityFlavorTest {

    private final TreeSet<String> validCodes = new TreeSet<>();
    private final TreeSet<String> invalidCodes = new TreeSet<>();
    private final HashMap<String, Flavor> expectedFlavorByCodeMap = new HashMap<>();

    @BeforeEach
    void setup() {
        setupValidCodes();
        setupInvalidCodes();
        setupExpectedFlavorByCodeMap();
    }

    @AfterEach
    void tearDown() {
        validCodes.clear();
        invalidCodes.clear();
        expectedFlavorByCodeMap.clear();
    }

    private void setupValidCodes() {
        validCodes.addAll(asList(
                "B", "F", "S", "T", "W"
        ));
    }

    private void setupInvalidCodes() {
        invalidCodes.addAll(asList(
                "b", "f", "s", "t", "w", "A", "Z"
        ));
    }

    private void setupExpectedFlavorByCodeMap() {
        expectedFlavorByCodeMap.put("B", Flavor.BITTER);
        expectedFlavorByCodeMap.put("F", Flavor.FRUITY);
        expectedFlavorByCodeMap.put("S", Flavor.SOFT);
        expectedFlavorByCodeMap.put("T", Flavor.STRONG);
        expectedFlavorByCodeMap.put("W", Flavor.SWEET);
    }

    @Test
    void getsEnumByCodeIfArgumentIsValid() {
        for (final var code : validCodes) {
            final var expected = expectedFlavorByCodeMap.get(code);
            final var actual = Flavor.getByCode(code);
            assertNotNull(actual);
            assertSame(expected, actual);
        }
    }

    @Test
    void gettingEnumByCodeReturnsNullIfArgumentIsNull() {
        assertNull(Flavor.getByCode((String) null));
    }

    @Test
    void throwsExceptionWhenGettingEnumByCodeIfCodeIsInvalid() {
        for (final var code : invalidCodes) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> Flavor.getByCode(code)
            );
        }
    }

    @Test
    void allEnumsAreTested() {
        final var testedFlavors = validCodes
                .stream()
                .map(Flavor::getByCode)
                .collect(toList());
        final var flavors = asList(Flavor.values());
        assertTrue(testedFlavors.containsAll(flavors));
    }
}
