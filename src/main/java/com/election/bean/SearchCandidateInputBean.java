/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prathibha_s
 */
public class SearchCandidateInputBean {
    private String candidateName;
    private String candidateID;
    private String candidateVote;
    private String candidateWard;
    private String candidateDistirct;
    private String candidateProvince;
    private String candidateLa;
    private String candidateParty;
    private String candidateFullVoteCount;
    private String candidateVotePercentage;
    private String candidateWardVoteCount;

    private String count;
    private List<CountVoteSummary> countList = new ArrayList<CountVoteSummary>();

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(String candidateID) {
        this.candidateID = candidateID;
    }

    public String getCandidateVote() {
        return candidateVote;
    }

    public void setCandidateVote(String candidateVote) {
        this.candidateVote = candidateVote;
    }

    public String getCandidateWard() {
        return candidateWard;
    }

    public void setCandidateWard(String candidateWard) {
        this.candidateWard = candidateWard;
    }

    public String getCandidateDistirct() {
        return candidateDistirct;
    }

    public void setCandidateDistirct(String candidateDistirct) {
        this.candidateDistirct = candidateDistirct;
    }

    public String getCandidateProvince() {
        return candidateProvince;
    }

    public void setCandidateProvince(String candidateProvince) {
        this.candidateProvince = candidateProvince;
    }

    public String getCandidateLa() {
        return candidateLa;
    }

    public void setCandidateLa(String candidateLa) {
        this.candidateLa = candidateLa;
    }

    public String getCandidateParty() {
        return candidateParty;
    }

    public void setCandidateParty(String candidateParty) {
        this.candidateParty = candidateParty;
    }

    public String getCandidateFullVoteCount() {
        return candidateFullVoteCount;
    }

    public void setCandidateFullVoteCount(String candidateFullVoteCount) {
        this.candidateFullVoteCount = candidateFullVoteCount;
    }

    public String getCandidateVotePercentage() {
        return candidateVotePercentage;
    }

    public void setCandidateVotePercentage(String candidateVotePercentage) {
        this.candidateVotePercentage = candidateVotePercentage;
    }

    public String getCandidateWardVoteCount() {
        return candidateWardVoteCount;
    }

    public void setCandidateWardVoteCount(String candidateWardVoteCount) {
        this.candidateWardVoteCount = candidateWardVoteCount;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<CountVoteSummary> getCountList() {
        return countList;
    }

    public void setCountList(List<CountVoteSummary> countList) {
        this.countList = countList;
    }
    
    
}
