package org.panda.systems.kakeibon.domain.service.common;

import org.panda.systems.kakeibon.domain.model.common.FirstClass;
import org.panda.systems.kakeibon.domain.repository.common.FirstClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Service
public class FirstClassService implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final FirstClassRepository firstClassRepository;

    public FirstClassService() {
        this.firstClassRepository = null;
    }

    @Autowired
    public FirstClassService(
            FirstClassRepository firstClassRepository) {
        this.firstClassRepository = firstClassRepository;
    }


    public FirstClass findByClassId(Long firstClassId) {
        return Objects.requireNonNull(firstClassRepository)
                .findByFirstClassId(firstClassId);
    }

    public List<FirstClass> findAll() {
        return Objects.requireNonNull(firstClassRepository)
                .findAll();
    }
}
