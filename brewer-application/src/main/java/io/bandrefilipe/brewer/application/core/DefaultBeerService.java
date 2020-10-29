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
import io.bandrefilipe.brewer.application.ports.in.BeerService;
import io.bandrefilipe.brewer.application.ports.out.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author bandrefilipe
 * @since 2020-10-22
 */
@Slf4j
@Component
class DefaultBeerService implements BeerService {

    private final BeerRepository beerRepository;

    @Autowired
    DefaultBeerService(final BeerRepository beerRepository) {
        log.debug("Creating component for class {}", DefaultBeerService.class);
        this.beerRepository = beerRepository;
    }

    @Override
    public Optional<Beer> findBeer(final Id id) {
        return findBeerTemplate(id, beerRepository::retrieveBeer);
    }

    @Override
    public Optional<Beer> findBeer(final SKU sku) {
        return findBeerTemplate(sku, beerRepository::retrieveBeer);
    }

    <I, R> R findBeerTemplate(final I input,
                              final Function<I, R> function) {
        log.debug("input: {}", input);
        final var result = function.apply(input);
        log.debug("result: {}", result);
        return result;
    }
}
