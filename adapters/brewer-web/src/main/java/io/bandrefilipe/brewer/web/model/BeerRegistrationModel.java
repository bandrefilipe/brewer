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
package io.bandrefilipe.brewer.web.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author bandrefilipe
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
public final class BeerRegistrationModel {

    private static final int MAX_DESCRIPTION_LENGTH_VALUE = 140;
    private static final String MAX_DESCRIPTION_LENGTH_MESSAGE = "description must have up to " + MAX_DESCRIPTION_LENGTH_VALUE + " characters";
    private static final long MAX_ALCOHOL_CONTENT_VALUE = 100L;
    private static final String MAX_ALCOHOL_CONTENT_MESSAGE = "alcohol content must be between 0% and " + MAX_ALCOHOL_CONTENT_VALUE + "%";

    @NotBlank(message = "sku is required")
    private String sku;

    @NotBlank(message = "name is required")
    private String name;

    @Size(
        max = MAX_DESCRIPTION_LENGTH_VALUE,
        message = MAX_DESCRIPTION_LENGTH_MESSAGE
    )
    private String description;

    @ToString.Exclude
    @Setter(AccessLevel.PRIVATE)
    private int descriptionMaxLength = MAX_DESCRIPTION_LENGTH_VALUE;

    private String type;

    private String flavor;

    @NotNull(message = "alcohol content is required")
    @Range(
        max = MAX_ALCOHOL_CONTENT_VALUE,
        message = MAX_ALCOHOL_CONTENT_MESSAGE
    )
    private BigDecimal alcoholContent;

    @NotBlank(message = "origin is required")
    private String origin;

    @NotNull(message = "unit price is required")
    @Positive(message = "unit price must be a positive value")
    private BigDecimal unitPrice;

    @NotNull(message = "commission is required")
    @PositiveOrZero(message = "commission must not be a negative value")
    private BigDecimal commission;

    @NotNull(message = "stock is required")
    @PositiveOrZero(message = "stock must not be a negative value")
    private Integer stock;
}
