package com.gymmanagement.gymmanagement.repository;

import com.gymmanagement.gymmanagement.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    Long countByCreatedAtAfter(LocalDateTime date);

    @Query("SELECT COUNT(m) FROM Member m WHERE YEAR(m.createdAt) = YEAR(CURRENT_DATE) AND MONTH(m.createdAt) = MONTH(CURRENT_DATE)")
    Long countMembersThisMonth();
}
