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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;

/**
 * @author bandrefilipe
 * @since 2020-10-10
 */
class OriginConverterTest {

    private final BeerEntity.Origin[] origins = BeerEntity.Origin.values();
    private final TreeSet<String> validDatabaseColumns = new TreeSet<>();
    private final TreeSet<String> invalidDatabaseColumns = new TreeSet<>();
    private final EnumMap<BeerEntity.Origin, String> expectedDatabaseColumnByOrigin = new EnumMap<>(BeerEntity.Origin.class);
    private final HashMap<String, BeerEntity.Origin> expectedOriginByDatabaseColumn = new HashMap<>();

    private OriginConverter objectUnderTest;

    @BeforeEach
    void setup() {
        setupValidDatabaseColumns();
        setupInvalidDatabaseColumns();
        setupExpectedResults();
        objectUnderTest = spy(new OriginConverter());
    }

    @AfterEach
    void tearDown() {
        validDatabaseColumns.clear();
        invalidDatabaseColumns.clear();
        expectedDatabaseColumnByOrigin.clear();
        expectedOriginByDatabaseColumn.clear();
    }

    private void setupValidDatabaseColumns() {
        validDatabaseColumns.addAll(asList(
                "D", "I"
        ));
    }

    private void setupInvalidDatabaseColumns() {
        invalidDatabaseColumns.addAll(asList(
                "d", "i", "A", "Z"
        ));
    }

    private void setupExpectedResults() {
        setupExpectedDatabaseColumnByOrigin();
        setupExpectedOriginByDatabaseColumn();
    }

    private void setupExpectedDatabaseColumnByOrigin() {
        expectedDatabaseColumnByOrigin.put(BeerEntity.Origin.DOMESTIC, "D");
        expectedDatabaseColumnByOrigin.put(BeerEntity.Origin.IMPORTED, "I");
    }

    private void setupExpectedOriginByDatabaseColumn() {
        expectedDatabaseColumnByOrigin
                .forEach((key, value) -> expectedOriginByDatabaseColumn.put(value, key));
    }

    @Test
    void convertsOriginToDatabaseColumn() {
        for (final var origin : origins) {
            final var expected = expectedDatabaseColumnByOrigin.get(origin);
            final var actual = objectUnderTest.convertToDatabaseColumn(origin);
            assertSame(expected, actual);
        }
    }

    @Test
    void convertingOriginToDatabaseColumnReturnsNullIfArgumentIsNull() {
        assertNull(objectUnderTest.convertToDatabaseColumn((BeerEntity.Origin) null));
    }

    @Test
    void convertsDatabaseColumnToOriginIfArgumentIsValid() {
        for (final var validDatabaseColumn : validDatabaseColumns) {
            final var expected = expectedOriginByDatabaseColumn.get(validDatabaseColumn);
            final var actual = objectUnderTest.convertToEntityAttribute(validDatabaseColumn);
            assertSame(expected, actual);
        }
    }

    @Test
    void convertingDatabaseColumnToOriginReturnsNullIfArgumentIsNull() {
        assertNull(objectUnderTest.convertToEntityAttribute((String) null));
    }

    @Test
    void throwsExceptionWhenConvertingDatabaseColumnToOriginIfArgumentIsInvalid() {
        for (final var invalidDatabaseColumn : invalidDatabaseColumns) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> objectUnderTest.convertToEntityAttribute(invalidDatabaseColumn)
            );
        }
    }
}
