/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.action;

import com.election.bean.PartyLABean;
import com.election.bean.PartyLAInputBean;
import com.election.bean.Type;
import com.election.dao.PartyLADAO;
import com.election.mapping.PartyLa;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prathibha_s
 */
public class PartyLAAction extends ActionSupport implements ModelDriven<Object> {

    PartyLAInputBean inputBean = new PartyLAInputBean();

    @Override
    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called PartyLAAction : execute");
        return SUCCESS;
    }

    public String view() {
        System.out.println("called PartyLAAction : view");
        String msg = "view";
        try {
            PartyLADAO dao = new PartyLADAO();

            List<Type> statusList = new ArrayList<Type>();

            Type status = new Type();
            status.setCode("ACT");
            status.setDescription("Active");
            statusList.add(status);
            status = new Type();
            status.setCode("DEAT");
            status.setDescription("Deactive");
            statusList.add(status);

            inputBean.setStatusList(statusList);
            dao.getPartyList(inputBean);
            dao.getLAList(inputBean);

        } catch (Exception e) {
            addActionError("PartyLA error occurred while processing");
            Logger.getLogger(PartyLAAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }

    public String list() {
        System.out.println("called PartyLAAction: List");
        try {

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }
            PartyLADAO dao = new PartyLADAO();

            List<PartyLABean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);

            if (!dataList.isEmpty()) {
                records = dataList.get(0).getFullCount();
                inputBean.setRecords(records);
                inputBean.setGridModel(dataList);
                int total = (int) Math.ceil((double) records / (double) rows);
                inputBean.setTotal(total);
            } else {
                inputBean.setRecords(0L);
                inputBean.setTotal(0);
            }

        } catch (Exception e) {
            addActionError("PartyLA list error occurred while processing");
            Logger.getLogger(PartyLAAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    public String add() {
        System.out.println("called CandidateAction : add");
        String result = "message";

        try {

            PartyLADAO dao = new PartyLADAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                message = dao.insertPartyLA(inputBean);

                if (message.isEmpty()) {
                    addActionMessage("Parties of local authority assigned successfully");
                } else {
                    addActionError(message);
                }

            } else {
                addActionError(message);
            }

        } catch (Exception e) {
            addActionError("PartyLA add error occurred while processing");
            Logger.getLogger(PartyLAAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String update() {
        System.out.println("called PartyLAAction : update");
        String result = "message";
        try {

            PartyLADAO dao = new PartyLADAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {
                message = dao.updatePartyLA(inputBean);
                if (message.isEmpty()) {
                    addActionMessage("Parties of local authority updated successfully");
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("PartyLA update error occurred while processing");
            Logger.getLogger(PartyLAAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public String find() {
        System.out.println("called PartyLAAction: find");
        PartyLa partyLa = null;
        try {

            System.out.println("id " + inputBean.getId());

            if (inputBean.getId() != null && !inputBean.getId().isEmpty()) {

                PartyLADAO dao = new PartyLADAO();

                partyLa = dao.findPartyLaById(inputBean.getId());
                
                inputBean.setLa(partyLa.getLocalAuthority().getCode());
                inputBean.setParty(partyLa.getPartyCode());
                inputBean.setContactNo(partyLa.getContactNo());
                inputBean.setAddress(partyLa.getAddress());
                inputBean.setStatus(partyLa.getStatus());
                
                

            } else {
                inputBean.setMessage("Empty detail");
            }
        } catch (Exception ex) {
            addActionError("PartyLA find error occurred while processing");
            Logger.getLogger(PartyLAAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    public String delete() {
        System.out.println("called PartyLAAction : Delete");
        String message = null;
        String retType = "delete";
        try {

            PartyLADAO dao = new PartyLADAO();
            message = dao.deletePartyLA(inputBean);
            if (message.isEmpty()) {
                message = "Deleted successfully";
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            addActionError("PartyLA delete error occurred while processing");
            Logger.getLogger(PartyLAAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return retType;
    }

    private String validateInputs() {
        String message = "";
        if (inputBean.getLa()== null || inputBean.getLa().trim().isEmpty()) {
            message = "Local authority cannot be empty";
        } else if (inputBean.getParty()== null || inputBean.getParty().trim().isEmpty()) {
            message = "Party cannot be empty";
        } else if (inputBean.getContactNo()== null || inputBean.getContactNo().trim().isEmpty()) {
            message = "Contact no cannot be empty";
        } else if (inputBean.getAddress()== null || inputBean.getAddress().trim().isEmpty()) {
            message = "Address cannot be empty";
        } else if (inputBean.getStatus()== null || inputBean.getStatus().trim().isEmpty()) {
            message = "Status cannot be empty";
        } 
        return message;
    }
}
