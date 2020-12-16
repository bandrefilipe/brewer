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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;

/**
 * @author bandrefilipe
 * @since 2020-10-12
 */
class FlavorConverterTest {

    private final BeerEntity.Flavor[] flavors = BeerEntity.Flavor.values();
    private final TreeSet<String> validDatabaseColumns = new TreeSet<>();
    private final TreeSet<String> invalidDatabaseColumns = new TreeSet<>();
    private final EnumMap<BeerEntity.Flavor, String> expectedDatabaseColumnByFlavor = new EnumMap<>(BeerEntity.Flavor.class);
    private final HashMap<String, BeerEntity.Flavor> expectedFlavorByDatabaseColumn = new HashMap<>();

    private FlavorConverter objectUnderTest;

    @BeforeEach
    void setup() {
        setupValidDatabaseColumns();
        setupInvalidDatabaseColumns();
        setupExpectedResults();
        objectUnderTest = spy(new FlavorConverter());
    }

    @AfterEach
    void tearDown() {
        validDatabaseColumns.clear();
        invalidDatabaseColumns.clear();
        expectedDatabaseColumnByFlavor.clear();
        expectedFlavorByDatabaseColumn.clear();
    }

    private void setupValidDatabaseColumns() {
        validDatabaseColumns.addAll(asList(
                "B", "F", "S", "T", "W"
        ));
    }

    private void setupInvalidDatabaseColumns() {
        invalidDatabaseColumns.addAll(asList(
                "b", "f", "s", "t", "w", "A", "Z"
        ));
    }

    private void setupExpectedResults() {
        setupExpectedDatabaseValueByFlavorMap();
        setupExpectedFlavorByDatabaseValueMap();
    }

    private void setupExpectedDatabaseValueByFlavorMap() {
        expectedDatabaseColumnByFlavor.put(BeerEntity.Flavor.BITTER, "B");
        expectedDatabaseColumnByFlavor.put(BeerEntity.Flavor.FRUITY, "F");
        expectedDatabaseColumnByFlavor.put(BeerEntity.Flavor.SOFT, "S");
        expectedDatabaseColumnByFlavor.put(BeerEntity.Flavor.STRONG, "T");
        expectedDatabaseColumnByFlavor.put(BeerEntity.Flavor.SWEET, "W");
    }

    private void setupExpectedFlavorByDatabaseValueMap() {
        expectedDatabaseColumnByFlavor
                .forEach((key, value) -> expectedFlavorByDatabaseColumn.put(value, key));
    }

    @Test
    void convertsFlavorToDatabaseColumn() {
        for (final var flavor : flavors) {
            final var expected = expectedDatabaseColumnByFlavor.get(flavor);
            final var actual = objectUnderTest.convertToDatabaseColumn(flavor);
            assertSame(expected, actual);
        }
    }

    @Test
    void convertingFlavorToDatabaseColumnReturnsNullIfArgumentIsNull() {
        assertNull(objectUnderTest.convertToDatabaseColumn((BeerEntity.Flavor) null));
    }

    @Test
    void convertsDatabaseColumnToFlavorIfArgumentIsValid() {
        for (final var validDatabaseColumn : validDatabaseColumns) {
            final var expected = expectedFlavorByDatabaseColumn.get(validDatabaseColumn);
            final var actual = objectUnderTest.convertToEntityAttribute(validDatabaseColumn);
            assertSame(expected, actual);
        }
    }

    @Test
    void convertingDatabaseColumnToFlavorReturnsNullIfArgumentIsNull() {
        assertNull(objectUnderTest.convertToEntityAttribute((String) null));
    }

    @Test
    void convertingDatabaseColumnToFlavorReturnsNullIfArgumentIsInvalid() {
        for (final var invalidDatabaseColumn : invalidDatabaseColumns) {
            assertNull(objectUnderTest.convertToEntityAttribute(invalidDatabaseColumn));
        }
    }
}
