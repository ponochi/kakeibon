package org.panda.systems.kakeibon.app.common;

import jakarta.persistence.*;
import org.panda.systems.kakeibon.domain.model.common.TaxRate;
import org.panda.systems.kakeibon.domain.service.common.TaxRateService;

import java.io.Serial;
import java.io.Serializable;

@Table(name = "tax_rate")
public class TaxRateForm implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final TaxRateService taxRateService;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "tax_rate_seq", allocationSize = 1)
    @Column(name = "tax_rate_id")
    private Long taxRateId;

    @Column(name = "tax_rate")
    private Long taxRate;

    // Default constructor
    public TaxRateForm() {

        this.taxRateService = null;
    }

    public TaxRateForm(TaxRateService taxRateService) {

        this.taxRateService = taxRateService;
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

    public TaxRateForm setTaxRateFormByDB(Long taxRateId) {

        TaxRate taxRate = taxRateService.findByTaxRateId(taxRateId);

        this.setTaxRateId(taxRate.getTaxRateId());
        this.setTaxRate(taxRate.getTaxRate());

        return this;
    }
}
