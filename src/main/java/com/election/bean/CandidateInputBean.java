/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.bean;

import com.election.mapping.Party;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prathibha
 */
public class CandidateInputBean {

    private String candidateId;
    private String id;
    private String party;
    private String wardCode;
    private String name;
    private String nic;
    private String contactNo;
    private String address;
    private String gender;
    private String youth;
    private String username;
    private String password;
    private String oldPassword;
    private String status;
    private String message;

    private List<Type> youthList = new ArrayList<Type>();
    private List<Type> statusList = new ArrayList<Type>();
    private List<Type> genderList = new ArrayList<Type>();
    private List<Party> partyList = new ArrayList<Party>();
    private List<Type> wardList = new ArrayList<Type>();

    //for history
    private String newvalue;
    private String oldvalue;

    //table
    private List<CandidateBean> gridModel;
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private String searchField;
    private String searchString;
    private String searchOper;
    private boolean loadonce = false;
    private boolean search;

    /**
     * @return the candidateId
     */
    public String getCandidateId() {
        return candidateId;
    }

    /**
     * @param candidateId the candidateId to set
     */
    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the party
     */
    public String getParty() {
        return party;
    }

    /**
     * @param party the party to set
     */
    public void setParty(String party) {
        this.party = party;
    }

    /**
     * @return the wardCode
     */
    public String getWardCode() {
        return wardCode;
    }

    /**
     * @param wardCode the wardCode to set
     */
    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the nic
     */
    public String getNic() {
        return nic;
    }

    /**
     * @param nic the nic to set
     */
    public void setNic(String nic) {
        this.nic = nic;
    }

    /**
     * @return the contactNo
     */
    public String getContactNo() {
        return contactNo;
    }

    /**
     * @param contactNo the contactNo to set
     */
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the youth
     */
    public String getYouth() {
        return youth;
    }

    /**
     * @param youth the youth to set
     */
    public void setYouth(String youth) {
        this.youth = youth;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the oldPassword
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * @param oldPassword the oldPassword to set
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the youthList
     */
    public List<Type> getYouthList() {
        return youthList;
    }

    /**
     * @param youthList the youthList to set
     */
    public void setYouthList(List<Type> youthList) {
        this.youthList = youthList;
    }

    /**
     * @return the statusList
     */
    public List<Type> getStatusList() {
        return statusList;
    }

    /**
     * @param statusList the statusList to set
     */
    public void setStatusList(List<Type> statusList) {
        this.statusList = statusList;
    }

    /**
     * @return the newvalue
     */
    public String getNewvalue() {
        return newvalue;
    }

    /**
     * @param newvalue the newvalue to set
     */
    public void setNewvalue(String newvalue) {
        this.newvalue = newvalue;
    }

    /**
     * @return the oldvalue
     */
    public String getOldvalue() {
        return oldvalue;
    }

    /**
     * @param oldvalue the oldvalue to set
     */
    public void setOldvalue(String oldvalue) {
        this.oldvalue = oldvalue;
    }

    /**
     * @return the gridModel
     */
    public List<CandidateBean> getGridModel() {
        return gridModel;
    }

    /**
     * @param gridModel the gridModel to set
     */
    public void setGridModel(List<CandidateBean> gridModel) {
        this.gridModel = gridModel;
    }

    /**
     * @return the rows
     */
    public Integer getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(Integer rows) {
        this.rows = rows;
    }

    /**
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return the total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @return the records
     */
    public Long getRecords() {
        return records;
    }

    /**
     * @param records the records to set
     */
    public void setRecords(Long records) {
        this.records = records;
    }

    /**
     * @return the sord
     */
    public String getSord() {
        return sord;
    }

    /**
     * @param sord the sord to set
     */
    public void setSord(String sord) {
        this.sord = sord;
    }

    /**
     * @return the sidx
     */
    public String getSidx() {
        return sidx;
    }

    /**
     * @param sidx the sidx to set
     */
    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    /**
     * @return the searchField
     */
    public String getSearchField() {
        return searchField;
    }

    /**
     * @param searchField the searchField to set
     */
    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    /**
     * @return the searchString
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * @param searchString the searchString to set
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    /**
     * @return the searchOper
     */
    public String getSearchOper() {
        return searchOper;
    }

    /**
     * @param searchOper the searchOper to set
     */
    public void setSearchOper(String searchOper) {
        this.searchOper = searchOper;
    }

    /**
     * @return the loadonce
     */
    public boolean isLoadonce() {
        return loadonce;
    }

    /**
     * @param loadonce the loadonce to set
     */
    public void setLoadonce(boolean loadonce) {
        this.loadonce = loadonce;
    }

    /**
     * @return the search
     */
    public boolean isSearch() {
        return search;
    }

    /**
     * @param search the search to set
     */
    public void setSearch(boolean search) {
        this.search = search;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the genderList
     */
    public List<Type> getGenderList() {
        return genderList;
    }

    /**
     * @param genderList the genderList to set
     */
    public void setGenderList(List<Type> genderList) {
        this.genderList = genderList;
    }

    /**
     * @return the partyList
     */
    public List<Party> getPartyList() {
        return partyList;
    }

    /**
     * @param partyList the partyList to set
     */
    public void setPartyList(List<Party> partyList) {
        this.partyList = partyList;
    }

    /**
     * @return the wardList
     */
    public List<Type> getWardList() {
        return wardList;
    }

    /**
     * @param wardList the wardList to set
     */
    public void setWardList(List<Type> wardList) {
        this.wardList = wardList;
    }

}
