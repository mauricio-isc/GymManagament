package com.gymmanagement.gymmanagement.controller;

import com.gymmanagement.gymmanagement.model.Member;
import com.gymmanagement.gymmanagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers(){
        try{
            List<Member> members = memberService.getAllMembers();
            return new ResponseEntity<>(members, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable("id") Long id){
        Optional<Member> member = memberService.getMemberById(id);
        return member.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<Member> createMember(@Valid @RequestBody Member member){
        try{
            if(memberService.emailExits(member.getEmail())){
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            }
            Member newMember = memberService.createMember(member);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }catch (Exception e ){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable("id") Long id, @Valid @RequestBody Member member){
        try{
            Member updateMember = memberService.UpdateMember(id, member);
            if (updateMember != null){
                return new ResponseEntity<>(updateMember, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMember(@PathVariable("id") Long id){
        try{
            boolean deleted = memberService.deleteMember(id);
            if (deleted){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
