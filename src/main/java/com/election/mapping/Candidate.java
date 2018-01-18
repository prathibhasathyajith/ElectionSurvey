package com.election.mapping;// Generated Jan 15, 2018 9:03:36 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Candidate generated by hbm2java
 */
@Entity
@Table(name="candidate"
    
)
public class Candidate  implements java.io.Serializable {


     private int candidateId;
     private Ward ward;
     private String partyCode;
     private String name;
     private String nic;
     private String contactNo;
     private String address;
     private String gender;
     private String youth;
     private String username;
     private String password;
     private String oldPassword;
     private String status;
     private Set<CandidateList> candidateLists = new HashSet(0);

    public Candidate() {
    }

	
    public Candidate(int candidateId) {
        this.candidateId = candidateId;
    }
    public Candidate(int candidateId, Ward ward, String partyCode, String name, String nic, String contactNo, String address, String gender, String youth, String username, String password, String oldPassword, String status, Set<CandidateList> candidateLists) {
       this.candidateId = candidateId;
       this.ward = ward;
       this.partyCode = partyCode;
       this.name = name;
       this.nic = nic;
       this.contactNo = contactNo;
       this.address = address;
       this.gender = gender;
       this.youth = youth;
       this.username = username;
       this.password = password;
       this.oldPassword = oldPassword;
       this.status = status;
       this.candidateLists = candidateLists;
    }
   
     @Id 

    
    @Column(name="candidate_id", unique=true, nullable=false)
    public int getCandidateId() {
        return this.candidateId;
    }
    
    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ward_code")
    public Ward getWard() {
        return this.ward;
    }
    
    public void setWard(Ward ward) {
        this.ward = ward;
    }

    
    @Column(name="party_code", length=45)
    public String getPartyCode() {
        return this.partyCode;
    }
    
    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    
    @Column(name="name")
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="nic", length=15)
    public String getNic() {
        return this.nic;
    }
    
    public void setNic(String nic) {
        this.nic = nic;
    }

    
    @Column(name="contact_no", length=15)
    public String getContactNo() {
        return this.contactNo;
    }
    
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    
    @Column(name="address")
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    
    @Column(name="gender", length=10)
    public String getGender() {
        return this.gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }

    
    @Column(name="youth", length=10)
    public String getYouth() {
        return this.youth;
    }
    
    public void setYouth(String youth) {
        this.youth = youth;
    }

    
    @Column(name="username")
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    
    @Column(name="password")
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    
    @Column(name="old_password")
    public String getOldPassword() {
        return this.oldPassword;
    }
    
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    
    @Column(name="status", length=45)
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="candidate")
    public Set<CandidateList> getCandidateLists() {
        return this.candidateLists;
    }
    
    public void setCandidateLists(Set<CandidateList> candidateLists) {
        this.candidateLists = candidateLists;
    }




}


