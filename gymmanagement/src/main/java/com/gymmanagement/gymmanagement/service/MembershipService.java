package com.gymmanagement.gymmanagement.service;


import com.gymmanagement.gymmanagement.model.Membership;
import com.gymmanagement.gymmanagement.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MembershipService {

    @Autowired
    private MembershipRepository membershipRepository;

    public List<Membership> getAllMemberships(){
        return membershipRepository.findAll();
    }

    public Optional<Membership> getMembershipById(Long id){
        return membershipRepository.findById(id);
    }

    public List<Membership> getMembershipsByMemberId(Long memberId){
        return membershipRepository.findByMemberId(memberId);
    }

    public Membership createMembership(Membership membership){
        //validar que la feha de inicio no sea despues de la fecha fin
        if (membership.getStartDate().isAfter(membership.getEndDate())){
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        //Validar que el precio sea positivo
        if (membership.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        return membershipRepository.save(membership);
    }

    public Membership updateMembership(Long id, Membership membershipDetails){
        Optional<Membership> optionalMembership = membershipRepository.findById(id);
        if (optionalMembership.isPresent()){
            Membership membership = optionalMembership.get();
            //validaciones
            if (membershipDetails.getStartDate().isAfter(membershipDetails.getEndDate())){
                throw new IllegalArgumentException("Start date cannot be after end date");
            }
            if (membershipDetails.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0){
                throw new IllegalArgumentException("Price must be greater than zero");
            }

            membership.setPlanType(membershipDetails.getPlanType());
            membership.setStartDate(membershipDetails.getStartDate());
            membership.setEndDate(membershipDetails.getEndDate());
            membership.setStatus(membershipDetails.getStatus());
            membership.setPrice(membershipDetails.getPrice());

            return membershipRepository.save(membership);
        }
        return null;
    }

    public boolean deleteMembership(Long id){
        if (membershipRepository.existsById(id)){
            membershipRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Membership> getMembershipsByStatus(String status){
        return membershipRepository.findByStatus(status);
    }

    public void checkAndUpdateExpiredMemberships(){
        List<Membership> activateMemberships = getMembershipsByStatus("ACTIVE");
        LocalDate today = LocalDate.now();

        for (Membership membership : activateMemberships){
            if(membership.getEndDate().isBefore(today)){
                membership.setStatus("EXPIRED");
                membershipRepository.save(membership);
            }
        }
    }

    public boolean isMembershipActive(Long memberId){
        List<Membership> memberships = getMembershipsByMemberId(memberId);
        return memberships.stream()
                .anyMatch(m -> "ACTIVE".equals(m.getStatus()) &&
                                            !m.getEndDate().isBefore(LocalDate.now()));
    }

    public Optional<Membership> getActiveMembership(Long memberId){
        List<Membership> memberships = getMembershipsByMemberId(memberId);
        return memberships.stream()
                .filter(m -> "ACTIVE".equals(m.getStatus()) &&
                                         !m.getEndDate().isBefore(LocalDate.now())
                                                 )
                .findFirst();
    }

    public Long countActiveMemberships(){
        return membershipRepository.countByStatus("ACTIVE");
    }


    public Long countExpiredMemberships(){
        return membershipRepository.countByStatus("EXPIRED");
    }

    public Long countCancelledMemberships(){
        return membershipRepository.countByStatus("CANCELLED");
    }

    public List<Membership> getExpiringMemberships(int daysBeforeExpiry){
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(daysBeforeExpiry);
        return membershipRepository.findExpiringMemberships(startDate, endDate);
    }
}
