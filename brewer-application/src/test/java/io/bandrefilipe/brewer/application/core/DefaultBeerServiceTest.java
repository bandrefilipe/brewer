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
package io.bandrefilipe.brewer.application.core;

import io.bandrefilipe.brewer.application.core.domain.entities.Beer;
import io.bandrefilipe.brewer.application.core.domain.vo.Id;
import io.bandrefilipe.brewer.application.core.domain.vo.SKU;
import io.bandrefilipe.brewer.application.ports.out.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author bandrefilipe
 * @since 2020-10-28
 */
class DefaultBeerServiceTest {

    private BeerRepository $beerRepository;
    private DefaultBeerService $defaultBeerService;

    @BeforeEach
    void beforeEach() {
        $beerRepository = mock(BeerRepository.class);
        $defaultBeerService = spy(new DefaultBeerService($beerRepository));
    }

    @Test
    void testFindBeerById() {
        // Arrange
        doReturn(Optional.of(Beer
                .builder()
                .id(Id.valueOf(789))
                .build())
        ).when($beerRepository).retrieveBeer(eq(Id.valueOf(123)));

        final var input = Id.valueOf(123);

        final var expected = Optional.of(Beer
                .builder()
                .id(Id.valueOf(789))
                .build());

        // Act
        final var actual = $defaultBeerService.findBeer(input);

        // Assert
        assertEquals(expected, actual);

        verify($beerRepository, times(1)).retrieveBeer(same(input));
    }

    @Test
    void testFindBeerBySKU() {
        // Arrange
        doReturn(Optional.of(Beer
                .builder()
                .sku(SKU.valueOf("RESULT"))
                .build())
        ).when($beerRepository).retrieveBeer(eq(SKU.valueOf("INPUT")));

        final var input = SKU.valueOf("INPUT");

        final var expected = Optional.of(Beer
                .builder()
                .sku(SKU.valueOf("RESULT"))
                .build());

        // Act
        final var actual = $defaultBeerService.findBeer(input);

        // Assert
        assertEquals(expected, actual);

        verify($beerRepository, times(1)).retrieveBeer(same(input));
    }

    @Test
    void testFindBeerTemplate() {
        // Arrange
        final var input = new Object();
        final var expected = "function result";

        // Act
        final var actual = $defaultBeerService.findBeerTemplate(input, obj -> "function result");

        // Assert
        assertEquals(expected, actual);
    }
}
