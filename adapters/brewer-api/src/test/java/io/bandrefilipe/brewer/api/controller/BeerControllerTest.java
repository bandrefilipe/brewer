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

import io.bandrefilipe.brewer.application.core.domain.entities.BeerFactory;
import io.bandrefilipe.brewer.application.core.domain.vo.Id;
import io.bandrefilipe.brewer.application.core.domain.vo.SKU;
import io.bandrefilipe.brewer.application.ports.in.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * @author bandrefilipe
 * @since 2020-10-28
 */
class BeerControllerTest {

    private BeerService $beerService;

    private BeerController classUnderTest;

    @BeforeEach
    void beforeEach() {
        this.$beerService = mock(BeerService.class);

        this.classUnderTest = new BeerController($beerService);
    }

    @Test
    void testGetBeerByIdForResponseEntityOk() {
        // Arrange
        doReturn(Optional.of(BeerFactory.newBeer()))
                .when($beerService).findBeer(any(Id.class));

        final var expected = ResponseEntity.ok(BeerFactory.newBeer());

        // Act
        final var actual = classUnderTest.getBeerById(123L);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void testGetBeerByIdForResponseEntityNotFound() {
        // Arrange
        doReturn(Optional.empty())
                .when($beerService).findBeer(any(Id.class));

        final var expected = ResponseEntity.notFound().build();

        // Act
        final var actual = classUnderTest.getBeerById(123L);

        // Arrange
        assertEquals(expected, actual);
    }

    @Test
    void testGetBeerBySkuForResponseEntityOk() {
        // Arrange
        doReturn(Optional.of(BeerFactory.newBeer()))
                .when($beerService).findBeer(any(SKU.class));

        final var expected = ResponseEntity.ok(BeerFactory.newBeer());

        // Act
        final var actual = classUnderTest.getBeerBySku("TEST_SKU");

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void testGetBeerBySkuForResponseEntityNoContent() {
        // Arrange
        doReturn(Optional.empty())
                .when($beerService).findBeer(any(SKU.class));

        final var expected = ResponseEntity.noContent().build();

        // Act
        final var actual = classUnderTest.getBeerBySku("TEST_SKU");

        // Arrange
        assertEquals(expected, actual);
    }
}
