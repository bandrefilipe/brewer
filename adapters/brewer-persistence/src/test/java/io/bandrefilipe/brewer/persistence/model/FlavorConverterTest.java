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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author bandrefilipe
 * @since 1.0.0
 */
class FlavorConverterTest {

    private FlavorConverter converter;

    @BeforeEach
    void beforeEach() {
        converter = new FlavorConverter();
    }

    @Test
    void testConvertToDatabaseColumn() {
        // Arrange
        final var expectedBitter = "B";
        final var expectedFruity = "F";
        final var expectedSoft   = "S";
        final var expectedStrong = "T";
        final var expectedSweet  = "W";

        // Act
        final var actualBitter = converter.convertToDatabaseColumn(BeerEntity.Flavor.BITTER);
        final var actualFruity = converter.convertToDatabaseColumn(BeerEntity.Flavor.FRUITY);
        final var actualSoft   = converter.convertToDatabaseColumn(BeerEntity.Flavor.SOFT);
        final var actualStrong = converter.convertToDatabaseColumn(BeerEntity.Flavor.STRONG);
        final var actualSweet  = converter.convertToDatabaseColumn(BeerEntity.Flavor.SWEET);

        // Assert
        assertEquals(expectedBitter, actualBitter, "Wrong result");
        assertEquals(expectedFruity, actualFruity, "Wrong result");
        assertEquals(expectedSoft,   actualSoft,   "Wrong result");
        assertEquals(expectedStrong, actualStrong, "Wrong result");
        assertEquals(expectedSweet,  actualSweet,  "Wrong result");
        assertNull(converter.convertToDatabaseColumn(null), "A null argument should produce a null return value");
    }

    @Test
    void testConvertToEntityAttribute() {
        // Arrange
        final var expectedBitter = BeerEntity.Flavor.BITTER;
        final var expectedFruity = BeerEntity.Flavor.FRUITY;
        final var expectedSoft   = BeerEntity.Flavor.SOFT;
        final var expectedStrong = BeerEntity.Flavor.STRONG;
        final var expectedSweet  = BeerEntity.Flavor.SWEET;

        // Act
        final var actualBitter = converter.convertToEntityAttribute("B");
        final var actualFruity = converter.convertToEntityAttribute("F");
        final var actualSoft   = converter.convertToEntityAttribute("S");
        final var actualStrong = converter.convertToEntityAttribute("T");
        final var actualSweet  = converter.convertToEntityAttribute("W");

        // Assert
        assertEquals(expectedBitter, actualBitter, "Wrong result");
        assertEquals(expectedFruity, actualFruity, "Wrong result");
        assertEquals(expectedSoft,   actualSoft,   "Wrong result");
        assertEquals(expectedStrong, actualStrong, "Wrong result");
        assertEquals(expectedSweet,  actualSweet,  "Wrong result");
        assertNull(converter.convertToEntityAttribute(null), "A null argument should produce a null return value");
        assertThrows(
                IllegalArgumentException.class,
                () -> converter.convertToEntityAttribute("invalidInput"),
                "Any argument value other than null, \"B\", \"F\", \"S\", \"T\" or \"W\" should throw an exception"
        );
    }
}
