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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * @author bandrefilipe
 * @since 2020-10-29
 */
@Slf4j
@Component
class ApiConversionFacade implements ConversionFacade {

    private final Function<Beer, BeerResponse> beerToBeerResponseFunction;

    @Autowired
    ApiConversionFacade(final Function<Beer, BeerResponse> beerToBeerResponseFunction) {
        log.debug("Creating component for {}", this.getClass());
        this.beerToBeerResponseFunction = requireNonNull(beerToBeerResponseFunction);
    }

    @Override
    public BeerResponse convertToBeerResponse(final Beer beer) {
        return beerToBeerResponseFunction.apply(beer);
    }
}
