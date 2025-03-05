package org.panda.systems.kakeibon.domain.repository.common;

import org.panda.systems.kakeibon.domain.model.common.ThirdClass;
import org.panda.systems.kakeibon.domain.model.common.ThirdClassEntityPkey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ThirdClassRepository
        extends JpaRepository<ThirdClass,
        ThirdClassEntityPkey> {

    @Query(value = "SELECT" +
            " tc.third_class_id," +
            " tc.second_class_id," +
            " tc.third_class_name," +
            " tcbo.order_number," +
            " tcbo.disabled" +
            " FROM third_class tc" +
            " INNER JOIN kp.third_class_by_order tcbo" +
            "   ON tc.second_class_id = tcbo.second_class_id" +
            "   AND tcbo.disabled = false" +
            " WHERE tc.third_class_id = ?1" +
            " ORDER BY tcbo.order_number", nativeQuery = true)
    ThirdClass getThirdClassificationByThirdClassId(
            Long thirdClassId);

    @Query(value = "SELECT" +
            " tc.third_class_id," +
            " tc.second_class_id," +
            " tc.third_class_name," +
            " tcbo.order_number," +
            " tcbo.disabled" +
            " FROM third_class tc" +
            " INNER JOIN kp.third_class_by_order tcbo" +
            "   ON tc.second_class_id = tcbo.second_class_id" +
            "   AND tcbo.disabled = false" +
            " WHERE tc.second_class_id = ?1" +
            " ORDER BY tcbo.order_number", nativeQuery = true)
    List<ThirdClass> getThirdClassificationsBySecondClassId(
            Long secondClassId);

    @Override
    @NonNull
    List<ThirdClass> findAll();
}
