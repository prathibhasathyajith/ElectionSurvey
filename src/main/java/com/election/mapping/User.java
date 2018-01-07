package com.election.mapping;
// Generated Jan 7, 2018 2:28:48 PM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name="user"
    ,catalog="elect_survey"
)
public class User  implements java.io.Serializable {


     private String userId;
     private String name;
     private String username;
     private String password;
     private String userType;
     private String userStatus;
     private Date lastLogged;
     private Date createdTime;

    public User() {
    }

	
    public User(String userId) {
        this.userId = userId;
    }
    public User(String userId, String name, String username, String password, String userType, String userStatus, Date lastLogged, Date createdTime) {
       this.userId = userId;
       this.name = name;
       this.username = username;
       this.password = password;
       this.userType = userType;
       this.userStatus = userStatus;
       this.lastLogged = lastLogged;
       this.createdTime = createdTime;
    }
   
     @Id 

    
    @Column(name="user_id", unique=true, nullable=false, length=45)
    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    
    @Column(name="name", length=50)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="username", length=45)
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    
    @Column(name="password", length=45)
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    
    @Column(name="user_type", length=45)
    public String getUserType() {
        return this.userType;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }

    
    @Column(name="user_status", length=45)
    public String getUserStatus() {
        return this.userStatus;
    }
    
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_logged", length=19)
    public Date getLastLogged() {
        return this.lastLogged;
    }
    
    public void setLastLogged(Date lastLogged) {
        this.lastLogged = lastLogged;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_time", length=19)
    public Date getCreatedTime() {
        return this.createdTime;
    }
    
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }




}


