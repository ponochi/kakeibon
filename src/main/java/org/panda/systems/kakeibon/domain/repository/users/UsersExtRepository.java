package org.panda.systems.kakeibon.domain.repository.users;

import org.panda.systems.kakeibon.domain.model.users.UsersExt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersExtRepository
        extends JpaRepository<UsersExt, Integer> {

    @SuppressWarnings("NullableProblems")
    @Override
    @Query(value =
            "SELECT " +
                    "  DISTINCT" +
                    "    kpue.user_id, " +
                    "  kpue.last_name, " +
                    "  kpue.first_name, " +
                    "  kpue.email, " +
                    "  kpue.birth_day, " +
                    "  kpue.phone_number, " +
                    "  kpue.entry_date, " +
                    "  kpue.update_date, " +
                    "  (SELECT " +
                    "    kpu.username " +
                    "  FROM " +
                    "    kp.users kpu " +
                    "  WHERE " +
                    "    kpu.user_id = kpue.user_id) AS username " +
                    "FROM" +
                    "  users_ext kpue " +
                    "ORDER BY " +
                    "  kpue.user_id", nativeQuery = true)
    List<UsersExt> findAll();

    @Query(value =
            "SELECT " +
                    "  kpue.user_id, " +
                    "  kpue.last_name, " +
                    "  kpue.first_name, " +
                    "  kpue.email, " +
                    "  kpue.birth_day, " +
                    "  kpue.phone_number, " +
                    "  kpue.entry_date, " +
                    "  kpue.update_date " +
                    "FROM" +
                    "  kp.users_ext kpue " +
                    "WHERE" +
                    "  kpue.user_id = ?1", nativeQuery = true)
    UsersExt findByUserExtId(Long userId);

    @Query(value = "SELECT MAX(user_id) FROM users_ext", nativeQuery = true)
    Integer getMaxId();

    UsersExt saveAndFlush(UsersExt entity);
}
