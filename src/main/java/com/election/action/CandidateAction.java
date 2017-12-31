/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.action;

import com.election.bean.CandidateBean;
import com.election.bean.CandidateInputBean;
import com.election.bean.Type;
import com.election.common.dao.CommonDAO;
import com.election.dao.CandidateDAO;
import com.election.mapping.Candidate;
import com.election.mapping.Party;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prathibha
 */
public class CandidateAction extends ActionSupport implements ModelDriven<Object> {

    CandidateInputBean inputBean = new CandidateInputBean();

    @Override
    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called CandidateAction : execute");
        return SUCCESS;
    }

    public String view() {
        System.out.println("called CandidateAction : view");
        String msg = "view";
        try {
            List<Type> statusList = new ArrayList<Type>();
            List<Type> youthList = new ArrayList<Type>();
            List<Type> genderList = new ArrayList<Type>();

            Type status = new Type();
            status.setCode("ACT");
            status.setDescription("Active");
            statusList.add(status);
            status = new Type();
            status.setCode("DEAT");
            status.setDescription("Deactive");
            statusList.add(status);

            inputBean.setStatusList(statusList);

            status = new Type();
            status.setCode("YES");
            status.setDescription("Yes");
            youthList.add(status);
            status = new Type();
            status.setCode("NO");
            status.setDescription("No");
            youthList.add(status);

            inputBean.setYouthList(youthList);

            status = new Type();
            status.setCode("MALE");
            status.setDescription("Male");
            genderList.add(status);
            status = new Type();
            status.setCode("FMALE");
            status.setDescription("Female");
            genderList.add(status);

            inputBean.setGenderList(genderList);

            CandidateDAO dao = new CandidateDAO();
            dao.getProvinceList(inputBean);
            dao.getDistrictList(inputBean);
            dao.getLAList(inputBean);
            dao.getWardList(inputBean);
            dao.getPartyList(inputBean);

        } catch (Exception e) {
            addActionError("Candidate error occurred while processing");
            Logger.getLogger(CandidateAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }

    public String list() {
        System.out.println("called CandidateAction: List");
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
            CandidateDAO dao = new CandidateDAO();

            List<CandidateBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);

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
            addActionError("Candidate list error occurred while processing");
            Logger.getLogger(CandidateAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    public String add() {
        System.out.println("called CandidateAction : add");
        String result = "message";

        try {

            CandidateDAO dao = new CandidateDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                message = dao.insertCandidate(inputBean);

                if (message.isEmpty()) {
                    addActionMessage("Candidate created successfully");
                } else {
                    addActionError(message);
                }

            } else {
                addActionError(message);
            }

        } catch (Exception e) {
            addActionError("Candidate add error occurred while processing");
            Logger.getLogger(CandidateAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String update() {
        System.out.println("called CandidateAction : update");
        String result = "message";
        try {
            if (inputBean.getWard()!= null && !inputBean.getWard().isEmpty()) {

                CandidateDAO dao = new CandidateDAO();
                String message = this.validateInputs();

                if (message.isEmpty()) {
                    message = dao.updateCandidate(inputBean);
                    if (message.isEmpty()) {
                        addActionMessage("Candidate updated successfully");
                    } else {
                        addActionError(message);
                    }
                } else {
                    addActionError(message);
                }

            }
        } catch (Exception ex) {
            addActionError("Candidate update error occurred while processing");
            Logger.getLogger(CandidateAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public String find() {
        System.out.println("called CandidateAction: find");
        Candidate candidate = null;
        try {
            
            System.out.println("can id "+inputBean.getCandidateId());
            System.out.println("can id "+inputBean.getNic());
            
            if (inputBean.getCandidateId() != null && !inputBean.getCandidateId().isEmpty()) {

                CandidateDAO dao = new CandidateDAO();

                candidate = dao.findCandidateById(inputBean.getCandidateId());
                
//                Party pty = CommonDAO.getPartyID(candidate.getPartyCode());
                inputBean.setCandidateId(candidate.getCandidateId().toString());
                inputBean.setParty(candidate.getPartyCode());
                inputBean.setWard(candidate.getWard().getCode());
                inputBean.setName(candidate.getName());
                inputBean.setNic(candidate.getNic());
                inputBean.setContactNo(candidate.getContactNo());
                inputBean.setAddress(candidate.getAddress());
                inputBean.setGender(candidate.getGender());
                inputBean.setYouth(candidate.getYouth());
                inputBean.setStatus(candidate.getStatus());

            }else{
                inputBean.setMessage("Empty detail");
            }
        } catch (Exception ex) {
            addActionError("Candidate find error occurred while processing");
            Logger.getLogger(CandidateAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    public String delete() {
        System.out.println("called CandidateAction : Delete");
        String message = null;
        String retType = "delete";
        try {

            CandidateDAO dao = new CandidateDAO();
            message = dao.deleteCandidate(inputBean);
            if (message.isEmpty()) {
                message = "Candidate deleted successfully";
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            addActionError("Candidate delete error occurred while processing");
            Logger.getLogger(CandidateAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return retType;
    }

//    ================ drop down load ===================
    public String findDistrict() throws Exception {
        System.out.println("called SearchAction: findDistrict from province");
        try {
            CandidateDAO dao = new CandidateDAO();

            if (inputBean.getProvince().equals("empty")) {
                dao.getProvinceList(inputBean);
                dao.getDistrictList(inputBean);
                dao.getLAList(inputBean);
                dao.getWardList(inputBean);
            } else {
                dao.getDistrictListFromProvince(inputBean);
            }

        } catch (Exception e) {
            System.out.println("Find District " + e);
            Logger.getLogger(CandidateAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    public String findLA() throws Exception {
        System.out.println("called SearchAction: findLA from District");
        try {
            CandidateDAO dao = new CandidateDAO();

            if (inputBean.getDistrict().equals("empty")) {
                dao.getProvinceList(inputBean);
                dao.getDistrictList(inputBean);
                dao.getLAList(inputBean);
                dao.getWardList(inputBean);
            } else {
                dao.getLAListFromDistrict(inputBean);
            }

        } catch (Exception e) {
            System.out.println("Find LA " + e);
            Logger.getLogger(CandidateAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    public String findWard() throws Exception {
        System.out.println("called SearchAction: findWard from LA");
        try {
            CandidateDAO dao = new CandidateDAO();

            if (inputBean.getLa().equals("empty")) {
                dao.getProvinceList(inputBean);
                dao.getDistrictList(inputBean);
                dao.getLAList(inputBean);
                dao.getWardList(inputBean);
            } else {
                dao.getWardListFromLA(inputBean);
            }

        } catch (Exception e) {
            System.out.println("Find ward " + e);
            Logger.getLogger(CandidateAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    public String findAll() throws Exception {
        System.out.println("called SearchAction: findAll from ward");
        try {
            CandidateDAO dao = new CandidateDAO();

            System.out.println("ward " + inputBean.getLa());

            if (inputBean.getWard().equals("empty")) {
                dao.getProvinceList(inputBean);
                dao.getDistrictList(inputBean);
                dao.getLAList(inputBean);
                dao.getWardList(inputBean);
            } else {
                dao.getAllListFromWard(inputBean);
            }

        } catch (Exception e) {
            System.out.println("Find all " + e);
            Logger.getLogger(CandidateAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    private String validateInputs() {
        String message = "";
        if (inputBean.getProvince() == null || inputBean.getProvince().trim().isEmpty()) {
            message = "Province cannot be empty";
        } else if (inputBean.getDistrict() == null || inputBean.getDistrict().trim().isEmpty()) {
            message = "District cannot be empty";
        } else if (inputBean.getLa() == null || inputBean.getLa().trim().isEmpty()) {
            message = "Local authority cannot be empty";
        } else if (inputBean.getWard() == null || inputBean.getWard().trim().isEmpty()) {
            message = "Ward cannot be empty";
        } else if (inputBean.getParty() == null || inputBean.getParty().trim().isEmpty()) {
            message = "Party cannot be empty";
        } else if (inputBean.getName()== null || inputBean.getName().trim().isEmpty()) {
            message = "Name cannot be empty";
        } else if (inputBean.getNic()== null || inputBean.getNic().trim().isEmpty()) {
            message = "NIC cannot be empty";
        } else if (inputBean.getContactNo()== null || inputBean.getContactNo().trim().isEmpty()) {
            message = "Contact no cannot be empty";
        } else if (inputBean.getAddress()== null || inputBean.getAddress().trim().isEmpty()) {
            message = "Address cannot be empty";
        } else if (inputBean.getGender()== null || inputBean.getGender().trim().isEmpty()) {
            message = "Gender cannot be empty";
        } else if (inputBean.getYouth()== null || inputBean.getYouth().trim().isEmpty()) {
            message = "Youth cannot be empty";
        } else if (inputBean.getStatus()== null || inputBean.getStatus().trim().isEmpty()) {
            message = "Status cannot be empty";
        }
        return message;
    }

    private String validateUpdates() {
        String message = "";

        return message;
    }

}
