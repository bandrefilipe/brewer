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
package io.bandrefilipe.brewer.persistence.model;

import io.bandrefilipe.brewer.commons.identifier.Identifiable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author bandrefilipe
 * @since 1.0.0
 */
@Entity
@Table(name = "beer")
@NoArgsConstructor @Getter @Setter
class BeerEntity implements Identifiable<Long> {

    @Id
    @Column(name = "beer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The Business Key */
    @NaturalId
    @Column(name = "sku", unique = true, nullable = false, updatable = false)
    private String sku;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    /** Max possible value: 999999.99 */
    @Column(name = "unit_price", precision = 8, scale = 2)
    private BigDecimal unitPrice;

    /** Max possible value: 999.99 | Max expected value: 100.00 */
    @Column(name = "alcohol_content", precision = 5, scale = 2)
    private BigDecimal alcoholContent;

    /** Max possible value: 999.99 | Max expected value: 100.00 */
    @Column(name = "commission", precision = 5, scale = 2)
    private BigDecimal commission;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "origin", length = 1)
    @Convert(converter = OriginConverter.class)
    private Origin origin;

    public String getSimpleNaturalId() {
        return this.getSku();
    }

    public int hashCode() {
        return Objects.hashCode(sku);
    }

    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        BeerEntity that = (BeerEntity) other;
        return Objects.equals(this.sku, that.sku);
    }

    @Getter
    enum Origin {
        DOMESTIC("D"),
        IMPORTED("I");

        private final String code;

        Origin(final String code) {
            this.code = code;
        }

        static Origin getByCode(final String code) {
            if (code == null) {
                return null;
            }
            switch (code) {
                case "D": return DOMESTIC;
                case "I": return IMPORTED;
                default:  throw new IllegalArgumentException(code);
            }
        }
    }
}
