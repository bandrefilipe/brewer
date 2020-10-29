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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author bandrefilipe
 * @since 2020-10-10
 */
class OriginConverterTest {

    private OriginConverter converter;

    @BeforeEach
    void beforeEach() {
        converter = new OriginConverter();
    }

    @Test
    void testConvertToDatabaseColumn() {
        // Arrange
        final var expectedDomestic = "D";
        final var expectedImported = "I";

        // Act
        final var actualDomestic = converter.convertToDatabaseColumn(Origin.DOMESTIC);
        final var actualImported = converter.convertToDatabaseColumn(Origin.IMPORTED);

        // Assert
        assertEquals(expectedDomestic, actualDomestic, "Wrong result");
        assertEquals(expectedImported, actualImported, "Wrong result");
        assertNull(converter.convertToDatabaseColumn(null), "A null argument should produce a null return value");
    }

    @Test
    void testConvertToEntityAttribute() {
        // Arrange
        final var expectedDomestic = Origin.DOMESTIC;
        final var expectedImported = Origin.IMPORTED;

        // Act
        final var actualDomestic = converter.convertToEntityAttribute("D");
        final var actualImported = converter.convertToEntityAttribute("I");

        // Assert
        assertEquals(expectedDomestic, actualDomestic, "Wrong result");
        assertEquals(expectedImported, actualImported, "Wrong result");
        assertNull(converter.convertToEntityAttribute(null), "A null argument should produce a null return value");
        assertThrows(
                IllegalArgumentException.class,
                () -> converter.convertToEntityAttribute("invalidInput"),
                "Any argument value other than null, \"D\" or \"I\" should throw an exception"
        );
    }
}
