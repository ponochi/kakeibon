package org.panda.systems.kakeibon.domain.model.common;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Component
@Entity
@Table(name = "tax_rate")
public class TaxRate implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "tax_rate_seq", allocationSize = 1)
    @Column(name = "tax_rate_id")
    private Long taxRateId;

    @Column(name = "tax_rate")
    private Long taxRate;

    // Default constructor
    public TaxRate() {
    }

    public Long getTaxRateId() {
        return taxRateId;
    }

    public void setTaxRateId(Long taxRateId) {
        this.taxRateId = taxRateId;
    }

    public Long getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Long taxRate) {
        this.taxRate = taxRate;
    }
}
