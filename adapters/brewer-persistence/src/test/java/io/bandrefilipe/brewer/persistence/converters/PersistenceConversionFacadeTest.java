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
package io.bandrefilipe.brewer.persistence.converters;

import io.bandrefilipe.brewer.application.core.domain.entities.Beer;
import io.bandrefilipe.brewer.application.core.domain.entities.BeerFactory;
import io.bandrefilipe.brewer.persistence.model.BeerEntity;
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
 * @since 2020-10-28
 */
class PersistenceConversionFacadeTest {

    private Function<BeerEntity, Beer> mockedBeerEntityToBeerFunction;

    private PersistenceConversionFacade objectUnderTest;

    @BeforeEach
    void setup() {
        setupBeerEntityToBeerFunction();
        objectUnderTest = spy(new PersistenceConversionFacade(mockedBeerEntityToBeerFunction));
    }

    @SuppressWarnings("unchecked")
    private void setupBeerEntityToBeerFunction() {
        mockedBeerEntityToBeerFunction = mock(Function.class);
        doReturn(BeerFactory.newBeer())
                .when(mockedBeerEntityToBeerFunction).apply(any());
    }

    @Test
    void beerEntityToBeerFunctionMustBeCalledEvenForANullArgument() {
        objectUnderTest.convertToBeer((BeerEntity) null);
        verify(mockedBeerEntityToBeerFunction, atLeastOnce()).apply(any());
    }

    @Test
    void beerEntityToBeerFunctionMustReceiveTheSameArgumentInstance() {
        final var input = new BeerEntity();
        objectUnderTest.convertToBeer(input);
        verify(mockedBeerEntityToBeerFunction, atLeastOnce()).apply(same(input));
    }
}
