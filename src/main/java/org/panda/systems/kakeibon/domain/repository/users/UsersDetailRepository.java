package org.panda.systems.kakeibon.domain.repository.users;

import org.panda.systems.kakeibon.domain.model.users.Users;
import org.panda.systems.kakeibon.domain.service.users.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersDetailRepository extends JpaRepository<Users, String> {

    @SuppressWarnings("NullableProblems")
    @Override
    @Query(value =
            "SELECT " +
                    "   kpu.user_id," +
                    "   kpu.username," +
                    "   kpu.password," +
                    "   kpu.role_name," +
                    "   kpu.enabled," +
                    "   kpu.account_non_expired," +
                    "   kpu.account_non_locked, " +
                    "   kpu.credentials_non_expired" +
                    " FROM" +
                    "   users kpu" +
                    " ORDER BY" +
                    "   kpu.username",
            nativeQuery = true)
    List<Users> findAll();

    @Query(value =
            "SELECT " +
                    "   kpu.user_id," +
                    "   kpu.username," +
                    "   kpu.password," +
                    "   kpu.role_name," +
                    "   kpu.enabled," +
                    "   kpu.account_non_expired," +
                    "   kpu.account_non_locked, " +
                    "   kpu.credentials_non_expired" +
                    " FROM" +
                    "  kp.users kpu " +
                    "WHERE " +
                    "  kpu.username = ?1", nativeQuery = true)
    Users findByUsername(String username);

    @Query(value =
            "SELECT" +
                    "   kpu.user_id," +
                    "   kpu.username," +
                    "   kpu.password," +
                    "   kpu.role_name," +
                    "   kpu.enabled," +
                    "   kpu.account_non_expired," +
                    "   kpu.account_non_locked, " +
                    "   kpu.credentials_non_expired" +
                    " FROM" +
                    "   kp.users kpu" +
                    " WHERE" +
                    "   kpu.user_id = ?1", nativeQuery = true)
    Users findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    boolean existsByUsername(String username);

    @Query(value = "SELECT MAX(user_id) FROM users", nativeQuery = true)
    Integer getMaxUserId();

    @Override
    <S extends Users> S saveAndFlush(S entity);
}
