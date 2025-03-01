package org.panda.systems.kakeibon.domain.service.common;

import org.panda.systems.kakeibon.domain.model.common.TaxRate;
import org.panda.systems.kakeibon.domain.repository.common.TaxRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Service
public class TaxRateService implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Autowired
    TaxRateRepository taxRateRepository;

    public List<TaxRate> findAll() {
        return taxRateRepository.findAll();
    }

    public TaxRate findByTaxRateId(Long taxRateId) {
        return (TaxRate) taxRateRepository.findByTaxRateId((Long) taxRateId);
    }

    public TaxRate saveAndFlush(TaxRate entity) {
        return taxRateRepository.saveAndFlush((TaxRate) entity);
    }
}
