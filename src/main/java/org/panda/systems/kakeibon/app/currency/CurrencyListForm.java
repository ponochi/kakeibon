package org.panda.systems.kakeibon.app.currency;

import jakarta.persistence.*;
import org.panda.systems.kakeibon.domain.model.currency.CurrencyList;
import org.panda.systems.kakeibon.domain.service.currency.CurrencyListService;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Component
@Table(name = "currency_list")
public class CurrencyListForm implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final CurrencyListService currencyListService;

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @SequenceGenerator(name = "currency_list_seq", allocationSize = 1)
    @Column(name = "currency_id")
    private Long currencyId;

    @Column(name = "currency_name")
    private String currencyName;

    public CurrencyListForm() {

        this.currencyListService = null;
    }

    public CurrencyListForm(CurrencyListService currencyListService) {

        this.currencyListService = currencyListService;
    }

    public CurrencyListForm(CurrencyListService currencyListService,
                            Long currencyId) {
        this.currencyListService = currencyListService;
        CurrencyList currency = currencyListService.findById(currencyId);

        this.setCurrencyId(currency.getCurrencyId());
        this.setCurrencyName(currency.getCurrencyName());
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public CurrencyListForm setCurrencyListToForm(CurrencyList currencyList) {
        this.setCurrencyId(currencyList.getCurrencyId());
        this.setCurrencyName(currencyList.getCurrencyName());

        return this;
    }
}
