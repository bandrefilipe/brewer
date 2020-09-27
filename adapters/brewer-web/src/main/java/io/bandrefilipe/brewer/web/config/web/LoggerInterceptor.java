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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author bandrefilipe
 * @since 1.0.0
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class LoggerInterceptor extends HandlerInterceptorAdapter {

    public static final LoggerInterceptor INSTANCE = new LoggerInterceptor();

    private static final String REQUEST_ATTRIBUTE_TIMESTAMP = "io.bandrefilipe.brewer.web.request.Timestamp";
    private static final long NANO_TO_MILLISECOND_DIVISOR = 1_000_000L;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        setRequestMetadata(request);
        if (log.isInfoEnabled())
            log.info("[preHandle] {} \"{}\" mapped to {}", request.getMethod(), getRequestURI(request), handler);
        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request,
                           final HttpServletResponse response,
                           final Object handler,
                           final ModelAndView modelAndView) throws Exception {
        if (log.isInfoEnabled())
            log.info("[postHandle] Completed with status {} in {} ms", getResponseStatus(response), getResponseTimeInMillis(request));
    }

    private void setRequestMetadata(final HttpServletRequest request) {
        request.setAttribute(REQUEST_ATTRIBUTE_TIMESTAMP, System.nanoTime());
    }

    private Object getRequestURI(final HttpServletRequest request) {
        final var requestURI = request.getRequestURI();
        final var queryString = request.getQueryString();
        return queryString != null
                ? requestURI + "?" + queryString
                : requestURI;
    }

    private Object getResponseStatus(final HttpServletResponse response) {
        return HttpStatus.valueOf(response.getStatus());
    }

    private long getResponseTimeInMillis(final HttpServletRequest request) {
        final var start = (long) request.getAttribute(REQUEST_ATTRIBUTE_TIMESTAMP);
        final var end = System.nanoTime();
        return (end - start) / NANO_TO_MILLISECOND_DIVISOR;
    }
}