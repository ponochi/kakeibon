package org.panda.systems.kakeibon.domain.repository.currency;

import org.panda.systems.kakeibon.domain.model.currency.CurrencyList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyListRepository
        extends JpaRepository<CurrencyList, Long> {

    List<CurrencyList> findAll();

    CurrencyList findByCurrencyId(Long currencyId);

    CurrencyList saveAndFlush(CurrencyList entity);
}
