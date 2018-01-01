/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author prathibha
 */
public class CandidateListBean {
    private String candidate;
    private String ward;
    private String party;
    private String type;
    private String name;
    private long fullCount;
    
    private Map<String,String> list_1 = new LinkedHashMap<String,String>();
    private Map<String,String> list_2 = new LinkedHashMap<String,String>();

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    public Map<String, String> getList_1() {
        return list_1;
    }

    public void setList_1(Map<String, String> list_1) {
        this.list_1 = list_1;
    }

    public Map<String, String> getList_2() {
        return list_2;
    }

    public void setList_2(Map<String, String> list_2) {
        this.list_2 = list_2;
    }
    
    
}
