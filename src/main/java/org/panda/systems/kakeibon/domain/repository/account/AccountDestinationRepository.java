package org.panda.systems.kakeibon.domain.repository.account;

import org.panda.systems.kakeibon.domain.model.account.AccountDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountDestinationRepository<S>
        extends JpaRepository<AccountDestination, Long> {

    @Override
    List<AccountDestination> findAll();

    S findByAccountDestinationId(Long accountId);

    @Override
    AccountDestination saveAndFlush(AccountDestination accountSource);
}
