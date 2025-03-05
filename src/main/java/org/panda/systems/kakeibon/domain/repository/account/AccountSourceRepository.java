package org.panda.systems.kakeibon.domain.repository.account;

import org.panda.systems.kakeibon.domain.model.account.AccountSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountSourceRepository<S> extends JpaRepository<AccountSource, Long> {

    List<AccountSource> findAll();

    S findByAccountSourceId(Long accountSourceId);

    AccountSource saveAndFlush(AccountSource accountSource);
}
