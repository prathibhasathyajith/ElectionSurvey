package elesur.mapping;
// Generated Jun 15, 2017 9:53:30 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Gnd generated by hbm2java
 */
@Entity
@Table(name="gnd"
   
)
public class Gnd  implements java.io.Serializable {


     private String code;
     private String description;
     private String laCode;
     private Set<SurveyData> surveyDatas = new HashSet(0);

    public Gnd() {
    }

	
    public Gnd(String code, String description, String laCode) {
        this.code = code;
        this.description = description;
        this.laCode = laCode;
    }
    public Gnd(String code, String description, String laCode, Set<SurveyData> surveyDatas) {
       this.code = code;
       this.description = description;
       this.laCode = laCode;
       this.surveyDatas = surveyDatas;
    }
   
     @Id 

    
    @Column(name="CODE", unique=true, nullable=false, length=10)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    
    @Column(name="DESCRIPTION", nullable=false, length=100)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name="LA_CODE", nullable=false, length=10)
    public String getLaCode() {
        return this.laCode;
    }
    
    public void setLaCode(String laCode) {
        this.laCode = laCode;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="gnd")
    public Set<SurveyData> getSurveyDatas() {
        return this.surveyDatas;
    }
    
    public void setSurveyDatas(Set surveyDatas) {
        this.surveyDatas = surveyDatas;
    }




}


