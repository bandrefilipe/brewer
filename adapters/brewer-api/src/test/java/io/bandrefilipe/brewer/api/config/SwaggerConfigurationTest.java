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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springfox.documentation.spi.DocumentationType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author bandrefilipe
 * @since 2020-07-26
 */
class SwaggerConfigurationTest {

    private SwaggerProperties mockedSwaggerProperties;
    private SwaggerConfiguration objectUnderTest;

    @BeforeEach
    void setup() {
        mockedSwaggerProperties = mock(SwaggerProperties.class);
        objectUnderTest = spy(new SwaggerConfiguration(t -> true, mockedSwaggerProperties));
        setupSwaggerProperties();
    }

    private void setupSwaggerProperties() {
        when(mockedSwaggerProperties.getTitle()).thenReturn("Mocked Title");
        when(mockedSwaggerProperties.getVersion()).thenReturn("Mocked Version");
        when(mockedSwaggerProperties.getDescription()).thenReturn("Mocked Description");
        when(mockedSwaggerProperties.getContactName()).thenReturn("Mocked Contact Name");
        when(mockedSwaggerProperties.getContactUrl()).thenReturn("Mocked Contact URL");
        when(mockedSwaggerProperties.getContactEmail()).thenReturn("Mocked Contact Email");
        when(mockedSwaggerProperties.getLicense()).thenReturn("Mocked License");
        when(mockedSwaggerProperties.getLicenseUrl()).thenReturn("Mocked License URL");
    }

    @Test
    void docketMustNotBeNull() {
        assertNotNull(objectUnderTest.docket());
    }

    @Test
    void documentationTypeMustBeOAS30() {
        final var expected = DocumentationType.OAS_30;
        final var actual = objectUnderTest.docket().getDocumentationType();
        assertEquals(expected, actual);
    }

    @Test
    void apiInfoMustBeCalledOnyOnce() {
        objectUnderTest.docket();
        verify(objectUnderTest, times(1)).apiInfo();
    }

    @Test
    void apiInfoMustNotBeNull() {
        assertNotNull(objectUnderTest.apiInfo());
    }

    @Test
    void apiInfoMustUsePropertiesData() {
        final var actual = objectUnderTest.apiInfo();
        assertEquals("Mocked Title", actual.getTitle());
        assertEquals("Mocked Version", actual.getVersion());
        assertEquals("Mocked Description", actual.getDescription());
        assertEquals("Mocked License", actual.getLicense());
        assertEquals("Mocked License URL", actual.getLicenseUrl());
    }

    @Test
    void contactMustBeCalledOnlyOnce() {
        objectUnderTest.docket();
        verify(objectUnderTest, times(1)).contact();
    }

    @Test
    void contactMustNotBeNull() {
        assertNotNull(objectUnderTest.contact());
    }

    @Test
    void contactMustUsePropertiesData() {
        final var actual = objectUnderTest.contact();
        assertEquals("Mocked Contact Name", actual.getName());
        assertEquals("Mocked Contact URL", actual.getUrl());
        assertEquals("Mocked Contact Email", actual.getEmail());
    }

    @Test
    void allPropertiesMethodsMustBeCalledAtLeastOnce() {
        objectUnderTest.docket();
        verify(mockedSwaggerProperties, atLeastOnce()).getTitle();
        verify(mockedSwaggerProperties, atLeastOnce()).getVersion();
        verify(mockedSwaggerProperties, atLeastOnce()).getDescription();
        verify(mockedSwaggerProperties, atLeastOnce()).getContactName();
        verify(mockedSwaggerProperties, atLeastOnce()).getContactUrl();
        verify(mockedSwaggerProperties, atLeastOnce()).getContactEmail();
        verify(mockedSwaggerProperties, atLeastOnce()).getLicense();
        verify(mockedSwaggerProperties, atLeastOnce()).getLicenseUrl();
    }
}
