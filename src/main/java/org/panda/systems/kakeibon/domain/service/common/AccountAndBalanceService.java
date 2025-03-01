package org.panda.systems.kakeibon.domain.service.common;

import org.panda.systems.kakeibon.domain.model.account.AccountSource;
import org.panda.systems.kakeibon.domain.model.common.AccountAndBalance;
import org.panda.systems.kakeibon.domain.repository.common.AccountAndBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class AccountAndBalanceService implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Autowired
    AccountAndBalanceRepository accountRepository;

    public Long getMaxAccountAndBalanceId() {
        return accountRepository.getMaxAccountAndBalanceId();
    }

    public List<AccountAndBalance> findAll() {
        return accountRepository.findAll();
    }

    public AccountAndBalance findByAccountAndBalanceId(Long accountAndBalanceId) {
        AccountAndBalance accountAndBalance
                = (AccountAndBalance) accountRepository
                .findById(
                        accountAndBalanceId).orElse(null);
        if (accountAndBalance == null) {
            accountAndBalance = new AccountAndBalance();
            accountAndBalance.setAccountAndBalanceId(accountRepository.getMaxAccountAndBalanceId() + 1);
        }
        return accountAndBalance;
    }

    public AccountAndBalance saveAndFlush(AccountAndBalance entity) {
        return (AccountAndBalance) accountRepository.saveAndFlush(entity);
    }
}
