package org.panda.systems.kakeibon.domain.repository.common;

import org.panda.systems.kakeibon.domain.model.common.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository<S> extends JpaRepository<Unit, Long> {

    @Override
    List<Unit> findAll();

    S findByUnitId(Long unitId);

    Unit saveAndFlush(Unit unit);
}
