package org.panda.systems.kakeibon.domain.service.common;

import org.panda.systems.kakeibon.domain.model.common.BalanceType;
import org.panda.systems.kakeibon.domain.repository.common.BalanceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Service
public class BalanceTypeService implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Autowired
    private BalanceTypeRepository balanceTypeRepository;

    public List<BalanceType> findAll() {
        return balanceTypeRepository.findAll();
    }

    public BalanceType findByBalanceTypeId(Long balanceId) {
        return balanceTypeRepository.findByBalanceTypeId(balanceId);
    }

}
