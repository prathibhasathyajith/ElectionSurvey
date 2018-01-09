/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.action;

import com.election.bean.CountVoteSummary;
import com.election.bean.LoginPARInputBean;
import com.election.dao.LoginPARDAO;
import com.election.mapping.Party;
import com.election.mapping.PartyLa;
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
public class LoginPARAction extends ActionSupport implements ModelDriven<Object> {

    LoginPARInputBean inputBean = new LoginPARInputBean();

    @Override
    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called LoginPARAction : execute");
        return SUCCESS;
    }

    public String view() {
        System.out.println("called LoginPARAction : view");
        String msg = "view";
        try {
            HttpSession session = ServletActionContext.getRequest().getSession(true);
            String sysType = (String) session.getAttribute("SYSTEMUSERTYPE");
            PartyLa partyLaObject = (PartyLa) session.getAttribute("PARTYLAOBJECT");
            Party partyObject = (Party) session.getAttribute("PARTYOBJECT");
            
            LoginPARDAO dao = new LoginPARDAO();
            List<CountVoteSummary> dataListDetails = dao.getDetailsParty(partyObject.getPartyCode(), inputBean);
            List<CountVoteSummary> dataList = dao.getFullVoteCountParty(inputBean);
            List<CountVoteSummary> fullDataList = dao.getFullDataParty(dataListDetails,dataList);
            
            System.out.println("sysType "+ sysType);
            System.out.println("partyLaObject "+ partyLaObject.getUsername());
            System.out.println("partyObject "+ partyObject.getPartyCode());
            System.out.println("partyObject "+ partyObject.getName());
            
            System.out.println("dataListDetails " + dataListDetails.get(0).getCount());
            System.out.println("dataListDetails wd " + dataListDetails.get(0).getColumName1());
            System.out.println("dataListDetails  " + dataListDetails.get(1).getCount());
            System.out.println("dataListDetails wd " + dataListDetails.get(1).getColumName1());
            

            
            System.out.println("dataList " + fullDataList.get(0).getCount());
            System.out.println("dataList wd " + fullDataList.get(0).getColumName1());
            System.out.println("dataList  " + fullDataList.get(1).getCount());
            System.out.println("dataList wd " + fullDataList.get(1).getColumName1());
            
            System.out.println("fullDataList  " + fullDataList.get(0).getCount());
            System.out.println("fullDataList wd " + fullDataList.get(0).getColumName1());
            System.out.println("fullDataList per " + fullDataList.get(0).getPercentage1());
            System.out.println("fullDataList  " + fullDataList.get(1).getCount());
            System.out.println("fullDataList wd " + fullDataList.get(1).getColumName1());
            System.out.println("fullDataList per " + fullDataList.get(1).getPercentage1());
            
            inputBean.setCountList(fullDataList);
            
            System.out.println("call");

        } catch (Exception e) {
            addActionError("LoginPARAction error occurred while processing");
            Logger.getLogger(LoginPARAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }
}
