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
package io.bandrefilipe.brewer.web.config.swagger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author bandrefilipe
 * @since 1.0.0
 */
class SwaggerConfigurationTest {

    @Test
    void testDocketCreation() {
        // Arrange
        final var $swaggerProperties = mock(SwaggerProperties.class);
        final var $swaggerConfiguration = spy(new SwaggerConfiguration(t -> true, $swaggerProperties));

        // Act
        final var actual = $swaggerConfiguration.docket();

        // Assert
        assertNotNull(actual, "Docket is missing");
        verify($swaggerConfiguration, times(1)).apiInfo();
    }

    @Test
    void testApiInfoCreation() {
        // Arrange
        final var $swaggerProperties = mock(SwaggerProperties.class);
        when($swaggerProperties.getTitle()).thenReturn("Mocked Title");
        when($swaggerProperties.getVersion()).thenReturn("Mocked Version");
        when($swaggerProperties.getDescription()).thenReturn("Mocked Description");
        when($swaggerProperties.getLicense()).thenReturn("Mocked License");
        when($swaggerProperties.getLicenseUrl()).thenReturn("Mocked License URL");

        final var $swaggerConfiguration = spy(new SwaggerConfiguration(null, $swaggerProperties));

        // Act
        final var actual = $swaggerConfiguration.apiInfo();

        // Assert
        assertEquals("Mocked Title", actual.getTitle(), "Wrong Title");
        assertEquals("Mocked Version", actual.getVersion(), "Wrong Version");
        assertEquals("Mocked Description", actual.getDescription(), "Wrong Description");
        assertEquals("Mocked License", actual.getLicense(), "Wrong License");
        assertEquals("Mocked License URL", actual.getLicenseUrl(), "Wrong License URL");
        verify($swaggerConfiguration, times(1)).contact();
    }

    @Test
    void testContactCreation() {
        // Arrange
        final var $swaggerProperties = mock(SwaggerProperties.class);
        when($swaggerProperties.getContactName()).thenReturn("Mocked Contact Name");
        when($swaggerProperties.getContactUrl()).thenReturn("Mocked Contact URL");
        when($swaggerProperties.getContactEmail()).thenReturn("Mocked Contact Email");

        final var swaggerConfiguration = new SwaggerConfiguration(null, $swaggerProperties);

        // Act
        final var actual = swaggerConfiguration.contact();

        // Assert
        assertEquals("Mocked Contact Name", actual.getName(), "Wrong Name");
        assertEquals("Mocked Contact URL", actual.getUrl(), "Wrong URL");
        assertEquals("Mocked Contact Email", actual.getEmail(), "Wrong Email");
    }
}
