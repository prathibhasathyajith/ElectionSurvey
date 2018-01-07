package com.election.mapping;
// Generated Jan 7, 2018 2:28:48 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * PartyLa generated by hbm2java
 */
@Entity
@Table(name="party_la"
    ,catalog="elect_survey"
)
public class PartyLa  implements java.io.Serializable {


     private Integer id;
     private LocalAuthority localAuthority;
     private String partyCode;
     private String contactNo;
     private String address;
     private String username;
     private String password;
     private String oldPassword;
     private String status;

    public PartyLa() {
    }

    public PartyLa(LocalAuthority localAuthority, String partyCode, String contactNo, String address, String username, String password, String oldPassword, String status) {
       this.localAuthority = localAuthority;
       this.partyCode = partyCode;
       this.contactNo = contactNo;
       this.address = address;
       this.username = username;
       this.password = password;
       this.oldPassword = oldPassword;
       this.status = status;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="la_code")
    public LocalAuthority getLocalAuthority() {
        return this.localAuthority;
    }
    
    public void setLocalAuthority(LocalAuthority localAuthority) {
        this.localAuthority = localAuthority;
    }

    
    @Column(name="party_code", length=45)
    public String getPartyCode() {
        return this.partyCode;
    }
    
    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    
    @Column(name="contact_no", length=12)
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




}


