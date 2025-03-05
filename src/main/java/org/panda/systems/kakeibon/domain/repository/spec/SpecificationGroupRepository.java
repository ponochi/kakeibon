package org.panda.systems.kakeibon.domain.repository.spec;

import jakarta.persistence.LockModeType;
import org.panda.systems.kakeibon.domain.model.spec.SpecificationGroup;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecificationGroupRepository
        extends JpaRepository<SpecificationGroup, Long> {

    @Query(nativeQuery = true,
            value =
                    "SELECT " +
                            "  MAX(specification_group_id) " +
                            "FROM " +
                            "  specification_group")
    Long getMaxGroupId();

    @Lock(LockModeType.PESSIMISTIC_READ)
    List<SpecificationGroup> findAllByDeleted(Boolean deleted, Sort sort);

    @Lock(LockModeType.PESSIMISTIC_READ)
    List<SpecificationGroup> findAllByUserIdAndDeleted
            (Long userId, Boolean deleted, Sort sort);

    @Lock(LockModeType.PESSIMISTIC_READ)
    SpecificationGroup findBySpecificationGroupIdAndUserIdAndDeleted(
            Long specificationGroupId, Long userId, Boolean deleted);

    @SuppressWarnings({"unchecked", "NullableProblems"})
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    SpecificationGroup saveAndFlush(
            SpecificationGroup specificationGroup);

}
