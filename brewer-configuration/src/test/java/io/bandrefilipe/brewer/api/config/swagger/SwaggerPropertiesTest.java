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
package io.bandrefilipe.brewer.api.config.swagger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author bandrefilipe
 * @since 1.0.0
 */
@SpringBootTest
class SwaggerPropertiesTest {

    @Autowired
    private SwaggerProperties properties;

    @Test
    void testPropertiesForNonNullity() {
        assertNotNull(properties.getTitle(), "Title is missing");
        assertNotNull(properties.getVersion(), "Version is missing");
        assertNotNull(properties.getDescription(), "Description is missing");
        assertNotNull(properties.getContactName(), "Contact Name is missing");
        assertNotNull(properties.getContactUrl(), "Contact URL is missing");
        assertNotNull(properties.getContactEmail(), "Contact E-mail is missing");
        assertNotNull(properties.getLicense(), "License is missing");
        assertNotNull(properties.getLicenseUrl(), "License URL is missing");
    }
}
