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
import io.bandrefilipe.brewer.application.core.domain.vo.Id;
import io.bandrefilipe.brewer.application.core.domain.vo.SKU;
import io.bandrefilipe.brewer.persistence.model.BeerEntity;
import io.bandrefilipe.brewer.persistence.model.BeerEntity.Flavor;
import io.bandrefilipe.brewer.persistence.model.BeerEntity.Origin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author bandrefilipe
 * @since 2020-10-25
 */
class BeerEntityToBeerFunctionTest {

    private final BeerEntityToBeerFunction functionUnderTest = BeerEntityToBeerFunction.getInstance();

    private BeerEntity input;
    private Beer expected;

    @BeforeEach
    void setup() {
        setupInput();
        setupExpected();
    }

    private void setupInput() {
        input = new BeerEntity();
        input.setId(1L);
        input.setSku("TEST_SKU");
        input.setName("Beer Name");
        input.setDescription("The Description");
        input.setUnitPrice(BigDecimal.valueOf(12.95));
        input.setAlcoholContent(BigDecimal.valueOf(8.0));
        input.setCommission(BigDecimal.valueOf(20.0));
        input.setStock(2_000);
        input.setOrigin(Origin.DOMESTIC);
        input.setFlavor(Flavor.SOFT);
        input.setType(null);
    }

    private void setupExpected() {
        expected = Beer
                .builder()
                .id(Id.valueOf(1))
                .sku(SKU.valueOf("TEST_SKU"))
                .build();
    }

    @Test
    void convertsTypeIfArgumentIsNotNull() {
        final var actual = functionUnderTest.apply(input);
        assertEquals(expected, actual);
    }

    @Test
    void throwsNullPointerExceptionIfArgumentIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> functionUnderTest.apply((BeerEntity) null)
        );
    }
}
