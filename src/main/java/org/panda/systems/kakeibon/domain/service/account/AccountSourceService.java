package org.panda.systems.kakeibon.domain.service.account;

import org.panda.systems.kakeibon.domain.model.account.AccountSource;
import org.panda.systems.kakeibon.domain.repository.account.AccountSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Service
public class AccountSourceService implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Autowired
    private AccountSourceRepository accountSourceRepository;

    public List<AccountSource> findAll() {
        return accountSourceRepository.findAll();
    }

    public AccountSource findByAccountSourceId(Long accountSourceId) {
        return (AccountSource) accountSourceRepository.findByAccountSourceId(accountSourceId);
    }

    public AccountSource saveAndFlush(AccountSource accountSource) {
        return (AccountSource) accountSourceRepository.saveAndFlush(accountSource);
    }
}
