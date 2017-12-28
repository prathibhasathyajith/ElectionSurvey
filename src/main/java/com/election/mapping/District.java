package com.election.mapping;// Generated Dec 28, 2017 3:24:17 PM by Hibernate Tools 4.3.1


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
 * District generated by hbm2java
 */
@Entity
@Table(name="district"
    ,catalog="elct_survey"
)
public class District  implements java.io.Serializable {


     private String code;
     private Province province;
     private String description;
     private Set<LocalAuthority> localAuthorities = new HashSet(0);

    public District() {
    }

	
    public District(String code) {
        this.code = code;
    }
    public District(String code, Province province, String description, Set<LocalAuthority> localAuthorities) {
       this.code = code;
       this.province = province;
       this.description = description;
       this.localAuthorities = localAuthorities;
    }
   
     @Id 

    
    @Column(name="code", unique=true, nullable=false, length=10)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="province_code")
    public Province getProvince() {
        return this.province;
    }
    
    public void setProvince(Province province) {
        this.province = province;
    }

    
    @Column(name="description", length=100)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="district")
    public Set<LocalAuthority> getLocalAuthorities() {
        return this.localAuthorities;
    }
    
    public void setLocalAuthorities(Set<LocalAuthority> localAuthorities) {
        this.localAuthorities = localAuthorities;
    }




}


