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
import io.bandrefilipe.brewer.application.core.domain.vo.Id;
import io.bandrefilipe.brewer.application.core.domain.vo.SKU;
import io.bandrefilipe.brewer.application.ports.in.BeerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * @author bandrefilipe
 * @since 2020-10-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping(
        path = ApiVersion.V1 + "/beers",
        produces = MediaType.APPLICATION_JSON_VALUE)
class BeerController {

    private final ConversionFacade conversionFacade;
    private final BeerService beerService;

    @Autowired
    BeerController(final ConversionFacade conversionFacade,
                   final BeerService beerService) {
        this.conversionFacade = requireNonNull(conversionFacade);
        this.beerService = requireNonNull(beerService);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BeerResponse> getBeerById(@PathVariable @PositiveOrZero final Long id) {
        log.debug("input: {}", id);
        return Optional
                .ofNullable(id)
                .map(Id::valueOf)
                .flatMap(beerService::findBeer)
                .map(conversionFacade::convertToBeerResponse)
                .map(Responses::ok)
                .orElseGet(Responses::notFound);
    }

    @GetMapping
    public ResponseEntity<BeerResponse> getBeerBySku(@RequestParam @Pattern(regexp = "\\w+") final String sku) {
        log.debug("input: {}", sku);
        return Optional
                .ofNullable(sku)
                .map(SKU::valueOf)
                .flatMap(beerService::findBeer)
                .map(conversionFacade::convertToBeerResponse)
                .map(Responses::ok)
                .orElseGet(Responses::noContent);
    }
}
