package com.gymmanagement.gymmanagement.controller;

import com.gymmanagement.gymmanagement.model.Membership;
import com.gymmanagement.gymmanagement.service.MembershipService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/memberships")
@CrossOrigin(origins =  "http://localhost:3000")
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @GetMapping
    public ResponseEntity<List<Membership>> getAllMemberships(){
        try{
            List<Membership> memberships = membershipService.getAllMemberships();
            return new ResponseEntity<>(memberships, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Membership> getMembershipById(@PathVariable("id") Long id){
        Optional<Membership> membership = membershipService.getMembershipById(id);
        return membership.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Membership>> getMembershipsByMemberId(@PathVariable("memberId") Long memberId){
        try{
            List<Membership> memberships = membershipService.getMembershipsByMemberId(memberId);
            return new ResponseEntity<>(memberships, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Membership>> getMembershipByStatus(@PathVariable("status") String status){
        try{
            List<Membership> memberships = membershipService.getMembershipsByStatus(status);
            return new ResponseEntity<>(memberships, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createdMembership(@Valid @RequestBody Membership membership){
    try{
        Membership newMembership = membershipService.createMembership(membership);
        return new ResponseEntity<>(membership, HttpStatus.CREATED);
    }catch (IllegalArgumentException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }catch (Exception e){
        return new ResponseEntity<>("Error creating membership", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMembership(@PathVariable("id") Long id, @Valid @RequestBody Membership membership){
        try {
            Membership updatedMembership = membershipService.updateMembership(id, membership);
            if (updatedMembership != null){
                return  new ResponseEntity<>(updatedMembership, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Membership not found", HttpStatus.NOT_FOUND);
            }
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>("Error updating membership", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMembership(@PathVariable("id") Long id){
        try{
            boolean deleted = membershipService.deleteMembership(id);
            if (deleted){
                return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/expiring")
    public ResponseEntity<List<Membership>> getExpiringMemberships(@RequestParam(defaultValue = "7") int days){
        try {
            List<Membership> expiringMemberships = membershipService.getExpiringMemberships(days);
            return new ResponseEntity<>(expiringMemberships, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/member/{memberId}/active")
    public ResponseEntity<Boolean> isMembershipActive(@PathVariable("memberId") Long memberId){
        try{
            boolean isActive = membershipService.isMembershipActive(memberId);
            return new ResponseEntity<>(isActive, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stats/active-count")
    public ResponseEntity<Long> getActiveMembershipsCount(){
        try{
            Long count = membershipService.countActiveMemberships();
            return new ResponseEntity<>(count, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/check-expired")
    public ResponseEntity<String> checkAndUpdateExpiredMemberships(){
        try{
            membershipService.checkAndUpdateExpiredMemberships();
            return new ResponseEntity<>("Expired memberships updated successfully", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error updating expired memberships", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
