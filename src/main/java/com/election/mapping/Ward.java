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
 * Ward generated by hbm2java
 */
@Entity
@Table(name = "ward"
)
public class Ward implements java.io.Serializable {

    private String code;
    private LocalAuthority localAuthority;
    private String description;
    private Set<Candidate> candidates = new HashSet(0);
    private Set<Voting> votings = new HashSet(0);
    private Set<CandidateList> candidateLists = new HashSet(0);

    public Ward() {
    }

    public Ward(String code) {
        this.code = code;
    }

    public Ward(String code, LocalAuthority localAuthority, String description, Set<Candidate> candidates, Set<Voting> votings, Set<CandidateList> candidateLists) {
        this.code = code;
        this.localAuthority = localAuthority;
        this.description = description;
        this.candidates = candidates;
        this.votings = votings;
        this.candidateLists = candidateLists;
    }

    @Id

    @Column(name = "code", unique = true, nullable = false, length = 10)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "la_code")
    public LocalAuthority getLocalAuthority() {
        return this.localAuthority;
    }

    public void setLocalAuthority(LocalAuthority localAuthority) {
        this.localAuthority = localAuthority;
    }

    @Column(name = "description", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ward")
    public Set<Candidate> getCandidates() {
        return this.candidates;
    }

    public void setCandidates(Set<Candidate> candidates) {
        this.candidates = candidates;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ward")
    public Set<Voting> getVotings() {
        return this.votings;
    }

    public void setVotings(Set<Voting> votings) {
        this.votings = votings;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ward")
    public Set<CandidateList> getCandidateLists() {
        return this.candidateLists;
    }

    public void setCandidateLists(Set<CandidateList> candidateLists) {
        this.candidateLists = candidateLists;
    }

}
