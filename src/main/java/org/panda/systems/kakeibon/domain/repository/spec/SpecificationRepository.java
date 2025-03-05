package org.panda.systems.kakeibon.domain.repository.spec;

import jakarta.persistence.LockModeType;
import org.panda.systems.kakeibon.domain.model.spec.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface SpecificationRepository
        extends JpaRepository<Specification, Long> {

    //  @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(nativeQuery = true,
            value = "SELECT" +
                    " COALESCE(MAX(ts.specification_id) + 1, 1)" +
                    " FROM" +
                    "   specification ts" +
                    " WHERE" +
                    "   ts.specification_group_id = ?1")
    Long getNextSpecificationId(Long specificationGroupId);

    @Query(nativeQuery = true,
            value = "SELECT" +
                    " ts.specification_group_id," +
                    " ts.specification_id," +
                    " ts.user_id," +
                    " ts.brand_name," +
                    " ts.price," +
                    " ts.currency_id," +
                    " ts.quantity," +
                    " ts.unit_id," +
                    " ts.tax_type_id," +
                    " ts.tax_rate_id," +
                    " ts.tax," +
                    " ts.spec_memo," +
                    " ts.deleted," +
                    " ts.entry_date," +
                    " ts.update_date" +
                    " FROM" +
                    "   specification ts" +
                    " WHERE" +
                    "   ts.specification_group_id = ?1" +
                    " AND" +
                    "   ts.specification_id = ?2" +
                    " AND" +
                    "   ts.user_id = ?3" +
                    " AND" +
                    "   ts.deleted = ?4")
    Specification findBySpecificationGroupIdAndSpecificationIdAndUserIdAndDeleted(
            Long specificationGroupId, Long specificationId, Long userId, Boolean deleted);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Specification> findAllByDeleted(Boolean deleted);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Specification> findBySpecificationGroupIdAndUserIdAndDeletedOrderBySpecificationIdDesc(
            Long specificationGroupId, Long userId, Boolean deleted);

    @SuppressWarnings({"unchecked", "NullableProblems"})
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    Specification saveAndFlush(Specification entity);

    @SuppressWarnings({"unchecked", "NullableProblems"})
//  @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Modifying
    @Query(nativeQuery = true,
            value =
                    "INSERT INTO specification(" +
                            "   specification_group_id," +
                            "   specification_id," +
                            "   user_id," +
                            "   brand_name," +
                            "   price," +
                            "   currency_id," +
                            "   quantity," +
                            "   unit_id," +
                            "   tax_type_id," +
                            "   tax_rate_id," +
                            "   tax," +
                            "   spec_memo," +
                            "   deleted," +
                            "   entry_date," +
                            "   update_date" +
                            " ) VALUES (" +
                            "   :specificationGroupId," +
                            "   :specificationId," +
                            "   :userId," +
                            "   :brandName," +
                            "   :price," +
                            "   :currencyId," +
                            "   :quantity," +
                            "   :unitId," +
                            "   :taxTypeId," +
                            "   :taxRateId," +
                            "   :tax," +
                            "   :specMemo," +
                            "   :deleted," +
                            "   :entryDate," +
                            "   :updateDate" +
                            ")")
    void saveAndFlushSpecification(
            @Param("specificationGroupId") Long specificationGroupId,
            @Param("specificationId") Long specificationId,
            @Param("userId") Long userId,
            @Param("brandName") String brandName,
            @Param("price") Long price,
            @Param("currencyId") Long currencyId,
            @Param("quantity") Long quantity,
            @Param("unitId") Long unitId,
            @Param("taxTypeId") Long taxTypeId,
            @Param("taxRateId") Long taxRateId,
            @Param("tax") Long tax,
            @Param("specMemo") String specMemo,
            @Param("deleted") Boolean deleted,
            @Param("entryDate") ZonedDateTime entryDate,
            @Param("updateDate") ZonedDateTime updateDate);

    @SuppressWarnings({"unchecked", "NullableProblems"})
//  @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Modifying
    @Query(nativeQuery = true,
            value =
                    "UPDATE" +
                            "   specification" +
                            " SET" +
                            "   user_id = :userId," +
                            "   brand_name = :brandName," +
                            "   price = :price," +
                            "   currency_id = :currencyId," +
                            "   quantity = :quantity," +
                            "   unit_id = :unitId," +
                            "   tax_type_id = :taxTypeId," +
                            "   tax_rate_id = :taxRateId," +
                            "   tax = :tax," +
                            "   spec_memo = :specMemo," +
                            "   deleted = :deleted," +
                            "   entry_date = :entryDate," +
                            "   update_date = :updateDate" +
                            " WHERE " +
                            "   specification_group_id = :specificationGroupId" +
                            " AND " +
                            "   specification_id = :specificationId")
    void update(
            @Param("specificationGroupId") Long specificationGroupId,
            @Param("specificationId") Long specificationId,
            @Param("userId") Long userId,
            @Param("brandName") String brandName,
            @Param("price") Long price,
            @Param("currencyId") Long currencyId,
            @Param("quantity") Long quantity,
            @Param("unitId") Long unitId,
            @Param("taxTypeId") Long taxTypeId,
            @Param("taxRateId") Long taxRateId,
            @Param("tax") Long tax,
            @Param("specMemo") String specMemo,
            @Param("deleted") Boolean deleted,
            @Param("entryDate") ZonedDateTime entryDate,
            @Param("updateDate") ZonedDateTime updateDate);

    @SuppressWarnings({"unchecked", "NullableProblems"})
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE" +
                    "   specification" +
                    " SET" +
                    "   deleted = true," +
                    "   update_date = :updateDate" +
                    " WHERE" +
                    "   specification_group_id = :specificationGroupId"
    )
    void deleteAllSpecDetail(
            @Param("specificationGroupId") Long specificationGroupId,
            @Param("updateDate") LocalDateTime updateDate);

    @SuppressWarnings({"unchecked", "NullableProblems"})
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE" +
                    "   specification" +
                    " SET" +
                    "   deleted = true," +
                    "   update_date = :updateDate" +
                    " WHERE" +
                    "   specification_group_id = :specificationGroupId" +
                    " AND" +
                    "  specification_id = :specificationId"
    )
    void deleteSpecDetail(
            @Param("specificationGroupId") Long specificationGroupId,
            @Param("specificationId") Long specificationId,
            @Param("updateDate") LocalDateTime updateDate);

}
