package org.panda.systems.kakeibon.domain.model.account;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Component
@Entity
@Table(name = "account_info")
public class AccountDestination implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "account_info_seq", allocationSize = 1)
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
    public AccountDestination() {
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
}
