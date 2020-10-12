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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author bandrefilipe
 * @since 1.0.0
 */
class BeerEntityFlavorTest {

    @Test
    void testGetByCode() {
        // Arrange
        final var expectedBitter = Flavor.BITTER;
        final var expectedFruity = Flavor.FRUITY;
        final var expectedSoft   = Flavor.SOFT;
        final var expectedStrong = Flavor.STRONG;
        final var expectedSweet  = Flavor.SWEET;

        // Act
        final var actualBitter = Flavor.getByCode("B");
        final var actualFruity = Flavor.getByCode("F");
        final var actualSoft   = Flavor.getByCode("S");
        final var actualStrong = Flavor.getByCode("T");
        final var actualSweet  = Flavor.getByCode("W");

        // Assert
        assertSame(expectedBitter, actualBitter, "Wrong result");
        assertSame(expectedFruity, actualFruity, "Wrong result");
        assertSame(expectedSoft,   actualSoft,   "Wrong result");
        assertSame(expectedStrong, actualStrong, "Wrong result");
        assertSame(expectedSweet,  actualSweet,  "Wrong result");
        assertNull(Flavor.getByCode(null), "A null argument should produce a null return value");
        assertThrows(
                IllegalArgumentException.class,
                () -> Flavor.getByCode("invalidInput"),
                "Any argument value other than null, \"B\", \"F\", \"S\", \"T\" or \"W\" should throw an exception"
        );
    }
}
