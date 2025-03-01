package org.panda.systems.kakeibon.domain.repository.common;

import org.panda.systems.kakeibon.domain.model.common.FirstClass;
import org.panda.systems.kakeibon.domain.model.common.FirstClassEntityPkey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirstClassRepository
        extends JpaRepository<FirstClass, FirstClassEntityPkey> {

    FirstClass findByFirstClassId(Long firstClassId);

    @Override
    List<FirstClass> findAll();
}
