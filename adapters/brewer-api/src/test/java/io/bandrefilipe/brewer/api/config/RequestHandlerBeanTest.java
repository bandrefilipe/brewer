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

import io.bandrefilipe.brewer.api.controller.RestControllerPackageMarker;
import io.bandrefilipe.brewer.application.ports.in.BeerService;
import io.bandrefilipe.brewer.commons.identifier.Identifiable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ClassFilter;
import org.junit.platform.commons.util.ReflectionUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.RequestHandler;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * @author bandrefilipe
 * @since 2020-12-05
 */
class RequestHandlerBeanTest {

    private static final RequestHandlerBean objectUnderTest = new RequestHandlerBean();

    private final Set<RequestHandler> validRequestHandlers = new HashSet<>();
    private final Set<RequestHandler> invalidRequestHandlers = new HashSet<>();
    private final Set<RequestHandler> mixedRequestHandlers = new HashSet<>();

    @SuppressWarnings("deprecation")
    private static RequestHandler mockRequestHandlerFor(final Class<?> aClass) {
        final var mockedRequestHandler = mock(RequestHandler.class);
        doReturn(aClass)
                .when(mockedRequestHandler)
                .declaringClass();
        return mockedRequestHandler;
    }

    @BeforeEach
    void setup() {
        loadValidRequestHandlers();
        loadInvalidRequestHandlers();
        loadMixedRequestHandlers();
    }

    @AfterEach
    void tearDown() {
        validRequestHandlers.clear();
        invalidRequestHandlers.clear();
        mixedRequestHandlers.clear();
    }

    private void loadValidRequestHandlers() {
        ReflectionUtils
                .findAllClassesInPackage(
                        RestControllerPackageMarker.class.getPackageName(),
                        ClassFilter.of(aClass -> aClass.isAnnotationPresent(RestController.class)))
                .stream()
                .map(RequestHandlerBeanTest::mockRequestHandlerFor)
                .forEach(validRequestHandlers::add);
    }

    private void loadInvalidRequestHandlers() {
        invalidRequestHandlers.addAll(asList(
                mockRequestHandlerFor(BeerService.class),
                mockRequestHandlerFor(Identifiable.class),
                mockRequestHandlerFor(SpringBootApplication.class),
                mockRequestHandlerFor(RestController.class),
                mockRequestHandlerFor(String.class),
                mockRequestHandlerFor(BigInteger.class)
        ));
    }

    private void loadMixedRequestHandlers() {
        mixedRequestHandlers.addAll(validRequestHandlers);
        mixedRequestHandlers.addAll(invalidRequestHandlers);
    }

    @Test
    void defaultPredicateAlwaysSucceeds() {
        for (var requestHandler : mixedRequestHandlers) {
            assertTrue(objectUnderTest
                    .defaultRequestHandler()
                    .test(requestHandler)
            );
        }
    }

    @Test
    void prodPredicateFailsForInvalidRequestHandlers() {
        for (var requestHandler : invalidRequestHandlers) {
            assertFalse(objectUnderTest
                    .prodRequestHandler()
                    .test(requestHandler)
            );
        }
    }

    @Test
    void prodPredicateSucceedsForValidRequestHandlers() {
        for (var requestHandler : validRequestHandlers) {
            assertTrue(objectUnderTest
                    .prodRequestHandler()
                    .test(requestHandler)
            );
        }
    }
}
