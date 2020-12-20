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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author bandrefilipe
 * @since 2020-10-22
 */
class SKUTest {

    private static final HashMap<String, SKU> expectedSkuByValidInput = new HashMap<>();
    private static final TreeSet<String> invalidInputs = new TreeSet<>();

    @BeforeEach
    void setup() {
        setupExpectedSkuByValidInput();
        setupInvalidValueOfInputs();
    }

    @AfterEach
    void tearDown() {
        expectedSkuByValidInput.clear();
        invalidInputs.clear();
    }

    private void setupExpectedSkuByValidInput() {
        expectedSkuByValidInput.put(null, SKU.empty());
        expectedSkuByValidInput.put("test1", SKU.valueOf("TEST1"));
        expectedSkuByValidInput.put("  test2  ", SKU.valueOf("TEST2"));
        expectedSkuByValidInput.put("~!@#$%^&*()+{}|:<>?  test3  `-=[]\\;',./", SKU.valueOf("TEST3"));
        expectedSkuByValidInput.put("  test!4@  ", SKU.valueOf("TEST4"));
        expectedSkuByValidInput.put("  test 5  ", SKU.valueOf("TEST5"));
        expectedSkuByValidInput.put("  test _ 6", SKU.valueOf("TEST_6"));
    }

    private void setupInvalidValueOfInputs() {
        invalidInputs.add("");
        invalidInputs.add(" ");
        invalidInputs.add("\u0000");
        invalidInputs.add("\u00FF");
        invalidInputs.add(" ~!@#$%^&*()+`-={}|[]\\:;'<>?,./ ");
        invalidInputs.add("ÁáÂâÀàÅåÃãÄäÆæ");
        invalidInputs.add("ÉéÊêÈèËëÐð");
        invalidInputs.add("ÍíÎîÌìÏï");
        invalidInputs.add("ÓóÔôÒòØøÕõÖö");
        invalidInputs.add("ÚúÛûÙùÜü");
        invalidInputs.add("Çç");
        invalidInputs.add("Ññ");
        invalidInputs.add("Ýý");
        invalidInputs.add("®©Þþß");
    }

    @Test
    void testValueOfForValidInputs() {
        for (final var validInput : expectedSkuByValidInput.keySet()) {
            assertEquals(
                    expectedSkuByValidInput.get(validInput),
                    SKU.valueOf(validInput)
            );
        }
    }

    @Test
    void skuValuesHaveNoNonWordCharacters() {
        assertEquals("TEST", SKU.valueOf("TEST~!@#$%^&*()+`-={}|[]\\:;'<>?,./").getValue());
    }

    @Test
    void skuValuesAreTrimmed() {
        assertEquals("TEST", SKU.valueOf("  TEST  ").getValue());
    }

    @Test
    void skuValuesAreUpperCase() {
        assertEquals("TEST", SKU.valueOf("test").getValue());
    }

    @Test
    void throwsExceptionIfValueOfInputIsInvalid() {
        for (final var invalidInput : invalidInputs) {
            assertThrows(
                    ParseException.class,
                    () -> SKU.valueOf(invalidInput)
            );
        }
    }

    @Test
    void testToString() {
        assertEquals("null", SKU.valueOf(null).toString());
        assertEquals("ABC123", SKU.valueOf("abc123").toString());
    }
}
