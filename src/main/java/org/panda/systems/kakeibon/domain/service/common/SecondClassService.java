package org.panda.systems.kakeibon.domain.service.common;

import org.panda.systems.kakeibon.domain.model.common.SecondClass;
import org.panda.systems.kakeibon.domain.repository.common.SecondClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Service
public class SecondClassService implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final SecondClassRepository secondClassRepository;

    public SecondClassService() {
        this.secondClassRepository = null;
    }

    @Autowired
    public SecondClassService(
            SecondClassRepository secondClassRepository) {
        this.secondClassRepository = secondClassRepository;
    }

    public SecondClass getSecondClassificationBySecondClassId(Long secondClassId) {
        return Objects.requireNonNull(secondClassRepository)
                .getSecondClassificationBySecondClassId(
                        secondClassId);
    }

    public List<SecondClass> getSecondClassificationsByFirstClassId(Long firstClassId) {
        return Objects.requireNonNull(secondClassRepository)
                .getSecondClassificationsByFirstClassId(
                        firstClassId);
    }

    public List<SecondClass> findAll() {
        return Objects.requireNonNull(secondClassRepository)
                .findAll();
    }
}
