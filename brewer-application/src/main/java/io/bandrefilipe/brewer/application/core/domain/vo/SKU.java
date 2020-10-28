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
package io.bandrefilipe.brewer.application.core.domain.vo;

import io.bandrefilipe.brewer.application.core.domain.exceptions.ParseException;
import io.bandrefilipe.brewer.commons.lang.Strings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

/**
 * @author bandrefilipe
 * @since 2020-10-22
 */
@Slf4j
@Getter @EqualsAndHashCode
public final class SKU {

    private final String value;

    private SKU(final String value) {
        log.debug("Constructing SKU for value \"{}\"", value);
        if (value == null) {
            this.value = null;
        }
        else {
            this.value = Optional
                    .ofNullable(Strings.replaceAllNonWordCharacters(value, Strings.EMPTY))
                    .map(Strings::trimToNull)
                    .map(Strings::toRootUpperCase)
                    .orElseThrow(ParseException::new);
        }
        log.debug("SKU \"{}\" constructed", this);
    }

    public static SKU valueOf(final String sku) {
        return new SKU(sku);
    }

    @Override
    public String toString() {
        return Objects.toString(this.value);
    }
}
