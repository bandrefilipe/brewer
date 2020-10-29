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
package io.bandrefilipe.brewer.persistence.model;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.util.Optional;

/**
 * @author bandrefilipe
 * @since 2020-10-12
 */
@Slf4j
class FlavorConverter implements AttributeConverter<BeerEntity.Flavor, String> {

    @Override
    public String convertToDatabaseColumn(final BeerEntity.Flavor attribute) {
        final var dbData = Optional
                .ofNullable(attribute)
                .map(BeerEntity.Flavor::getCode)
                .orElse(null);
        log.debug("Converting attribute '{}' to database data '{}'", attribute, dbData);
        return dbData;
    }

    @Override
    public BeerEntity.Flavor convertToEntityAttribute(final String dbData) {
        final var attribute = BeerEntity.Flavor.getByCode(dbData);
        log.debug("Converting database data '{}' to attribute '{}'", dbData, attribute);
        return attribute;
    }
}
