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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @param <T> The type of object under test.
 * @author bandrefilipe
 * @since 2020-10-10
 */
abstract class AbstractEqualsAndHashCodeTest<T> {

    private Object x;
    private Object y;
    private Object z;

    abstract T newObject();

    @BeforeEach
    final void setup() {
        x = newObject();
        y = newObject();
        z = newObject();
    }

    /**
     * For any non-null reference value {@code x},
     * {@code x.equals(x)} should return {@code true}.
     */
    @Test
    final void equalsIsReflexive() {
        final var xEqualsX = x.equals(x);
        assertTrue(xEqualsX);
    }

    /**
     * For any non-null reference values {@code x} and {@code y},
     * {@code x.equals(y)} should return {@code true} if and only if
     * {@code y.equals(x)} returns {@code true}.
     */
    @Test
    final void equalsIsSymmetric() {
        final var xEqualsY = x.equals(y);
        final var yEqualsX = y.equals(x);
        assertTrue(xEqualsY);
        assertTrue(yEqualsX);
    }

    /**
     * For any non-null reference values {@code x}, {@code y}, and {@code z},
     * if {@code x.equals(y)} returns {@code true}
     * and {@code y.equals(z)} returns {@code true},
     * then {@code x.equals(z)} should return {@code true}.
     */
    @Test
    final void equalsIsTransitive() {
        final var xEqualsY = x.equals(y);
        final var yEqualsZ = y.equals(z);
        final var xEqualsZ = x.equals(z);
        assertTrue(xEqualsY);
        assertTrue(yEqualsZ);
        assertTrue(xEqualsZ);
    }

    /**
     * For any non-null reference values {@code x} and {@code y},
     * multiple invocations of {@code x.equals(y)}
     * consistently return {@code true} or
     * consistently return {@code false},
     * provided no information used in {@code equals} comparisons
     * on the objects is modified.
     */
    @Test
    final void equalsIsConsistent() {
        final var max = 100;
        final var expected = x.equals(y);
        for (int i = 1; i <= max; i++) {
            final var actual = x.equals(y);
            final var current = i;
            assertEquals(expected, actual,
                    () -> format("Multiple calls to equals(Object) should produce consistent results. It failed on attempt %d/%d", current, max));
        }
    }

    /**
     * For any non-null reference value {@code x},
     * {@code x.equals(null)} should return {@code false}.
     */
    @Test
    final void equalsReturnsFalseIfArgumentIsNull() {
        final var xEqualsNull = x.equals(null);
        assertFalse(xEqualsNull);
    }

    /**
     * According to the {@link Object#hashCode()}'s contract:
     * <p>
     * Whenever it is invoked on the same object more than once during
     * an execution of a Java application, the {@code hashCode} method
     * must consistently return the same integer, provided no information
     * used in {@code equals} comparisons on the object is modified.
     * This integer need not remain consistent from one execution of an
     * application to another execution of the same application.
     */
    @Test
    final void hashCodeIsConsistent() {
        final var max = 100;
        final var expected = x.hashCode();
        for (int i = 1; i <= max; i++) {
            final var actual = x.hashCode();
            final var current = i;
            assertEquals(expected, actual,
                    () -> format("Multiple calls to hashcode() should produce consistent results. It failed on attempt %d/%d", current, max));
        }
    }

    /**
     * According to the {@link Object#hashCode()}'s contract:
     * <p>
     * If two objects are equal according to the {@code equals(Object)}
     * method, then calling the {@code hashCode} method on each of
     * the two objects must produce the same integer result.
     */
    @Test
    final void hashCodesOfTwoEqualObjectsMustBeSameValue() {
        if (!x.equals(y)) {
            fail("Objects x and y should be equals");
        }
        final var expected = x.hashCode();
        final var actual = y.hashCode();
        assertEquals(expected, actual);
    }
}
