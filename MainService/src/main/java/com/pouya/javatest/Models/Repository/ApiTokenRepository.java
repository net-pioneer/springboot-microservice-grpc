package com.pouya.javatest.Models.Repository;

import com.pouya.javatest.Models.PersonalAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ApiTokenRepository extends JpaRepository<PersonalAccessToken, Long> {
    @Query("select t from PersonalAccessToken t join fetch t.user u left join fetch u.roles r where t.token=:token")
    Optional<PersonalAccessToken> findByToken(String token);
    void deleteByToken(String token);
    @Query("select t from PersonalAccessToken t where t.user.id = :userId AND t.expiresAt > :now")
    Optional<PersonalAccessToken> findValidToken(Long userId, LocalDateTime now);
}