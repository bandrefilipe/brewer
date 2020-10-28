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
 * @since 2020-10-22
 */
class IdTest {

    @Test
    void testValueOf() {
        assertNull(Id.valueOf(null).getValue());

        assertEquals(Long.valueOf(0L), Id.valueOf(0L).getValue());
        assertEquals(Long.valueOf(Long.MAX_VALUE), Id.valueOf(Long.MAX_VALUE).getValue());

        assertThrows(ParseException.class, () -> Id.valueOf(-1L));
    }

    @Test
    void testToString() {
        assertEquals("null", Id.valueOf(null).toString());
        assertEquals("0", Id.valueOf(0L).toString());
        assertEquals("9223372036854775807", Id.valueOf(Long.MAX_VALUE).toString());
    }

    @Test
    void testIdentity() {
        // Arrange
        final var valuesById = new HashMap<Id, Long>();
        asList(
                Id.valueOf(null),
                Id.valueOf(0L),
                Id.valueOf(Long.MAX_VALUE)
        ).forEach(id -> valuesById.put(id, id.getValue()));

        // Act & Assert
        assertNull(valuesById.get(Id.valueOf(null)));

        assertEquals(0L, valuesById.get(Id.valueOf(0L)));
        assertEquals(Long.MAX_VALUE, valuesById.get(Id.valueOf(Long.MAX_VALUE)));
    }
}
