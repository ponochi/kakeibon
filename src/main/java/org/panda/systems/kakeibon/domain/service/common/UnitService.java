package org.panda.systems.kakeibon.domain.service.common;

import org.panda.systems.kakeibon.domain.model.common.Unit;
import org.panda.systems.kakeibon.domain.repository.common.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Service
public class UnitService implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    UnitRepository unitRepository;

    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    public Unit findByUnitId(Long unitId) {
        return (Unit) unitRepository.findByUnitId(unitId);
    }

    public Unit saveAndFlush(Unit entity) {

        return (Unit) unitRepository.saveAndFlush(entity);
    }
}
