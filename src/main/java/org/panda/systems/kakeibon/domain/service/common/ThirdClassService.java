package org.panda.systems.kakeibon.domain.service.common;

import org.panda.systems.kakeibon.domain.model.common.ThirdClass;
import org.panda.systems.kakeibon.domain.repository.common.ThirdClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Service
public class ThirdClassService implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final ThirdClassRepository thirdClassRepository;

    public ThirdClassService() {
        this.thirdClassRepository = null;
    }

    @Autowired
    public ThirdClassService(
            ThirdClassRepository thirdClassRepository) {
        this.thirdClassRepository = thirdClassRepository;
    }

    public ThirdClass getByThirdClassId(Long thirdClassId) {
        return Objects.requireNonNull(thirdClassRepository)
                .getThirdClassificationByThirdClassId(
                        thirdClassId);
    }

    public List<ThirdClass> getBySecondClassId(Long secondClassId) {
        return Objects.requireNonNull(thirdClassRepository)
                .getThirdClassificationsBySecondClassId(
                        secondClassId);
    }

    public List<ThirdClass> findAll() {
        return Objects.requireNonNull(thirdClassRepository)
                .findAll();
    }
}
