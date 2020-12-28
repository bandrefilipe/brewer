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
package io.bandrefilipe.brewer.api;

import io.bandrefilipe.brewer.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.lang.NonNull;

import java.net.URI;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * @author bandrefilipe
 * @since 2020-10-30
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
abstract class AbstractRestAPITest {

    private static final Logger log = LoggerFactory.getLogger(AbstractRestAPITest.class);

    @LocalServerPort
    private int localServerPort;

    @Autowired
    protected TestRestTemplate restTemplate;

    private static boolean not(final boolean bool) {
        return !bool;
    }

    protected URI buildUri(@NonNull final ApiVersion version,
                           @NonNull final String URN) {
        log.debug("Creating URI for ApiVersion={} URN={}", version, URN);

        final var builder = buildUriPrefix(version);
        final var uriString = buildUriSuffix(builder, URN).toString();
        final var uri = URI.create(uriString);

        log.debug("URI \"{}\" created", uri);
        return uri;
    }

    private StringBuilder buildUriPrefix(@NonNull final ApiVersion version) {
        return new StringBuilder()
                .append("http://localhost:")
                .append(localServerPort)
                .append(version.getValue());
    }

    private StringBuilder buildUriSuffix(@NonNull final StringBuilder builder,
                                         @NonNull final String URN) {
        final var suffix = Strings.trimToEmpty(URN);
        if (not(suffix.startsWith("/"))) {
            builder.append("/");
        }
        return builder.append(suffix);
    }

    protected enum ApiVersion {
        V1("/api/v1");

        private final String value;

        ApiVersion(final String value) {
            this.value = value;
        }

        private String getValue() {
            return this.value;
        }
    }
}
