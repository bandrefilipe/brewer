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

import io.bandrefilipe.brewer.api.converters.ConversionFacade;
import io.bandrefilipe.brewer.api.model.BeerResponse;
import io.bandrefilipe.brewer.application.core.domain.entities.Beer;
import io.bandrefilipe.brewer.application.core.domain.entities.BeerFactory;
import io.bandrefilipe.brewer.application.core.domain.vo.Id;
import io.bandrefilipe.brewer.application.core.domain.vo.SKU;
import io.bandrefilipe.brewer.application.ports.in.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
class BeerControllerTest {

    private ConversionFacade mockedConversionFacade;
    private BeerService mockedBeerService;

    private BeerController objectUnderTest;

    @BeforeEach
    void setup() {
        setupConversionFacade();
        setupBeerService();

        this.objectUnderTest = spy(new BeerController(
                mockedConversionFacade,
                mockedBeerService
        ));
    }

    private void setupConversionFacade() {
        this.mockedConversionFacade = mock(ConversionFacade.class);
        doReturn(new BeerResponse())
                .when(mockedConversionFacade).convertToBeerResponse(any(Beer.class));
    }

    private void setupBeerService() {
        this.mockedBeerService = mock(BeerService.class);
        doReturn(Optional.of(BeerFactory.newBeer()))
                .when(mockedBeerService).findBeer(eq(Id.valueOf(200)));
        doReturn(Optional.empty())
                .when(mockedBeerService).findBeer(eq(Id.valueOf(404)));
        doReturn(Optional.of(BeerFactory.newBeer()))
                .when(mockedBeerService).findBeer(eq(SKU.valueOf("SKU200")));
        doReturn(Optional.empty())
                .when(mockedBeerService).findBeer(eq(SKU.valueOf("SKU204")));
    }

    @Test
    void respondsOkIfABeerIsFoundByItsId() {
        assertSame(
                HttpStatus.OK,
                objectUnderTest.getBeerById(200L).getStatusCode()
        );
    }

    @Test
    void respondsNotFoundIfABeerIsNotFoundByItsId() {
        assertSame(
                HttpStatus.NOT_FOUND,
                objectUnderTest.getBeerById(404L).getStatusCode()
        );
    }

    @Test
    void respondsOkIfABeerIsFoundByQueriedSku() {
        assertSame(
                HttpStatus.OK,
                objectUnderTest.getBeerBySku("SKU200").getStatusCode()
        );
    }

    @Test
    void respondsNoContentIfABeerIsNotFoundByQueriedSku() {
        assertSame(
                HttpStatus.NO_CONTENT,
                objectUnderTest.getBeerBySku("SKU204").getStatusCode()
        );
    }
}
