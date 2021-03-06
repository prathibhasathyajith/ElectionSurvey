/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.bean;

import com.election.mapping.LocalAuthority;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prathibha_s
 */
public class LoginPARInputBean {

    private String partyName;
    private String partyID;
    private String partyVote;
    private String partyWard;
    private String partyDistirct;
    private String partyProvince;
    private String partyLa;
    private String partyParty;
    private String partyFullVoteCount;
    private String partyVotePercentage;
    private String partyWardVoteCount;

    private String la;
    private List<LocalAuthority> laList = new ArrayList<LocalAuthority>();

    public String getLa() {
        return la;
    }

    public void setLa(String la) {
        this.la = la;
    }

    public List<LocalAuthority> getLaList() {
        return laList;
    }

    public void setLaList(List<LocalAuthority> laList) {
        this.laList = laList;
    }

    private String count;
    private List<CountVoteSummary> countList = new ArrayList<CountVoteSummary>();

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyID() {
        return partyID;
    }

    public void setPartyID(String partyID) {
        this.partyID = partyID;
    }

    public String getPartyVote() {
        return partyVote;
    }

    public void setPartyVote(String partyVote) {
        this.partyVote = partyVote;
    }

    public String getPartyWard() {
        return partyWard;
    }

    public void setPartyWard(String partyWard) {
        this.partyWard = partyWard;
    }

    public String getPartyDistirct() {
        return partyDistirct;
    }

    public void setPartyDistirct(String partyDistirct) {
        this.partyDistirct = partyDistirct;
    }

    public String getPartyProvince() {
        return partyProvince;
    }

    public void setPartyProvince(String partyProvince) {
        this.partyProvince = partyProvince;
    }

    public String getPartyLa() {
        return partyLa;
    }

    public void setPartyLa(String partyLa) {
        this.partyLa = partyLa;
    }

    public String getPartyParty() {
        return partyParty;
    }

    public void setPartyParty(String partyParty) {
        this.partyParty = partyParty;
    }

    public String getPartyFullVoteCount() {
        return partyFullVoteCount;
    }

    public void setPartyFullVoteCount(String partyFullVoteCount) {
        this.partyFullVoteCount = partyFullVoteCount;
    }

    public String getPartyVotePercentage() {
        return partyVotePercentage;
    }

    public void setPartyVotePercentage(String partyVotePercentage) {
        this.partyVotePercentage = partyVotePercentage;
    }

    public String getPartyWardVoteCount() {
        return partyWardVoteCount;
    }

    public void setPartyWardVoteCount(String partyWardVoteCount) {
        this.partyWardVoteCount = partyWardVoteCount;
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
