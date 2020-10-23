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
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author bandrefilipe
 * @since 1.0.0
 */
class SKUTest {

    @Test
    void testValueOf() {
        assertNull(SKU.valueOf(null).getValue());

        assertEquals("TEST123", SKU.valueOf("test123").getValue());
        assertEquals("TEST234", SKU.valueOf("  test234  ").getValue());
        assertEquals("TEST345", SKU.valueOf("~!@#$%^&*()+{}|:<>?  test345  `-=[]\\;',./").getValue());
        assertEquals("TEST456", SKU.valueOf("  test!4@5#6  ").getValue());
        assertEquals("TEST567", SKU.valueOf("  test 5 6 7  ").getValue());
        assertEquals("TEST_67", SKU.valueOf("  test _ 6 7").getValue());

        final var expectedException = ParseException.class;
        assertThrows(expectedException, () -> SKU.valueOf(""));
        assertThrows(expectedException, () -> SKU.valueOf(" "));
        assertThrows(expectedException, () -> SKU.valueOf("\u0000"));
        assertThrows(expectedException, () -> SKU.valueOf("\u00FF"));
        assertThrows(expectedException, () -> SKU.valueOf(" ~!@#$%^&*()+`-={}|[]\\:;'<>?,./ "));
        assertThrows(expectedException, () -> SKU.valueOf("ÁáÂâÀàÅåÃãÄäÆæ"));
        assertThrows(expectedException, () -> SKU.valueOf("ÉéÊêÈèËëÐð"));
        assertThrows(expectedException, () -> SKU.valueOf("ÍíÎîÌìÏï"));
        assertThrows(expectedException, () -> SKU.valueOf("ÓóÔôÒòØøÕõÖö"));
        assertThrows(expectedException, () -> SKU.valueOf("ÚúÛûÙùÜü"));
        assertThrows(expectedException, () -> SKU.valueOf("Çç"));
        assertThrows(expectedException, () -> SKU.valueOf("Ññ"));
        assertThrows(expectedException, () -> SKU.valueOf("Ýý"));
        assertThrows(expectedException, () -> SKU.valueOf("®©Þþß"));
    }

    @Test
    void testToString() {
        assertEquals("null", SKU.valueOf(null).toString());
        assertEquals("ABC123", SKU.valueOf("abc123").toString());
    }

    @Test
    void testIdentity() {
        // Arrange
        final var valuesBySKU = new HashMap<SKU, String>();
        asList(
                SKU.valueOf(null),
                SKU.valueOf("abc"),
                SKU.valueOf("   xyz   ")
        ).forEach(sku -> valuesBySKU.put(sku, sku.getValue()));

        // Act & Assert
        assertNull(valuesBySKU.get(SKU.valueOf(null)));

        assertEquals("ABC", valuesBySKU.get(SKU.valueOf("  abc  ")));
        assertEquals("XYZ", valuesBySKU.get(SKU.valueOf("xyz")));
    }
}
