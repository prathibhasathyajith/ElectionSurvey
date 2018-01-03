/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.action;

import com.election.bean.CandidateListBean;
import com.election.bean.CandidateListInputBean;
import com.election.bean.Type;
import com.election.dao.CandidateDAO;
import com.election.dao.CandidateListDAO;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prathibha
 */
public class CandidateListAction extends ActionSupport implements ModelDriven<Object> {

    CandidateListInputBean inputBean = new CandidateListInputBean();

    @Override
    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called CandidateListAction : execute");
        return SUCCESS;
    }

    public String view() {
        System.out.println("called CandidateListAction : view");
        String msg = "view";
        try {
            CandidateListDAO dao = new CandidateListDAO();
            dao.getProvinceList(inputBean);
            dao.getDistrictList(inputBean);
            dao.getLAList(inputBean);
            dao.getWardList(inputBean);
            dao.getPartyList(inputBean);

            List<Type> typeList = new ArrayList<Type>();

            Type list = new Type();
            list.setCode("L1");
            list.setDescription("List 1");
            typeList.add(list);
            list = new Type();
            list.setCode("L2");
            list.setDescription("List 2");
            typeList.add(list);

            inputBean.setTypeList(typeList);

        } catch (Exception e) {
            addActionError("Candidate_List error occurred while processing");
            Logger.getLogger(CandidateListAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }

    public String load() {
        System.out.println("called CandidateListAction : assignLoad");
        String msg = "list";

        try {
            CandidateListDAO dao = new CandidateListDAO();

            System.out.println("ward " + inputBean.getWard());
            System.out.println("party " + inputBean.getParty());
            System.out.println("list " + inputBean.getType());

            dao.findCandidateList(inputBean);

        } catch (Exception e) {
            addActionError("Candidate_List assignload error occurred while processing");
            Logger.getLogger(CandidateListAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }

    public String findSelect() {
        System.out.println("called CandidateListAction: find");

        try {

            if (inputBean.getWard() != null && !inputBean.getWard().isEmpty()) {

                CandidateListDAO dao = new CandidateListDAO();

                dao.findCandidateList(inputBean);

            } else {
                inputBean.setMessage("Empty detail");
            }
        } catch (Exception ex) {
            addActionError("Candidate List find error occurred while processing");
            Logger.getLogger(CandidateListAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";
    }

    public String assign() {
        System.out.println("called CandidateListAction: assign");
        String message = "";

        try {

            if (inputBean.getWard() != null && !inputBean.getWard().isEmpty()) {

                System.out.println("type "+ inputBean.getType() );
                CandidateListDAO dao = new CandidateListDAO();
                dao.deleteCandidateListByWard(inputBean);
                message = dao.assignFields(inputBean);

                if (message.isEmpty()) {
                    inputBean.setMessage("");
                    addActionMessage("Candidate list assigned successfully");
                } else {
                    inputBean.setMessage(message);
                    addActionError(message);
                }

            } else {
                inputBean.setMessage("Empty detail");
            }
        } catch (Exception ex) {
            addActionError("Candidate List assign error occurred while processing");
            Logger.getLogger(CandidateListAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "list";
    }

    //    ================ drop down load ===================
    public String findDistrict() throws Exception {
        System.out.println("called CandidateListAction: findDistrict from province");
        try {
            CandidateListDAO dao = new CandidateListDAO();

            if (inputBean.getProvince().equals("empty")) {
                dao.getProvinceList(inputBean);
                dao.getDistrictList(inputBean);
                dao.getLAList(inputBean);
                dao.getWardList(inputBean);
            } else {
                dao.getProvinceList(inputBean);
                dao.getDistrictList(inputBean);
                dao.getLAList(inputBean);
                dao.getWardList(inputBean);
                dao.getDistrictListFromProvince(inputBean);
            }

        } catch (Exception e) {
            System.out.println("Find District " + e);
            Logger.getLogger(CandidateListAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    public String findLA() throws Exception {
        System.out.println("called CandidateListAction: findLA from District");
        try {
            CandidateListDAO dao = new CandidateListDAO();

            if (inputBean.getDistrict().equals("empty")) {
                dao.getProvinceList(inputBean);
                dao.getDistrictList(inputBean);
                dao.getLAList(inputBean);
                dao.getWardList(inputBean);
            } else {
                dao.getProvinceList(inputBean);
                dao.getDistrictList(inputBean);
                dao.getLAList(inputBean);
                dao.getWardList(inputBean);
                dao.getLAListFromDistrict(inputBean);
            }

        } catch (Exception e) {
            System.out.println("Find LA " + e);
            Logger.getLogger(CandidateListAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    public String findWard() throws Exception {
        System.out.println("called CandidateListAction: findWard from LA");
        try {
            CandidateListDAO dao = new CandidateListDAO();

            if (inputBean.getLa().equals("empty")) {
                dao.getProvinceList(inputBean);
                dao.getDistrictList(inputBean);
                dao.getLAList(inputBean);
                dao.getWardList(inputBean);
            } else {
                dao.getProvinceList(inputBean);
                dao.getDistrictList(inputBean);
                dao.getLAList(inputBean);
                dao.getWardList(inputBean);
                dao.getWardListFromLA(inputBean);
            }

        } catch (Exception e) {
            System.out.println("Find ward " + e);
            Logger.getLogger(CandidateListAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    public String findAll() throws Exception {
        System.out.println("called CandidateListAction: findAll from ward");
        try {
            CandidateListDAO dao = new CandidateListDAO();

            System.out.println("ward " + inputBean.getLa());

            if (inputBean.getWard().equals("empty")) {
                dao.getProvinceList(inputBean);
                dao.getDistrictList(inputBean);
                dao.getLAList(inputBean);
                dao.getWardList(inputBean);
            } else {
                dao.getProvinceList(inputBean);
                dao.getDistrictList(inputBean);
                dao.getLAList(inputBean);
                dao.getWardList(inputBean);
                dao.getAllListFromWard(inputBean);
            }

        } catch (Exception e) {
            System.out.println("Find all " + e);
            Logger.getLogger(CandidateListAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

}
