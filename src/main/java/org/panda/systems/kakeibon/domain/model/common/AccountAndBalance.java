package org.panda.systems.kakeibon.domain.model.common;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Component
@Entity
@Table(name = "account_and_balance")
public class AccountAndBalance implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "account_and_balance_seq", allocationSize = 1)
    @Column(name = "account_and_balance_id")
    private Long accountAndBalanceId;

    @Column(name = "account_source_id")
    private Long accountSourceId;

    @Column(name = "account_destination_id")
    private Long accountDestinationId;

    @Column(name = "entry_date")
    private ZonedDateTime entryDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    // Default constructor
    public AccountAndBalance() {
    }

    public Long getAccountAndBalanceId() {
        return accountAndBalanceId;
    }

    public void setAccountAndBalanceId(Long accountAndBalanceId) {
        this.accountAndBalanceId = accountAndBalanceId;
    }

    public Long getAccountSourceId() {
        return accountSourceId;
    }

    public void setAccountSourceId(Long accountSourceId) {
        this.accountSourceId = accountSourceId;
    }

    public Long getAccountDestinationId() {
        return accountDestinationId;
    }

    public void setAccountDestinationId(Long accountDestinationId) {
        this.accountDestinationId = accountDestinationId;
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
