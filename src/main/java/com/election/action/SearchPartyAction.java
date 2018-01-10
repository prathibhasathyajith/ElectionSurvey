/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.action;

import com.election.bean.CountVoteSummary;
import com.election.bean.SearchPartyInputBean;
import com.election.dao.SearchPartyDAO;
import com.election.mapping.Party;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author prathibha_s
 */
public class SearchPartyAction extends ActionSupport implements ModelDriven<Object> {

    SearchPartyInputBean inputBean = new SearchPartyInputBean();

    @Override
    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called SearchPartyAction : execute");
        return SUCCESS;
    }

    public String view() {
        System.out.println("called SearchPartyAction : view");
        String msg = "view";
        try {
            SearchPartyDAO dao = new SearchPartyDAO();
            dao.getProvinceList(inputBean);
            dao.getDistrictList(inputBean);
            dao.getLAList(inputBean);

        } catch (Exception e) {
            addActionError("Search party error occurred while processing");
            Logger.getLogger(SearchPartyAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }

    public String load() {
        System.out.println("called SearchPartyAction : view");
        String msg = "list";
        String message = "";
        try {

            message = this.validateInputs();
            if (message.isEmpty()) {
                HttpSession session = ServletActionContext.getRequest().getSession(true);
//                String sysType = (String) session.getAttribute("SYSTEMUSERTYPE");
//                PartyLa partyLaObject = (PartyLa) session.getAttribute("PARTYLAOBJECT");
                Party partyObject = (Party) session.getAttribute("PARTYOBJECT");

                SearchPartyDAO dao = new SearchPartyDAO();
                List<CountVoteSummary> dataListDetails = dao.getDetailsParty(partyObject.getPartyCode(), inputBean);
                List<CountVoteSummary> dataList = dao.getFullVoteCountParty(inputBean);
                List<CountVoteSummary> fullDataList = dao.getFullDataParty(dataListDetails, dataList);

                inputBean.setFullCount(fullDataList.size()+"");
                inputBean.setCountList(fullDataList);
            } else {
                addActionError(message);
                inputBean.setMessage(message);
            }
        } catch (Exception e) {
            addActionError("Search party error occurred while processing");
            Logger.getLogger(SearchPartyAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }

    //    ================ drop down load ===================
    public String findDistrict() throws Exception {
        System.out.println("called SearchPartyAction: findDistrict from province");
        try {
            SearchPartyDAO dao = new SearchPartyDAO();

            if (inputBean.getProvince().equals("empty")) {
                dao.getProvinceList(inputBean);
                dao.getDistrictList(inputBean);
                dao.getLAList(inputBean);
            } else {
                dao.getProvinceList(inputBean);
                dao.getDistrictList(inputBean);
                dao.getLAList(inputBean);
                dao.getDistrictListFromProvince(inputBean);
            }

        } catch (Exception e) {
            System.out.println("Find District " + e);
            Logger.getLogger(SearchPartyAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    public String findLA() throws Exception {
        System.out.println("called SearchPartyAction: findLA from District");
        try {
            SearchPartyDAO dao = new SearchPartyDAO();

            if (inputBean.getDistrict().equals("empty")) {
                dao.getProvinceList(inputBean);
                dao.getDistrictList(inputBean);
                dao.getLAList(inputBean);
            } else {
                dao.getProvinceList(inputBean);
                dao.getDistrictList(inputBean);
                dao.getLAList(inputBean);
                dao.getLAListFromDistrict(inputBean);
            }

        } catch (Exception e) {
            System.out.println("Find LA " + e);
            Logger.getLogger(SearchPartyAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }
    
    public String findAll() throws Exception {
        System.out.println("called SearchPartyAction: findAll from ward");
        try {
            SearchPartyDAO dao = new SearchPartyDAO();

            System.out.println("la " + inputBean.getLa());

            if (inputBean.getLa().equals("empty")) {
                dao.getProvinceList(inputBean);
                dao.getDistrictList(inputBean);
                dao.getLAList(inputBean);
            } else {
                dao.getProvinceList(inputBean);
                dao.getDistrictList(inputBean);
                dao.getLAList(inputBean);
                dao.getALLFromLA(inputBean);
            }

        } catch (Exception e) {
            System.out.println("Find all " + e);
            Logger.getLogger(SearchPartyAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    private String validateInputs() {
        String message = "";
//        if (inputBean.getProvince() == null || inputBean.getProvince().trim().isEmpty()) {
//            message = "Province cannot be empty";
//        } else if (inputBean.getDistrict() == null || inputBean.getDistrict().trim().isEmpty()) {
//            message = "District cannot be empty";
//        } else 
        if (inputBean.getLa() == null || inputBean.getLa().trim().isEmpty()) {
            message = "Local authority cannot be empty";
        }
        return message;
    }

}
