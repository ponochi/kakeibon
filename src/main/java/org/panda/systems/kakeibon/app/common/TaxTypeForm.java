package org.panda.systems.kakeibon.app.common;

import jakarta.persistence.*;
import org.panda.systems.kakeibon.domain.model.common.TaxType;
import org.panda.systems.kakeibon.domain.service.common.TaxTypeService;

import java.io.Serial;
import java.io.Serializable;

@Table(name = "tax_type")
public class TaxTypeForm implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final TaxTypeService taxTypeService;

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @SequenceGenerator(name = "tax_type_seq", allocationSize = 1)
    @Column(name = "tax_type_id")
    private Long taxTypeId;

    @Column(name = "tax_type_name")
    private String taxTypeName;

    // Default constructor
    public TaxTypeForm() {

        this.taxTypeService = null;
    }

    public TaxTypeForm(TaxTypeService taxTypeService) {

        this.taxTypeService = taxTypeService;
    }

    public Long getTaxTypeId() {
        return taxTypeId;
    }

    public void setTaxTypeId(Long taxTypeId) {
        this.taxTypeId = taxTypeId;
    }

    public String getTaxTypeName() {
        return taxTypeName;
    }

    public void setTaxTypeName(String taxTypeName) {
        this.taxTypeName = taxTypeName;
    }

    public TaxTypeForm setTaxTypeFormByDB(Long taxTypeId) {

        TaxType taxType = taxTypeService.findByTaxTypeId(taxTypeId);

        this.setTaxTypeId(taxType.getTaxTypeId());
        this.setTaxTypeName(taxType.getTaxTypeName());

        return this;
    }
}
