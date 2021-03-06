/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.action;

import com.election.bean.CountVoteSummary;
import com.election.bean.Type;
import com.election.bean.VotingSummaryInputBean;
import com.election.dao.CandidateListDAO;
import com.election.dao.VotingSummaryDAO;
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
public class VotingSummaryAction extends ActionSupport implements ModelDriven<Object> {

    VotingSummaryInputBean inputBean = new VotingSummaryInputBean();

    @Override
    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called VotingSummaryAction : execute");
        return SUCCESS;
    }

    public String view() {
        System.out.println("called VotingSummaryAction : view");
        String msg = "view";
        try {
            List<Type> typeList = new ArrayList<Type>();

            Type list = new Type();
            list.setCode("USER");
            list.setDescription("Candidate");
            typeList.add(list);
            list = new Type();
            list.setCode("PARTY");
            list.setDescription("Party");
            typeList.add(list);

            inputBean.setTypeList(typeList);

            VotingSummaryDAO dao = new VotingSummaryDAO();
            dao.getProvinceList(inputBean);
            dao.getDistrictList(inputBean);
            dao.getLAList(inputBean);
            dao.getWardList(inputBean);

        } catch (Exception e) {
            addActionError("Voting Summary error occurred while processing");
            Logger.getLogger(VotingSummaryAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }

    public String load() {
        System.out.println("called VotingSummaryAction : load");
        String msg = "list";
        String message = "";
        try {

            message = this.validateInputs();
            System.out.println("message " + message);
            if (message.isEmpty()) {
                if (inputBean.getLa()!= null && !inputBean.getLa().isEmpty()) {

                    System.out.println("ward " + inputBean.getWard());
                    System.out.println("type " + inputBean.getType());
                    System.out.println("la " + inputBean.getLa());

                    VotingSummaryDAO dao = new VotingSummaryDAO();
                    List<CountVoteSummary> dataList = dao.loadDeatils(inputBean);
                    inputBean.setCountList(dataList);

                } else {
                    inputBean.setMessage("Empty detail");
                }
            } else {
                addActionError(message);
                inputBean.setMessage(message);
            }

        } catch (Exception e) {
            addActionError("Voting Summary load error occurred while processing");
            Logger.getLogger(VotingSummaryAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }

    //    ================ drop down load ===================
    public String findDistrict() throws Exception {
        System.out.println("called VotingSummaryAction: findDistrict from province");
        try {
            VotingSummaryDAO dao = new VotingSummaryDAO();

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
            Logger.getLogger(VotingSummaryAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    public String findLA() throws Exception {
        System.out.println("called VotingSummaryAction: findLA from District");
        try {
            VotingSummaryDAO dao = new VotingSummaryDAO();

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
            Logger.getLogger(VotingSummaryAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    public String findWard() throws Exception {
        System.out.println("called VotingSummaryAction: findWard from LA");
        try {
            VotingSummaryDAO dao = new VotingSummaryDAO();

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
            Logger.getLogger(VotingSummaryAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    public String findAll() throws Exception {
        System.out.println("called VotingSummaryAction: findAll from ward");
        try {
            VotingSummaryDAO dao = new VotingSummaryDAO();

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
            Logger.getLogger(VotingSummaryAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    private String validateInputs() {
        String message = "";
        if (inputBean.getLa()== null || inputBean.getLa().trim().isEmpty()) {
            message = "Local authority cannot be empty";
        } else if (inputBean.getType() == null || inputBean.getType().trim().isEmpty()) {
            message = "Search type cannot be empty";
        }
        return message;
    }

}
