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
package io.bandrefilipe.brewer.persistence.config.datasource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author bandrefilipe
 * @since 1.0.0
 */
@SpringBootTest
class DatasourceConfigurationTest {

    private static final String SPRING_DATASOURCE_URL = "spring.datasource.url";
    private static final String SPRING_DATASOURCE_DRIVERCLASSNAME = "spring.datasource.driverClassName";
    private static final String SPRING_DATASOURCE_HIKARI_POOLNAME = "spring.datasource.hikari.poolName";
    private static final String SPRING_JPA_DATABASEPLATFORM = "spring.jpa.database-platform";

    @Autowired
    private Environment env;

    @Test
    void testConfigurationForNonNullity() {
        assertNotNull(env.getProperty(SPRING_DATASOURCE_URL), SPRING_DATASOURCE_URL + " is missing");
        assertNotNull(env.getProperty(SPRING_DATASOURCE_DRIVERCLASSNAME), SPRING_DATASOURCE_DRIVERCLASSNAME + " is missing");
        assertNotNull(env.getProperty(SPRING_DATASOURCE_HIKARI_POOLNAME), SPRING_DATASOURCE_HIKARI_POOLNAME + " is missing");
        assertNotNull(env.getProperty(SPRING_JPA_DATABASEPLATFORM), SPRING_JPA_DATABASEPLATFORM + " is missing");
    }
}
