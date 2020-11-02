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
package io.bandrefilipe.brewer.api;

import io.bandrefilipe.brewer.api.model.BeerResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author bandrefilipe
 * @since 2020-10-30
 */
class BeerRetrievalByIdTest extends AbstractRestAPITest {

    @Test
    void testResponseOk() {
        // Arrange
        final var requestEntity = RequestEntity
                .get(buildUri(ApiVersion.V1, "/beers/1"))
                .accept(MediaType.APPLICATION_JSON)
                .build();

        final var expectedResponseStatus = HttpStatus.OK;
        final var expectedResponseBody = BeerResponse
                .builder()
                .id(1L)
                .sku("TEST001")
                .build();

        // Act
        final var actualResponse = restTemplate.exchange(requestEntity, BeerResponse.class);

        // Assert
        assertEquals(expectedResponseStatus, actualResponse.getStatusCode());
        assertEquals(expectedResponseBody, actualResponse.getBody());
    }

    @Test
    void testResponseNotFound() {
        // Arrange
        final var requestEntity = RequestEntity
                .get(buildUri(ApiVersion.V1, "/beers/0"))
                .accept(MediaType.APPLICATION_JSON)
                .build();

        final var expectedResponseStatus = HttpStatus.NOT_FOUND;

        // Act
        final var actualResponse = restTemplate.exchange(requestEntity, BeerResponse.class);

        // Assert
        assertEquals(expectedResponseStatus, actualResponse.getStatusCode());
    }

    @Disabled @Test
    void testResponseBadRequest() {
        // Arrange
        final var requestEntity = RequestEntity
                .get(buildUri(ApiVersion.V1, "/beers/-1"))
                .accept(MediaType.APPLICATION_JSON)
                .build();

        final var expectedResponseStatus = HttpStatus.BAD_REQUEST;

        // Act
        final var actualResponse = restTemplate.exchange(requestEntity, BeerResponse.class);

        // Assert
        assertEquals(expectedResponseStatus, actualResponse.getStatusCode());
    }
}
