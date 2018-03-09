/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.action;

//import com.election.bean.CountVoteSummary;
import com.election.bean.CountVoteSummary;
import com.election.bean.DataServiceSummary;
import com.election.bean.ServiceSummaryInputBean;
import com.election.bean.Type;
import com.election.dao.ServiceSummaryDAO;
//import com.election.bean.VotingSummaryInputBean;
//import com.election.dao.VotingSummaryDAO;
import static com.opensymphony.xwork2.Action.SUCCESS;
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
public class ServiceSummaryAction extends ActionSupport implements ModelDriven<Object>{
    ServiceSummaryInputBean inputBean = new ServiceSummaryInputBean();

    @Override
    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called ServiceSummaryAction : execute");
        return SUCCESS;
    }

    public String view() {
        System.out.println("called ServiceSummaryAction : view");
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

            ServiceSummaryDAO dao = new ServiceSummaryDAO();
            dao.getProvinceList(inputBean);
            dao.getDistrictList(inputBean);
            dao.getLAList(inputBean);
            dao.getWardList(inputBean);

        } catch (Exception e) {
            addActionError("Voting Summary error occurred while processing");
            Logger.getLogger(ServiceSummaryAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }

    public String load() {
        System.out.println("called ServiceSummaryAction : load");
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

                    ServiceSummaryDAO dao = new ServiceSummaryDAO();
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
            Logger.getLogger(ServiceSummaryAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }
    
    public String loadService() {
        System.out.println("called ServiceSummaryAction : load");
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

                    ServiceSummaryDAO dao = new ServiceSummaryDAO();
                    List<DataServiceSummary> dataList = dao.loadServiceDeatils(inputBean);
                    System.out.println("table list "+ dataList);
                    
                    inputBean.setTableList(dataList);

                } else {
                    inputBean.setMessage("Empty detail");
                }
            } else {
                addActionError(message);
                inputBean.setMessage(message);
            }

        } catch (Exception e) {
            addActionError("Voting Summary load error occurred while processing");
            Logger.getLogger(ServiceSummaryAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }

    //    ================ drop down load ===================
    public String findDistrict() throws Exception {
        System.out.println("called ServiceSummaryAction: findDistrict from province");
        try {
            ServiceSummaryDAO dao = new ServiceSummaryDAO();

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
            Logger.getLogger(ServiceSummaryAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    public String findLA() throws Exception {
        System.out.println("called ServiceSummaryAction: findLA from District");
        try {
            ServiceSummaryDAO dao = new ServiceSummaryDAO();

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
            Logger.getLogger(ServiceSummaryAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    public String findWard() throws Exception {
        System.out.println("called ServiceSummaryAction: findWard from LA");
        try {
            ServiceSummaryDAO dao = new ServiceSummaryDAO();

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
            Logger.getLogger(ServiceSummaryAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    public String findAll() throws Exception {
        System.out.println("called ServiceSummaryAction: findAll from ward");
        try {
            ServiceSummaryDAO dao = new ServiceSummaryDAO();

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
            Logger.getLogger(ServiceSummaryAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    private String validateInputs() {
        String message = "";
        if (inputBean.getLa()== null || inputBean.getLa().trim().isEmpty()) {
            message = "Local authority cannot be empty";
        } else if (inputBean.getWard()== null || inputBean.getWard().trim().isEmpty()) {
            message = "Ward cannot be empty";
        }
        return message;
    }
}
