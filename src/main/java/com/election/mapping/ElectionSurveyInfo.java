package com.election.mapping;// Generated Jan 15, 2018 9:03:36 AM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ElectionSurveyInfo generated by hbm2java
 */
@Entity
@Table(name="election_survey_info"
    
)
public class ElectionSurveyInfo  implements java.io.Serializable {


     private int id;
     private ElectionSurvey electionSurvey;
     private ServiceList serviceList;

    public ElectionSurveyInfo() {
    }

	
    public ElectionSurveyInfo(int id) {
        this.id = id;
    }
    public ElectionSurveyInfo(int id, ElectionSurvey electionSurvey, ServiceList serviceList) {
       this.id = id;
       this.electionSurvey = electionSurvey;
       this.serviceList = serviceList;
    }
   
     @Id 

    
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="survey_id")
    public ElectionSurvey getElectionSurvey() {
        return this.electionSurvey;
    }
    
    public void setElectionSurvey(ElectionSurvey electionSurvey) {
        this.electionSurvey = electionSurvey;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="service_list_id")
    public ServiceList getServiceList() {
        return this.serviceList;
    }
    
    public void setServiceList(ServiceList serviceList) {
        this.serviceList = serviceList;
    }




}


