package org.panda.systems.kakeibon.domain.model.common;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Component
@Entity
@Table(name = "shop")
public class Shop implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "shop_seq", allocationSize = 1)
    @Column(name = "shop_id")
    private Long shopId;

    @NotEmpty
    @Column
    private String shopName;

    @NotEmpty
    @Column
    private String branchName;

    @Column
    private String shopUrl;

    @Column
    private String phoneNumber;

    @Column
    private String email;

    @Column
    private LocalDate openShopDate;

    @Column
    private LocalDate closeShopDate;

    @Column
    private String shopMemo;

    @PastOrPresent
    @Column(name = "entry_date")
    private ZonedDateTime entryDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    // Default constructor
    public Shop() {
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getOpenShopDate() {
        return openShopDate;
    }

    public void setOpenShopDate(LocalDate openShopDate) {
        this.openShopDate = openShopDate;
    }

    public LocalDate getCloseShopDate() {
        return closeShopDate;
    }

    public void setCloseShopDate(LocalDate closeShopDate) {
        this.closeShopDate = closeShopDate;
    }

    public String getShopMemo() {
        return shopMemo;
    }

    public void setShopMemo(String shopMemo) {
        this.shopMemo = shopMemo;
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

