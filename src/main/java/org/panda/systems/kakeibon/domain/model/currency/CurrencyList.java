package org.panda.systems.kakeibon.domain.model.currency;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Component
@Entity
@Table(name = "currency_list")
public class CurrencyList implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "currency_list_seq", allocationSize = 1)
    @Column(name = "currency_id")
    private Long currencyId;

    @Column(name = "currency_name")
    @Size(min = 3, max = 3, message = "通貨名には半角アルファベット３文字を入力してください")
    private String currencyName;

    // Default constructor
    public CurrencyList() {
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
}
