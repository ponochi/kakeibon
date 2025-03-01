package org.panda.systems.kakeibon.app.common;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.panda.systems.kakeibon.domain.model.common.BalanceType;
import org.panda.systems.kakeibon.domain.service.common.BalanceTypeService;

import java.io.Serial;
import java.io.Serializable;

@Table(name = "balance_type")
public class BalanceTypeForm implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final BalanceTypeService balanceTypeService;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "balance_type_seq", allocationSize = 1)
    @Column(name = "balance_type_id")
    private Long balanceTypeId;

    @NotEmpty
    @Column(name = "balance_type_name")
    private String balanceTypeName;

    // Default constructor
    public BalanceTypeForm() {

        this.balanceTypeService = null;
    }

    public BalanceTypeForm(BalanceTypeService balanceTypeService) {

        this.balanceTypeService = balanceTypeService;
        this.setBalanceTypeId(Long.parseLong("1"));
    }

    public Long getBalanceTypeId() {
        return balanceTypeId;
    }

    public void setBalanceTypeId(Long balanceTypeId) {
        this.balanceTypeId = balanceTypeId;
    }

    public String getBalanceTypeName() {
        return balanceTypeName;
    }

    public void setBalanceTypeName(String balanceTypeName) {
        this.balanceTypeName = balanceTypeName;
    }

    public BalanceTypeForm serBalanceTypeFormByDB(Long balanceTypeId) {

        if (balanceTypeId == null) {
            this.setBalanceTypeId(Long.parseLong("1"));
        } else {
            this.setBalanceTypeId(balanceTypeId);
        }
        BalanceType balanceType
                = balanceTypeService.findByBalanceTypeId(this.getBalanceTypeId());
        this.setBalanceTypeName(balanceType.getBalanceTypeName());

        return this;
    }
}
