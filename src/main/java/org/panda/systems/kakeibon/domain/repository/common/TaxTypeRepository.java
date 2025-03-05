package org.panda.systems.kakeibon.domain.repository.common;

import org.panda.systems.kakeibon.domain.model.common.TaxType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxTypeRepository<S>
        extends JpaRepository<TaxType, Long> {

    @Override
    List<TaxType> findAll();

    S findByTaxTypeId(Long taxTypeId);

    TaxType saveAndFlush(TaxType taxType);
}