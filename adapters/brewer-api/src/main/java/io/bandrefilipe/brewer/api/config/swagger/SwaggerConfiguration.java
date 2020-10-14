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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

/**
 * @author bandrefilipe
 * @since 1.0.0
 */
@Configuration
class SwaggerConfiguration {

    private final Predicate<RequestHandler> requestHandler;
    private final SwaggerProperties properties;

    @Autowired
    SwaggerConfiguration(final Predicate<RequestHandler> requestHandler,
                         final SwaggerProperties properties) {
        this.requestHandler = requestHandler;
        this.properties = properties;
    }

    @Bean
    Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(requestHandler)
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.apiInfo());
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(properties.getTitle())
                .version(properties.getVersion())
                .description(properties.getDescription())
                .contact(this.contact())
                .license(properties.getLicense())
                .licenseUrl(properties.getLicenseUrl())
                .build();
    }

    Contact contact() {
        return new Contact(
                properties.getContactName(),
                properties.getContactUrl(),
                properties.getContactEmail());
    }
}
