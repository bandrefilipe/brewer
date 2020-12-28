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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author bandrefilipe
 * @since 2020-10-04
 */
@SpringBootTest
class SwaggerPropertiesTest {

    @Autowired
    private SwaggerProperties properties;

    @Test
    void propertiesTitleMustNotBeNull() {
        assertNotNull(properties.getTitle(), "Title is missing");
    }

    @Test
    void propertiesVersionMustNotBeNull() {
        assertNotNull(properties.getVersion(), "Version is missing");
    }

    @Test
    void propertiesDescriptionMustNotBeNull() {
        assertNotNull(properties.getDescription(), "Description is missing");
    }

    @Test
    void propertiesContactNameMustNotBeNull() {
        assertNotNull(properties.getContactName(), "Contact Name is missing");
    }

    @Test
    void propertiesContactUrlMustNotBeNull() {
        assertNotNull(properties.getContactUrl(), "Contact URL is missing");
    }

    @Test
    void propertiesContactEmailMustNotBeNull() {
        assertNotNull(properties.getContactEmail(), "Contact E-mail is missing");
    }

    @Test
    void propertiesLicenseMustNotBeNull() {
        assertNotNull(properties.getLicense(), "License is missing");
    }

    @Test
    void propertiesLicenseUrlMustNotBeNull() {
        assertNotNull(properties.getLicenseUrl(), "License URL is missing");
    }
}
