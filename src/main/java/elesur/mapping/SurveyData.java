package elesur.mapping;
// Generated Jun 15, 2017 9:53:30 AM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * SurveyData generated by hbm2java
 */
@Entity
@Table(name="survey_data"
)
public class SurveyData  implements java.io.Serializable {


     private Integer id;
     private Gnd gnd;
     private LocalAuthority localAuthority;
     private RevenueSource revenueSource;
     private User user;
     private Date startTime;
     private Date endTime;
     private int transferType;
     private String latitude;
     private String longitude;
     private Date createdTime;
     private byte[] image;
     private Set<UserAnswer> userAnswers = new HashSet(0);

    public SurveyData() {
    }

	
    public SurveyData(RevenueSource revenueSource, User user, Date startTime, Date endTime, int transferType, String latitude, String longitude, Date createdTime) {
        this.revenueSource = revenueSource;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.transferType = transferType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdTime = createdTime;
    }
    public SurveyData(Gnd gnd, LocalAuthority localAuthority, RevenueSource revenueSource, User user, Date startTime, Date endTime, int transferType, String latitude, String longitude, Date createdTime, byte[] image, Set<UserAnswer> userAnswers) {
       this.gnd = gnd;
       this.localAuthority = localAuthority;
       this.revenueSource = revenueSource;
       this.user = user;
       this.startTime = startTime;
       this.endTime = endTime;
       this.transferType = transferType;
       this.latitude = latitude;
       this.longitude = longitude;
       this.createdTime = createdTime;
       this.image = image;
       this.userAnswers = userAnswers;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="ID", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="GND_CODE")
    public Gnd getGnd() {
        return this.gnd;
    }
    
    public void setGnd(Gnd gnd) {
        this.gnd = gnd;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LA_CODE")
    public LocalAuthority getLocalAuthority() {
        return this.localAuthority;
    }
    
    public void setLocalAuthority(LocalAuthority localAuthority) {
        this.localAuthority = localAuthority;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVENUE_SRC_CODE", nullable=false)
    public RevenueSource getRevenueSource() {
        return this.revenueSource;
    }
    
    public void setRevenueSource(RevenueSource revenueSource) {
        this.revenueSource = revenueSource;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="USER_ID", unique=true, nullable=false)
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="START_TIME", nullable=false, length=19)
    public Date getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="END_TIME", nullable=false, length=19)
    public Date getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    
    @Column(name="TRANSFER_TYPE", nullable=false)
    public int getTransferType() {
        return this.transferType;
    }
    
    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    
    @Column(name="LATITUDE", nullable=false, length=15)
    public String getLatitude() {
        return this.latitude;
    }
    
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    
    @Column(name="LONGITUDE", nullable=false, length=15)
    public String getLongitude() {
        return this.longitude;
    }
    
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_TIME", nullable=false, length=19)
    public Date getCreatedTime() {
        return this.createdTime;
    }
    
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    
    @Column(name="IMAGE")
    public byte[] getImage() {
        return this.image;
    }
    
    public void setImage(byte[] image) {
        this.image = image;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="surveyData")
    public Set<UserAnswer> getUserAnswers() {
        return this.userAnswers;
    }
    
    public void setUserAnswers(Set userAnswers) {
        this.userAnswers = userAnswers;
    }




}


