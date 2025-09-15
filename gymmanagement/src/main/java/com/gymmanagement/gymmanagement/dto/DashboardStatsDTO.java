package com.gymmanagement.gymmanagement.dto;
import java.math.BigDecimal;

public class DashboardStatsDTO {
    private Long totalMembers;
    private Long activateMemberships;
    private Long expiringMemberships;
    private BigDecimal monthlyRevenue;
    private Long newMembersThisMonth;

    public DashboardStatsDTO() {}

    public DashboardStatsDTO(Long totalMembers, Long activateMemberships, Long expiringMemberships,
                             BigDecimal monthlyRevenue, Long newMembersThisMonth){
        this.totalMembers = totalMembers;
        this.activateMemberships = activateMemberships;
        this.expiringMemberships = expiringMemberships;
        this.monthlyRevenue = monthlyRevenue;
        this.newMembersThisMonth = newMembersThisMonth;
    }

    //getters and setters

    public Long getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(Long totalMembers) {
        this.totalMembers = totalMembers;
    }

    public Long getActivateMemberships() {
        return activateMemberships;
    }

    public void setActivateMemberships(Long activateMemberships) {
        this.activateMemberships = activateMemberships;
    }

    public Long getExpiringMemberships() {
        return expiringMemberships;
    }

    public void setExpiringMemberships(Long expiringMemberships) {
        this.expiringMemberships = expiringMemberships;
    }

    public BigDecimal getMonthlyRevenue() {
        return monthlyRevenue;
    }

    public void setMonthlyRevenue(BigDecimal monthlyRevenue) {
        this.monthlyRevenue = monthlyRevenue;
    }

    public Long getNewMembersThisMonth() {
        return newMembersThisMonth;
    }

    public void setNewMembersThisMonth(Long newMembersThisMonth) {
        this.newMembersThisMonth = newMembersThisMonth;
    }
}
