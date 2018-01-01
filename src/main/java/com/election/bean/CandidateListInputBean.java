/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.bean;

import com.election.mapping.District;
import com.election.mapping.LocalAuthority;
import com.election.mapping.Party;
import com.election.mapping.Province;
import com.election.mapping.Ward;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prathibha
 */
public class CandidateListInputBean {
    private String candidate;
    private String ward;
    private String party;
    private String type;
    private String name;
    private String message;
    
    private String list1;
    private String list2;
    
    
    
    private String province;
    private String la;
    private String district;
    
    private List<Type> typeList = new ArrayList<Type>();
    
    private List<Ward> wardList = new ArrayList<Ward>();
    private List<Party> partyList = new ArrayList<Party>();
    private List<Province> provinceList = new ArrayList<Province>();
    private List<District> districtList = new ArrayList<District>();
    private List<LocalAuthority> laList = new ArrayList<LocalAuthority>();
    
    private List<List<String>> newList_m = new ArrayList<List<String>>();
    
    private List<CandidateListBean> list_1 = new ArrayList<CandidateListBean>();
    private List<CandidateListBean> list_2 = new ArrayList<CandidateListBean>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLa() {
        return la;
    }

    public void setLa(String la) {
        this.la = la;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public List<Type> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Type> typeList) {
        this.typeList = typeList;
    }

    public List<Ward> getWardList() {
        return wardList;
    }

    public void setWardList(List<Ward> wardList) {
        this.wardList = wardList;
    }

    public List<Party> getPartyList() {
        return partyList;
    }

    public void setPartyList(List<Party> partyList) {
        this.partyList = partyList;
    }

    public List<Province> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<Province> provinceList) {
        this.provinceList = provinceList;
    }

    public List<District> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<District> districtList) {
        this.districtList = districtList;
    }

    public List<LocalAuthority> getLaList() {
        return laList;
    }

    public void setLaList(List<LocalAuthority> laList) {
        this.laList = laList;
    }

    public List<List<String>> getNewList_m() {
        return newList_m;
    }

    public void setNewList_m(List<List<String>> newList_m) {
        this.newList_m = newList_m;
    }

    public List<CandidateListBean> getList_1() {
        return list_1;
    }

    public void setList_1(List<CandidateListBean> list_1) {
        this.list_1 = list_1;
    }

    public List<CandidateListBean> getList_2() {
        return list_2;
    }

    public void setList_2(List<CandidateListBean> list_2) {
        this.list_2 = list_2;
    }

    public String getList1() {
        return list1;
    }

    public void setList1(String list1) {
        this.list1 = list1;
    }

    public String getList2() {
        return list2;
    }

    public void setList2(String list2) {
        this.list2 = list2;
    }
    
    
    
}
