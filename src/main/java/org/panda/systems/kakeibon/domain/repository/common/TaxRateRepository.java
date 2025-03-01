package org.panda.systems.kakeibon.domain.repository.common;

import org.panda.systems.kakeibon.domain.model.common.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxRateRepository
        extends JpaRepository<TaxRate, Long> {

    @SuppressWarnings("NullableProblems")
    @Override
    List<TaxRate> findAll();

    TaxRate findByTaxRateId(Long taxRateId);

    @SuppressWarnings({"unchecked", "NullableProblems"})
    TaxRate saveAndFlush(TaxRate taxRate);
}