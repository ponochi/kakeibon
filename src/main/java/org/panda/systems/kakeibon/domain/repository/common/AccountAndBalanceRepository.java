package org.panda.systems.kakeibon.domain.repository.common;

import org.panda.systems.kakeibon.domain.model.common.AccountAndBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountAndBalanceRepository<S>
        extends JpaRepository<AccountAndBalance, Long> {

    @SuppressWarnings("JpaQlInspection")
    @Query(nativeQuery = true,
            value = "SELECT" +
                    "   MAX(taab.account_and_balance_id) AS account_and_balance_id" +
                    " FROM" +
                    "   account_and_balance taab")
    Long getMaxAccountAndBalanceId();

    @Override
    List<AccountAndBalance> findAll();

    Optional<AccountAndBalance> findById(Long accountAndBalanceId);

    AccountAndBalance saveAndFlush(AccountAndBalance entity);
}
