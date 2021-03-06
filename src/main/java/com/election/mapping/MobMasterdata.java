package com.election.mapping;// Generated Jan 15, 2018 9:03:36 AM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MobMasterdata generated by hbm2java
 */
@Entity
@Table(name="mob_masterdata"
    
)
public class MobMasterdata  implements java.io.Serializable {


     private String code;
     private String data;

    public MobMasterdata() {
    }

	
    public MobMasterdata(String code) {
        this.code = code;
    }
    public MobMasterdata(String code, String data) {
       this.code = code;
       this.data = data;
    }
   
     @Id 

    
    @Column(name="CODE", unique=true, nullable=false, length=16)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    
    @Column(name="DATA", length=65535)
    public String getData() {
        return this.data;
    }
    
    public void setData(String data) {
        this.data = data;
    }




}


