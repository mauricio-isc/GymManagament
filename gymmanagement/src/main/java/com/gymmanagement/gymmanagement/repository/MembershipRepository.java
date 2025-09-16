package com.gymmanagement.gymmanagement.repository;


import com.gymmanagement.gymmanagement.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    List<Membership> findByMemberId(Long memberId);
    List<Membership> findByStatus(String status);
    Long countByStatus(String status);

    @Query("SELECT m FROM Membership m WHERE m.endDate BETWEEN :startDate AND :endDate AND m.status = 'ACTIVE'")
    List<Membership> findExpiringMemberships(@Param("startDate")LocalDate startDate,
                                            @Param("endDate") LocalDate endDate);

    @Query("SELECT m FROM Membership m WHERE m.member.id = :memberId AND m.status = 'ACTIVE' AND m.endDate >= CURRENT_DATE")
    List<Membership> findActiveMembershipsByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT COUNT(m) FROM Membership m WHERE m.status = 'ACTIVE' AND m.endDate >= CURRENT_DATE")
    Long countCurrentlyActiveMemberships();


}
