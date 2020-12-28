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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author bandrefilipe
 * @since 2020-10-28
 */
class BeerRepositoryAdapterTest {

    private ConversionFacade mockedConversionFacade;
    private BeerEntityRepository mockedBeerEntityRepository;

    private BeerRepositoryAdapter objectUnderTest;

    @BeforeEach
    void setup() {
        setupConversionFacade();
        setupBeerEntityRepository();

        objectUnderTest = spy(new BeerRepositoryAdapter(mockedConversionFacade, mockedBeerEntityRepository));
    }

    private void setupConversionFacade() {
        mockedConversionFacade = mock(ConversionFacade.class);
        doReturn(Beer.builder().build())
                .when(mockedConversionFacade).convertToBeer(any(BeerEntity.class));
    }

    private void setupBeerEntityRepository() {
        mockedBeerEntityRepository = mock(BeerEntityRepository.class);
        doReturn(Optional.empty())
                .when(mockedBeerEntityRepository).findById(isNull());
        doReturn(Optional.of(new BeerEntity()))
                .when(mockedBeerEntityRepository).findById(any(Long.class));
        doReturn(Optional.empty())
                .when(mockedBeerEntityRepository).findBySku(isNull());
        doReturn(Optional.of(new BeerEntity()))
                .when(mockedBeerEntityRepository).findBySku(any(String.class));
    }

    @Test
    void conversionIsCalledIfEntityIsRetrievedById() {
        objectUnderTest.retrieveBeer(Id.valueOf(1));
        verify(mockedConversionFacade, times(1)).convertToBeer(any(BeerEntity.class));
    }

    @Test
    void beerIsReturnedIfEntityIsRetrievedById() {
        assertTrue(objectUnderTest
                .retrieveBeer(Id.valueOf(1))
                .isPresent()
        );
    }

    @Test
    void conversionIsNotCalledIfEntityIsNotRetrievedById() {
        objectUnderTest.retrieveBeer(Id.empty());
        verify(mockedConversionFacade, never()).convertToBeer(any());
    }

    @Test
    void beerIsNotReturnedIfEntityIsNotRetrievedById() {
        assertTrue(objectUnderTest
                .retrieveBeer(Id.empty())
                .isEmpty()
        );
    }

    @Test
    void throwsNullPointerExceptionIfIdArgumentIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> objectUnderTest.retrieveBeer((Id) null)
        );
    }

    @Test
    void conversionIsCalledIfEntityIsRetrievedBySku() {
        objectUnderTest.retrieveBeer(SKU.valueOf("TEST"));
        verify(mockedConversionFacade, times(1)).convertToBeer(any(BeerEntity.class));
    }

    @Test
    void beerIsReturnedIfEntityIsRetrievedBySku() {
        assertTrue(objectUnderTest
                .retrieveBeer(SKU.valueOf("TEST"))
                .isPresent()
        );
    }

    @Test
    void conversionIsNotCalledIfEntityIsNotRetrievedBySku() {
        objectUnderTest.retrieveBeer(SKU.empty());
        verify(mockedConversionFacade, never()).convertToBeer(any());
    }

    @Test
    void beerIsNotReturnedIfEntityIsNotRetrievedBySku() {
        assertTrue(objectUnderTest
                .retrieveBeer(SKU.empty())
                .isEmpty()
        );
    }

    @Test
    void throwsNullPointerExceptionIfSkuArgumentIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> objectUnderTest.retrieveBeer((SKU) null)
        );
    }
}
