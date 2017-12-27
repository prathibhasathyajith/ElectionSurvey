/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author prathibha
 */
public class PartyInputBean {

    private String partyId;
    private String partyCode;
    private String name;
    private String description;
    private String type;
    private String contactNo;
    private String email;
    private String address;
    private String status;
    private String image;
    private byte[] img;
    private String message;

    private File logoImg;
    private String logoImgContentType;
    private String logoImgFileName;

    private byte[] editLogo;
    private String editLogoImg;

    private List<Type> typeList = new ArrayList<Type>();
    private List<Type> statusList = new ArrayList<Type>();

    //for history
    private String newvalue;
    private String oldvalue;

    //table
    private List<PartyBean> gridModel;
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
     * @return the partyId
     */
    public String getPartyId() {
        return partyId;
    }

    /**
     * @param partyId the partyId to set
     */
    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    /**
     * @return the partyCode
     */
    public String getPartyCode() {
        return partyCode;
    }

    /**
     * @param partyCode the partyCode to set
     */
    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String discription) {
        this.description = discription;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the img
     */
    public byte[] getImg() {
        return img;
    }

    /**
     * @param img the img to set
     */
    public void setImg(byte[] img) {
        this.img = img;
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
     * @return the logoImg
     */
    public File getLogoImg() {
        return logoImg;
    }

    /**
     * @param logoImg the logoImg to set
     */
    public void setLogoImg(File logoImg) {
        this.logoImg = logoImg;
    }

    /**
     * @return the logoImgContentType
     */
    public String getLogoImgContentType() {
        return logoImgContentType;
    }

    /**
     * @param logoImgContentType the logoImgContentType to set
     */
    public void setLogoImgContentType(String logoImgContentType) {
        this.logoImgContentType = logoImgContentType;
    }

    /**
     * @return the logoImgFileName
     */
    public String getLogoImgFileName() {
        return logoImgFileName;
    }

    /**
     * @param logoImgFileName the logoImgFileName to set
     */
    public void setLogoImgFileName(String logoImgFileName) {
        this.logoImgFileName = logoImgFileName;
    }

    /**
     * @return the editLogo
     */
    public byte[] getEditLogo() {
        return editLogo;
    }

    /**
     * @param editLogo the editLogo to set
     */
    public void setEditLogo(byte[] editLogo) {
        this.editLogo = editLogo;
    }

    /**
     * @return the editLogoImg
     */
    public String getEditLogoImg() {
        try {
            byte[] blobAsBytes = getEditLogo();
            blobAsBytes = Base64.encodeBase64(blobAsBytes);
            this.editLogoImg = new String(blobAsBytes);
        } catch (Exception e) {
            this.editLogoImg = "";
        }
        return editLogoImg;
    }

    /**
     * @param editLogoImg the editLogoImg to set
     */
    public void setEditLogoImg(String editLogoImg) {
        this.editLogoImg = editLogoImg;
    }

    /**
     * @return the typeList
     */
    public List<Type> getTypeList() {
        return typeList;
    }

    /**
     * @param typeList the typeList to set
     */
    public void setTypeList(List<Type> typeList) {
        this.typeList = typeList;
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
    public List<PartyBean> getGridModel() {
        return gridModel;
    }

    /**
     * @param gridModel the gridModel to set
     */
    public void setGridModel(List<PartyBean> gridModel) {
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

}
