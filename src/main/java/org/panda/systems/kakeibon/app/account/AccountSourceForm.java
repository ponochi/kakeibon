package org.panda.systems.kakeibon.app.account;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import org.panda.systems.kakeibon.domain.model.account.AccountSource;
import org.panda.systems.kakeibon.domain.service.account.AccountSourceService;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Table(name = "account_info")
public class AccountSourceForm implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final AccountSourceService accountSourceService;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "account_info_seq", allocationSize = 1)
    @PositiveOrZero
    @Column(name = "account_id")
    private Long accountSourceId;

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
    public AccountSourceForm() {

        this.accountSourceService = null;
    }

    public AccountSourceForm(
            AccountSourceService accountSourceService) {

        this.accountSourceService = accountSourceService;
    }

    public Long getAccountSourceId() {
        return accountSourceId;
    }

    public void setAccountSourceId(Long accountSourceId) {
        this.accountSourceId = accountSourceId;
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

    public AccountSourceForm setAccountSourceFormByDB(Long accountSourceId) {

        if (accountSourceId == null) {
            this.setAccountSourceId(Long.parseLong("1"));
        } else {
            this.setAccountSourceId(accountSourceId);
        }
        AccountSource source
                = accountSourceService.findByAccountSourceId(this.getAccountSourceId());
        this.setAccountName(source.getAccountName());
        this.setBranchName(source.getBranchName());
        this.setEntryDate(source.getEntryDate());
        this.setUpdateDate(source.getUpdateDate());

        return this;
    }
}
