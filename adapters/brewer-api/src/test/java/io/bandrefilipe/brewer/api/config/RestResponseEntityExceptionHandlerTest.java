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
package io.bandrefilipe.brewer.api.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * @author bandrefilipe
 * @since 2020-12-28
 */
class RestResponseEntityExceptionHandlerTest {

    private final HashMap<Class<? extends Exception>, Exception> mockedExceptions = new HashMap<>();
    private WebRequest mockedRequest;
    private RestResponseEntityExceptionHandler objectUnderTest;

    @BeforeEach
    void setup() {
        setupExceptions();
        mockedRequest = mock(WebRequest.class);
        objectUnderTest = spy(new RestResponseEntityExceptionHandler());
    }

    @AfterEach
    void tearDown() {
        mockedExceptions.clear();
    }

    private void setupExceptions() {
        setupConstraintViolationException();
    }

    private void setupConstraintViolationException() {
        final var mockedPath = mock(Path.class);
        doReturn("propertyPath.test")
                .when(mockedPath).toString();

        final var mockedConstraintViolation = mock(ConstraintViolation.class);
        doReturn(mockedPath)
                .when(mockedConstraintViolation).getPropertyPath();
        doReturn("test message")
                .when(mockedConstraintViolation).getMessage();

        final var mockedConstraintViolationException = mock(ConstraintViolationException.class);
        doReturn(Collections.singleton(mockedConstraintViolation))
                .when(mockedConstraintViolationException).getConstraintViolations();

        mockedExceptions.put(ConstraintViolationException.class, mockedConstraintViolationException);
    }

    @SuppressWarnings("unchecked")
    private <T extends Exception> T getMockedExceptionForClass(final Class<T> clazz) {
        return (T) mockedExceptions.get(clazz);
    }

    @Test
    void handlingConstraintViolationExceptionMustReturnBadRequestStatus() {
        final var actualResponse = objectUnderTest.handleConstraintViolationException(
                getMockedExceptionForClass(ConstraintViolationException.class),
                mockedRequest
        );
        assertSame(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
    }

    @Test
    @SuppressWarnings({ "rawtypes", "ConstantConditions", "unchecked"})
    void handlingConstraintViolationExceptionMustReturnPathAndMessageOnBody() {
        final var actualResponse = objectUnderTest.handleConstraintViolationException(
                getMockedExceptionForClass(ConstraintViolationException.class),
                mockedRequest
        );
        final var actualBody = (Map) actualResponse.getBody();
        // Assert Path
        assertTrue(actualBody
                .keySet()
                .stream()
                .map(Object::toString)
                .anyMatch("propertyPath.test"::equals));
        // Assert Message
        assertTrue(actualBody
                .containsValue("test message"));
    }

    @Test
    void handlingConstraintViolationExceptionMustCallLogMethod() {
        objectUnderTest.handleConstraintViolationException(
                getMockedExceptionForClass(ConstraintViolationException.class),
                mockedRequest
        );
        verify(objectUnderTest, atLeastOnce()).log(
                any(ConstraintViolationException.class),
                same(HttpStatus.BAD_REQUEST),
                same(mockedRequest)
        );
    }
}
