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
package io.bandrefilipe.brewer.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author bandrefilipe
 * @since 2020-10-22
 */
class ResponsesTest {

    @Test
    void testOkResponse() {
        // Arrange
        final String stringInput = "Response Body";
        final var expectedStringResponse = ResponseEntity.ok("Response Body");

        final Collection<Integer> integerCollectionInput = asList(1, 2, 3, 4, 5);
        final var expectedIntegerCollectionResponse = ResponseEntity.ok(asList(1, 2, 3, 4, 5));

        final Object nullInput = null;
        final var expectedNullResponse = ResponseEntity.ok((Object) null);

        // Act
        final var actualStringResponse = Responses.ok(stringInput);
        final var actualIntegerCollectionResponse = Responses.ok(integerCollectionInput);
        final var actualNullResponse = Responses.ok(nullInput);

        // Assert
        assertEquals(expectedStringResponse, actualStringResponse);
        assertEquals(expectedIntegerCollectionResponse, actualIntegerCollectionResponse);
        assertEquals(expectedNullResponse, actualNullResponse);
    }

    @Test
    void testNoContentResponse() {
        // Arrange
        final var expected = ResponseEntity.noContent().build();

        // Act
        final var actual = Responses.noContent();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void testNotFoundResponse() {
        // Arrange
        final var expected = ResponseEntity.notFound().build();

        // Act
        final var actual = Responses.notFound();

        // Assert
        assertEquals(expected, actual);
    }
}
