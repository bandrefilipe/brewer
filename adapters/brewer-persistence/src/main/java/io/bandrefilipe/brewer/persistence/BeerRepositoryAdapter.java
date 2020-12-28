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
import io.bandrefilipe.brewer.application.ports.out.BeerRepository;
import io.bandrefilipe.brewer.persistence.converters.ConversionFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * @author bandrefilipe
 * @since 2020-10-25
 */
@Slf4j
@Component
class BeerRepositoryAdapter implements BeerRepository {

    private final ConversionFacade conversionFacade;
    private final BeerEntityRepository beerEntityRepository;

    @Autowired
    BeerRepositoryAdapter(final ConversionFacade conversionFacade,
                          final BeerEntityRepository beerEntityRepository) {
        log.debug("Creating component for {}", this.getClass());
        this.conversionFacade = requireNonNull(conversionFacade);
        this.beerEntityRepository = requireNonNull(beerEntityRepository);
    }

    @Override
    public Optional<Beer> retrieveBeer(final Id id) {
        log.debug("input: {}", id);
        return beerEntityRepository.findById(id.getValue())
                .map(conversionFacade::convertToBeer);
    }

    @Override
    public Optional<Beer> retrieveBeer(final SKU sku) {
        log.debug("input: {}", sku);
        return beerEntityRepository.findBySku(sku.getValue())
                .map(conversionFacade::convertToBeer);
    }
}
