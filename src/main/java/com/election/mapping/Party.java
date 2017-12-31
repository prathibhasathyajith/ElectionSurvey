package com.election.mapping;// Generated Dec 28, 2017 3:24:17 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Party generated by hbm2java
 */
@Entity
@Table(name="party"
    ,catalog="elct_survey"
    , uniqueConstraints = @UniqueConstraint(columnNames="party_code") 
)
public class Party  implements java.io.Serializable {


     private Integer partyId;
     private String partyCode;
     private String name;
     private String description;
     private String type;
     private String contactNo;
     private String email;
     private String address;
     private String status;
     private byte[] image;
     private Set<PartyLa> partyLas = new HashSet(0);
//     private Set<Candidate> candidates = new HashSet(0);

    public Party() {
    }

    public Party(String partyCode, String name, String description, String type, String contactNo, String email, String address, String status, byte[] image, Set<PartyLa> partyLas) {
       this.partyCode = partyCode;
       this.name = name;
       this.description = description;
       this.type = type;
       this.contactNo = contactNo;
       this.email = email;
       this.address = address;
       this.status = status;
       this.image = image;
       this.partyLas = partyLas;
//       this.candidates = candidates;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="party_id", unique=true, nullable=false)
    public Integer getPartyId() {
        return this.partyId;
    }
    
    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    
    @Column(name="party_code", unique=true, length=45)
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

    
    @Column(name="description")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name="type", length=45)
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    
    @Column(name="contact_no", length=12)
    public String getContactNo() {
        return this.contactNo;
    }
    
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    
    @Column(name="email")
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    
    @Column(name="address")
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    
    @Column(name="status", length=45)
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    
    @Column(name="image")
    public byte[] getImage() {
        return this.image;
    }
    
    public void setImage(byte[] image) {
        this.image = image;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="party")
    public Set<PartyLa> getPartyLas() {
        return this.partyLas;
    }
    
    public void setPartyLas(Set<PartyLa> partyLas) {
        this.partyLas = partyLas;
    }

//@OneToMany(fetch=FetchType.LAZY, mappedBy="party")
//    public Set<Candidate> getCandidates() {
//        return this.candidates;
//    }
//    
//    public void setCandidates(Set<Candidate> candidates) {
//        this.candidates = candidates;
//    }
//



}


