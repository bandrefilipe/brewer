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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author bandrefilipe
 * @since 1.0.0
 */
@SpringBootTest
class DefaultConversionFacadeTest {

    @Autowired
    private DefaultConversionFacade defaultConversionFacade;

    @Test
    void testConvertToBeer() {
        // Arrange
        final var entity = new BeerEntity();
        entity.setId(1L);
        entity.setSku("AAA0001");
        entity.setName("Beer Name");
        entity.setDescription("The beer description");
        entity.setUnitPrice(BigDecimal.valueOf(12.95));
        entity.setAlcoholContent(BigDecimal.valueOf(8.0));
        entity.setCommission(BigDecimal.valueOf(20.0));
        entity.setStock(2_000);
        entity.setOrigin(Origin.DOMESTIC);
        entity.setFlavor(Flavor.SOFT);
        entity.setType(null);

        final var expected = Beer.builder()
                .id(Id.valueOf(1L))
                .sku(SKU.valueOf("AAA0001"))
                .build();

        // Act
        final var actual = defaultConversionFacade.convertToBeer(entity);

        // Assert
        assertEquals(expected, actual);

        assertThrows(NullPointerException.class, () -> defaultConversionFacade.convertToBeer(null));
    }
}
