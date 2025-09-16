package com.gymmanagement.gymmanagement.service;

import com.gymmanagement.gymmanagement.dto.DashboardStatsDTO;
import com.gymmanagement.gymmanagement.model.Membership;
import com.gymmanagement.gymmanagement.repository.MemberRepository;
import com.gymmanagement.gymmanagement.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    public DashboardStatsDTO getDashboardStats(){
        Long totalMembers = memberRepository.count();
        Long activeMemberships = membershipRepository.countByStatus("ACTIVE");
        Long expiringMemberships = (long) membershipRepository.findExpiringMemberships(
                LocalDate.now(), LocalDate.now().plusDays(7)).size();
        BigDecimal monthlyRevenue = calculateMonthlyRevenue();
        Long newMembersThisMonth = memberRepository.countByCreatedAtAfter(
                LocalDateTime.now().withDayOfMonth(1));

        return new DashboardStatsDTO(
                totalMembers, activeMemberships, expiringMemberships,
                monthlyRevenue, newMembersThisMonth
        );
    }

    private BigDecimal calculateMonthlyRevenue(){
        List<Membership> activateMemberships = membershipRepository.findByStatus("Activate");
        return activateMemberships.stream()
                .map(Membership::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
