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
package io.bandrefilipe.brewer.web.config.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author bandrefilipe
 * @since 1.0.0
 */
class LoggerInterceptorTest {

    @Test
    void testPreHandle_withLogInfoEnabled() {
        // Arrange
        final var $httpServletRequest = mock(HttpServletRequest.class);
        when($httpServletRequest.getMethod()).thenReturn("GET");

        final var $httpServletResponse = mock(HttpServletResponse.class);

        final var $loggerInterceptor = spy(LoggerInterceptor.INSTANCE);
        doNothing().when($loggerInterceptor).setRequestMetadata($httpServletRequest);
        when($loggerInterceptor.isLogInfoEnabled()).thenReturn(true);
        doReturn("/mocked/endpoint").when($loggerInterceptor).getRequestURI($httpServletRequest);

        // Act
        final var actual = $loggerInterceptor.preHandle($httpServletRequest, $httpServletResponse, new Object());

        // Assert
        verify($loggerInterceptor, times(1)).setRequestMetadata(same($httpServletRequest));
        verify($loggerInterceptor, times(1)).isLogInfoEnabled();
        verify($httpServletRequest, times(1)).getMethod();
        verify($loggerInterceptor, times(1)).getRequestURI(same($httpServletRequest));
        assertTrue(actual, "preHandle should always return true");
    }

    @Test
    void testPreHandle_withLogInfoDisabled() {
        // Arrange
        final var $httpServletRequest = mock(HttpServletRequest.class);

        final var $httpServletResponse = mock(HttpServletResponse.class);

        final var $loggerInterceptor = spy(LoggerInterceptor.INSTANCE);
        doNothing().when($loggerInterceptor).setRequestMetadata($httpServletRequest);
        when($loggerInterceptor.isLogInfoEnabled()).thenReturn(false);

        // Act
        final var actual = $loggerInterceptor.preHandle($httpServletRequest, $httpServletResponse, new Object());

        // Assert
        verify($loggerInterceptor, times(1)).setRequestMetadata(same($httpServletRequest));
        verify($loggerInterceptor, times(1)).isLogInfoEnabled();
        verify($httpServletRequest, never()).getMethod();
        verify($loggerInterceptor, never()).getRequestURI(same($httpServletRequest));
        assertTrue(actual, "preHandle should always return true");
    }

    @Test
    void testPostHandle_withLogInfoEnabled() {
        // Arrange
        final var $httpServletRequest = mock(HttpServletRequest.class);

        final var $httpServletResponse = mock(HttpServletResponse.class);

        final var $loggerInterceptor = spy(LoggerInterceptor.INSTANCE);
        when($loggerInterceptor.isLogInfoEnabled()).thenReturn(true);
        doReturn(HttpStatus.OK).when($loggerInterceptor).getResponseStatus($httpServletResponse);
        doReturn(1L).when($loggerInterceptor).getResponseTimeInMillis($httpServletRequest);

        // Act
        $loggerInterceptor.postHandle($httpServletRequest, $httpServletResponse, new Object(), null);

        // Assert
        verify($loggerInterceptor, times(1)).isLogInfoEnabled();
        verify($loggerInterceptor, times(1)).getResponseStatus(same($httpServletResponse));
        verify($loggerInterceptor, times(1)).getResponseTimeInMillis(same($httpServletRequest));
    }

    @Test
    void testPostHandle_withLogInfoDisabled() {
        // Arrange
        final var $httpServletRequest = mock(HttpServletRequest.class);

        final var $httpServletResponse = mock(HttpServletResponse.class);

        final var $loggerInterceptor = spy(LoggerInterceptor.INSTANCE);
        when($loggerInterceptor.isLogInfoEnabled()).thenReturn(false);

        // Act
        $loggerInterceptor.postHandle($httpServletRequest, $httpServletResponse, new Object(), null);

        // Assert
        verify($loggerInterceptor, times(1)).isLogInfoEnabled();
        verify($loggerInterceptor, never()).getResponseStatus(same($httpServletResponse));
        verify($loggerInterceptor, never()).getResponseTimeInMillis(same($httpServletRequest));
    }

    @Test
    void testSetRequestMetadata() {
        // Arrange
        final var $loggerInterceptor = spy(LoggerInterceptor.INSTANCE);
        when($loggerInterceptor.currentNanoTime()).thenReturn(1_000_000L);

        final var $httpServletRequest = mock(HttpServletRequest.class);

        // Act
        $loggerInterceptor.setRequestMetadata($httpServletRequest);

        // Assert
        verify($httpServletRequest, times(1)).setAttribute(contains("request.Timestamp"), eq(1_000_000L));
    }

    @Test
    void testGetRequestURI_withoutQueryString() {
        // Arrange
        final var $httpServletRequest = mock(HttpServletRequest.class);
        when($httpServletRequest.getRequestURI()).thenReturn("/mocked/endpoint");
        when($httpServletRequest.getQueryString()).thenReturn(null);

        final var expected = "/mocked/endpoint";

        // Act
        final var actual = LoggerInterceptor.INSTANCE.getRequestURI($httpServletRequest);

        // Assert
        verify($httpServletRequest, times(1)).getRequestURI();
        verify($httpServletRequest, times(1)).getQueryString();
        assertEquals(expected, actual, "Wrong result");
    }

    @Test
    void testGetRequestURI_withQueryString() {
        // Arrange
        final var $httpServletRequest = mock(HttpServletRequest.class);
        when($httpServletRequest.getRequestURI()).thenReturn("/mocked/endpoint");
        when($httpServletRequest.getQueryString()).thenReturn("param=value");

        final var expected = "/mocked/endpoint?param=value";

        // Act
        final var actual = LoggerInterceptor.INSTANCE.getRequestURI($httpServletRequest);

        // Assert
        verify($httpServletRequest, times(1)).getRequestURI();
        verify($httpServletRequest, times(1)).getQueryString();
        assertEquals(expected, actual, "Wrong result");
    }

    @Test
    void testGetResponseStatus() {
        // Arrange
        final var $httpServletResponse = mock(HttpServletResponse.class);
        when($httpServletResponse.getStatus()).thenReturn(200);

        final var expected = HttpStatus.OK;

        // Act
        final var actual = LoggerInterceptor.INSTANCE.getResponseStatus($httpServletResponse);

        // Assert
        verify($httpServletResponse, times(1)).getStatus();
        assertEquals(expected, actual, () -> "Expecting a " + expected.name() + " return");
    }

    @Test
    void testGetResponseTimeMillis() {
        // Arrange
        final var $httpServletRequest = mock(HttpServletRequest.class);
        when($httpServletRequest.getAttribute(contains("request.Timestamp"))).thenReturn(1_000_000L);

        final var $loggerInterceptor = spy(LoggerInterceptor.INSTANCE);
        when($loggerInterceptor.currentNanoTime()).thenReturn(3_000_000L);

        final var expected = 2L;

        // Act
        final var actual = $loggerInterceptor.getResponseTimeInMillis($httpServletRequest);

        // Assert
        verify($httpServletRequest, times(1)).getAttribute(contains("request.Timestamp"));
        verify($loggerInterceptor, times(1)).currentNanoTime();
        assertEquals(expected, actual, "Wrong result");
    }

    @Test
    void testCurrentNanoTime() {
        // Arrange
        final var t0 = System.nanoTime();

        // Act
        final var t1 = LoggerInterceptor.INSTANCE.currentNanoTime();

        // Assert
        assertThat(t1)
            .withFailMessage("Expecting t1 (%d) to be greater than t0 (%d), but was not.", t1, t0)
            .isGreaterThan(t0);
    }
}
