package com.gymmanagement.gymmanagement.service;

import com.gymmanagement.gymmanagement.model.Member;

import com.gymmanagement.gymmanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(Long id){
        return memberRepository.findById(id);
    }

    public Optional<Member> getMemberByEmail(String email){
        return memberRepository.findByEmail(email);
    }

    public Member createMember(Member member){
        return memberRepository.save(member);
    }

    public Member UpdateMember(Long id, Member memberDetails){
        Optional<Member> optionalMember = memberRepository.findById(id);
        if(optionalMember.isPresent()){
            Member member = optionalMember.get();
            member.setName(memberDetails.getName());
            member.setEmail(memberDetails.getEmail());
            member.setPhone(memberDetails.getPhone());
            member.setBirthDate(memberDetails.getBirthDate());
            member.setEmergencyContact(memberDetails.getEmergencyContact());
            member.setMedicalNotes(memberDetails.getMedicalNotes());
            return memberRepository.save(member);
        }
        return null;
    }

    public boolean deleteMember(Long id){
        if(memberRepository.existsById(id)){
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean emailExits(String email){
        return memberRepository.existsByEmail(email);
    }
}
