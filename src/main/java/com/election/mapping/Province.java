package com.election.mapping;// Generated Jan 15, 2018 9:03:36 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Province generated by hbm2java
 */
@Entity
@Table(name="province"
    
)
public class Province  implements java.io.Serializable {


     private String code;
     private String description;
     private Set<District> districts = new HashSet(0);

    public Province() {
    }

	
    public Province(String code) {
        this.code = code;
    }
    public Province(String code, String description, Set<District> districts) {
       this.code = code;
       this.description = description;
       this.districts = districts;
    }
   
     @Id 

    
    @Column(name="code", unique=true, nullable=false, length=10)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    
    @Column(name="description", length=100)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="province")
    public Set<District> getDistricts() {
        return this.districts;
    }
    
    public void setDistricts(Set<District> districts) {
        this.districts = districts;
    }




}


