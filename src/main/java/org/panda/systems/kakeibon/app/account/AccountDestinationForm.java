package org.panda.systems.kakeibon.app.account;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import org.panda.systems.kakeibon.domain.model.account.AccountDestination;
import org.panda.systems.kakeibon.domain.service.account.AccountDestinationService;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Component
public class AccountDestinationForm implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final AccountDestinationService accountDestinationService;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "tblAccountSeq", allocationSize = 1)
    @PositiveOrZero
    @Column(name = "account_id")
    private Long accountDestinationId;

    @NotEmpty
    @Column(name = "account_name")
    private String accountName;

    @Column(name = "branch_name")
    private String branchName;

    @PastOrPresent
    @Column(name = "entry_date")
    private ZonedDateTime entryDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    // Default constructor
    public AccountDestinationForm() {

        this.accountDestinationService = null;
    }

    public Long getAccountDestinationId() {
        return accountDestinationId;
    }

    public void setAccountDestinationId(Long accountDestinationId) {
        this.accountDestinationId = accountDestinationId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
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

    public AccountDestinationForm(
            AccountDestinationService accountDestinationService) {

        this.accountDestinationService = accountDestinationService;
    }

    public AccountDestinationForm setAccountDestinationFormByDB(Long accountDestinationId) {

        if (accountDestinationId == null) {
            this.setAccountDestinationId(Long.parseLong("1"));
        } else {
            this.setAccountDestinationId(accountDestinationId);
        }
        AccountDestination destination
                = (AccountDestination) accountDestinationService
                .findByAccountDestinationId(this.getAccountDestinationId());
        this.setAccountName(destination.getAccountName());
        this.setBranchName(destination.getBranchName());
        this.setEntryDate(destination.getEntryDate());
        this.setUpdateDate(destination.getUpdateDate());

        return this;
    }
}
