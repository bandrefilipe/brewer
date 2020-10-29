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
package io.bandrefilipe.brewer.persistence;

import io.bandrefilipe.brewer.application.core.domain.entities.Beer;
import io.bandrefilipe.brewer.application.core.domain.vo.Id;
import io.bandrefilipe.brewer.application.core.domain.vo.SKU;
import io.bandrefilipe.brewer.persistence.converters.ConversionFacade;
import io.bandrefilipe.brewer.persistence.model.BeerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author bandrefilipe
 * @since 2020-10-28
 */
class BeerRepositoryAdapterTest {

    private ConversionFacade $conversionFacade;
    private BeerEntityRepository $beerEntityRepository;

    private BeerRepositoryAdapter classUnderTest;

    @BeforeEach
    void beforeEach() {
        $conversionFacade = mock(ConversionFacade.class);
        $beerEntityRepository = mock(BeerEntityRepository.class);

        classUnderTest = new BeerRepositoryAdapter($conversionFacade, $beerEntityRepository);
    }

    @Test
    void testRetrieveBeerById() {
        // Arrange
        final var entity = new BeerEntity();
        entity.setSku("TEST001");
        doReturn(Optional.of(entity)).when($beerEntityRepository).findById(eq(123L));

        doReturn(Beer
                .builder()
                .id(Id.valueOf(123))
                .sku(SKU.valueOf("TEST001"))
                .build()
        ).when($conversionFacade).convertToBeer(same(entity));

        final var expected = Optional.of(Beer
                .builder()
                .id(Id.valueOf(123))
                .sku(SKU.valueOf("TEST001"))
                .build());

        // Act
        final var actual = classUnderTest.retrieveBeer(Id.valueOf(123));

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void testRetrieveBeerBySku() {
        // Arrange
        final var entity = new BeerEntity();
        entity.setSku("TEST002");
        doReturn(Optional.of(entity)).when($beerEntityRepository).findBySku(eq("TEST002"));

        doReturn(Beer
                .builder()
                .sku(SKU.valueOf("TEST002"))
                .build()
        ).when($conversionFacade).convertToBeer(same(entity));

        final var expected = Optional.of(Beer
                .builder()
                .sku(SKU.valueOf("TEST002"))
                .build());

        // Act
        final var actual = classUnderTest.retrieveBeer(SKU.valueOf("TEST002"));

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void testConvertToBeerNotBeingCalledForEmptyBeer() {
        // Arrange
        doReturn(Optional.empty()).when($beerEntityRepository).findById(any());
        doReturn(Optional.empty()).when($beerEntityRepository).findBySku(any());

        // Act
        classUnderTest.retrieveBeer(Id.empty());
        classUnderTest.retrieveBeer(SKU.empty());

        // Assert
        verify($conversionFacade, never()).convertToBeer(any());
    }
}
