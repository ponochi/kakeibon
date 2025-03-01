package org.panda.systems.kakeibon.domain.model.spec;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "specification_group")
@SecondaryTable(name = "users",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id"))
@SecondaryTable(name = "balance_type",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "balance_type_id"))
@SecondaryTable(name = "account_and_balance",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "account_and_balance_id"))
public class SpecificationGroup implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "specification_group_seq", allocationSize = 1)
    @Column(name = "specification_group_id")
    private Long specificationGroupId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "shop_id")
    private Long shopId;

    @PastOrPresent
    @Column
    private LocalDate receivingAndPaymentDate;

    @Column
    private LocalTime receivingAndPaymentTime;

    @Column(name = "balance_type_id")
    private Long balanceTypeId;

    @Column(name = "account_and_balance_id")
    private Long accountAndBalanceId;

    @Column(name = "group_memo")
    private String groupMemo;

    @Column(name = "deleted")
    private Boolean deleted;

    @PastOrPresent
    @Column(name = "entry_date")
    private ZonedDateTime entryDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    // Default constructor
    public SpecificationGroup() {
    }

    public Long getSpecificationGroupId() {
        return specificationGroupId;
    }

    public void setSpecificationGroupId(Long specificationGroupId) {
        this.specificationGroupId = specificationGroupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public LocalDate getReceivingAndPaymentDate() {
        return receivingAndPaymentDate;
    }

    public void setReceivingAndPaymentDate(LocalDate receivingAndPaymentDate) {
        this.receivingAndPaymentDate = receivingAndPaymentDate;
    }

    public LocalTime getReceivingAndPaymentTime() {
        return receivingAndPaymentTime;
    }

    public void setReceivingAndPaymentTime(LocalTime receivingAndPaymentTime) {
        this.receivingAndPaymentTime = receivingAndPaymentTime;
    }

    public Long getBalanceTypeId() {
        return balanceTypeId;
    }

    public void setBalanceTypeId(Long balanceTypeId) {
        this.balanceTypeId = balanceTypeId;
    }

    public Long getAccountAndBalanceId() {
        return accountAndBalanceId;
    }

    public void setAccountAndBalanceId(Long accountAndBalanceId) {
        this.accountAndBalanceId = accountAndBalanceId;
    }

    public String getGroupMemo() {
        return groupMemo;
    }

    public void setGroupMemo(String groupMemo) {
        this.groupMemo = groupMemo;
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
