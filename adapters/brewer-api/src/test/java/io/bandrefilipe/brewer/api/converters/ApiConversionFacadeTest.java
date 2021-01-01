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
package io.bandrefilipe.brewer.api.converters;

import io.bandrefilipe.brewer.api.model.BeerResponse;
import io.bandrefilipe.brewer.application.core.domain.entities.Beer;
import io.bandrefilipe.brewer.application.core.domain.entities.BeerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * @author bandrefilipe
 * @since 2020-10-29
 */
class ApiConversionFacadeTest {

    private Function<Beer, BeerResponse> mockedBeerToBeerResponseFunction;

    private ApiConversionFacade objectUnderTest;

    @BeforeEach
    void setup() {
        setupBeerToBeerResponseFunction();
        objectUnderTest = spy(new ApiConversionFacade(mockedBeerToBeerResponseFunction));
    }

    @SuppressWarnings("unchecked")
    private void setupBeerToBeerResponseFunction() {
        mockedBeerToBeerResponseFunction = mock(Function.class);
        doReturn(new BeerResponse())
                .when(mockedBeerToBeerResponseFunction).apply(any());
    }

    @Test
    void beerToBeerResponseFunctionMustBeCalledEvenForANullArgument() {
        objectUnderTest.convertToBeerResponse(null);
        verify(mockedBeerToBeerResponseFunction, atLeastOnce()).apply(any());
    }

    @Test
    void beerToBeerResponseFunctionMustReceiveTheSameArgumentInstance() {
        final var input = BeerFactory.newBeer();
        objectUnderTest.convertToBeerResponse(input);
        verify(mockedBeerToBeerResponseFunction, atLeastOnce()).apply(same(input));
    }
}
