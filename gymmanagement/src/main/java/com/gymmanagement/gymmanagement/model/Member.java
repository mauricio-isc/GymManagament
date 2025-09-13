package com.gymmanagement.gymmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "Name is mandatory")
    @Column(nullable = false)
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name= "emergency_contact")
    private String emergencyContact;

    @Column(name="medical_notes", columnDefinition = "TEXT")
    private String medicalNotes;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Membership> membership = new ArrayList<>();

    //constructor
    public Member(){
        this.createdAt = LocalDateTime.now();
    }

    public Member(String name, String email){
        this.name = name;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    //getters and setters

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getMedicalNotes() {
        return medicalNotes;
    }

    public void setMedicalNotes(String medicalNotes) {
        this.medicalNotes = medicalNotes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Membership> getMembership() {
        return membership;
    }

    public void setMembership(List<Membership> membership) {
        this.membership = membership;
    }

    public void addMembership(Membership membership){
        membership.add(membership);
        membership.setMember(this);
    }

    public void removeMembership(Membership membership){
        memberships.add(membership);
        membership.setMember(this);
    }

    public void removeMembership(Membership membership) {
        memberships.remove(membership);
        membership.setMember(null);
    }
}
