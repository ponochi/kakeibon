package org.panda.systems.kakeibon.domain.model.spec;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "specification")
@SecondaryTable(name = "specification_group",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "specification_group_id"))
@SecondaryTable(name = "users",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id"))
@SecondaryTable(name = "currency_list",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "currency_id"))
@SecondaryTable(name = "unit",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "unit_id"))
@SecondaryTable(name = "tax_type",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "tax_type_id"))
@SecondaryTable(name = "tax_rate",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "tax_rate_id"))
public class Specification implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "specification_group_id")
    private Long specificationGroupId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "specification_seq", allocationSize = 1)
    @Column(name = "specification_id")
    private Long specificationId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "price")
    private Long price;

    @Column(name = "currency_id")
    private Long currencyId;

    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "tax_type_id")
    private Long taxTypeId;

    @Column(name = "tax_rate_id")
    private Long taxRateId;

    @Column(name = "tax")
    private Long tax;

    @Column(name = "spec_memo")
    @Size(max = 1000, message = "メモは1000文字以内で入力してください")
    private String specMemo;

    @Column(name = "deleted")
    private Boolean deleted;

    @PastOrPresent
    @Column(name = "entry_date")
    private ZonedDateTime entryDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    // Default constructor
    public Specification() {
    }

    public Long getSpecificationGroupId() {
        return specificationGroupId;
    }

    public void setSpecificationGroupId(Long specificationGroupId) {
        this.specificationGroupId = specificationGroupId;
    }

    public Long getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Long specificationId) {
        this.specificationId = specificationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getTaxTypeId() {
        return taxTypeId;
    }

    public void setTaxTypeId(Long taxTypeId) {
        this.taxTypeId = taxTypeId;
    }

    public Long getTaxRateId() {
        return taxRateId;
    }

    public void setTaxRateId(Long taxRateId) {
        this.taxRateId = taxRateId;
    }

    public Long getTax() {
        return tax;
    }

    public void setTax(Long tax) {
        this.tax = tax;
    }

    public String getSpecMemo() {
        return specMemo;
    }

    public void setSpecMemo(String specMemo) {
        this.specMemo = specMemo;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public ZonedDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(ZonedDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
