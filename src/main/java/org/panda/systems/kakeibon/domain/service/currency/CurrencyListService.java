package org.panda.systems.kakeibon.domain.service.currency;

import org.panda.systems.kakeibon.domain.model.currency.CurrencyList;
import org.panda.systems.kakeibon.domain.repository.currency.CurrencyListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Service
public class CurrencyListService implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Autowired
    CurrencyListRepository currencyListRepository;

    public List<CurrencyList> findAll() {
        return currencyListRepository.findAll(
                Sort.by(Sort.Direction.ASC,
                        "CurrencyId")
        );
    }

    public CurrencyList findById(Long currencyId) {
        return (CurrencyList) currencyListRepository.findByCurrencyId(currencyId);
    }

    public CurrencyList saveAndFlush(CurrencyList entity) {

        return (CurrencyList) currencyListRepository.saveAndFlush(entity);
    }
}
