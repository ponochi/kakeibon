package org.panda.systems.kakeibon.app.shop;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import org.panda.systems.kakeibon.domain.model.common.Shop;
import org.panda.systems.kakeibon.domain.service.common.ShopService;
import org.panda.systems.kakeibon.utils.common.Converter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Table(name = "shop")
public class ShopForm implements Serializable, Converter {
    @Serial
    private static final long serialVersionUID = 1L;

    private final ShopService shopService;

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
    @Column
    private ZonedDateTime entryDate;

    @Column
    private ZonedDateTime updateDate;

    // Default constructor
    public ShopForm() {

        this.shopService = null;
    }

    public ShopForm(ShopService shopService) {

        this.shopService = shopService;
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

    public ShopForm setShopFormByDB(Long shopId) {

        if (shopId == null) {
            shopId = Long.parseLong("1");
        } else {
            this.setShopId(shopId);
        }
        Shop shop = shopService.findById(this.getShopId());
        this.setShopName(shop.getShopName());
        this.setBranchName(shop.getBranchName());
        this.setShopUrl(shop.getShopUrl());
        this.setPhoneNumber(shop.getPhoneNumber());
        this.setEmail(shop.getEmail());
        this.setOpenShopDate(shop.getOpenShopDate());
        this.setCloseShopDate(shop.getCloseShopDate());
        this.setShopMemo(shop.getShopMemo());
        this.setEntryDate(convertUTCtoJST(shop.getEntryDate()));
        this.setUpdateDate(convertUTCtoJST(shop.getUpdateDate()));

        return this;
    }

    public ShopForm setShopToForm(Shop shop) {
        ShopForm form = new ShopForm(shopService);

        form.setShopId(shop.getShopId());
        form.setShopName(shop.getShopName());
        form.setBranchName(shop.getBranchName());
        form.setShopUrl(shop.getShopUrl());
        form.setPhoneNumber(shop.getPhoneNumber());
        form.setEmail(shop.getEmail());
        form.setOpenShopDate(shop.getOpenShopDate());
        form.setCloseShopDate(shop.getCloseShopDate());
        form.setShopMemo(shop.getShopMemo());
        form.setEntryDate(shop.getEntryDate());
        form.setUpdateDate(shop.getUpdateDate());

        return form;
    }
}
