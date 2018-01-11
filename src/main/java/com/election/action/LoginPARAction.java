/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.action;

import com.election.bean.CountVoteSummary;
import com.election.bean.LoginPARInputBean;
import com.election.common.dao.CommonDAO;
import com.election.dao.LoginPARDAO;
import com.election.mapping.LocalAuthority;
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

            // all local athorities
            List<CountVoteSummary> dataListDetails = dao.getDetailsParty(partyObject.getPartyCode(), inputBean);
            inputBean.setLa((String) session.getAttribute("PARTYLAOBJECTLOCALAUTHORITY"));

            // specific local au 
            LocalAuthority la = CommonDAO.getLAFromCode(inputBean.getLa());
            

            List<CountVoteSummary> dataListDetailsLA = dao.getDetailsPartyLA(partyObject.getPartyCode(), inputBean);
            List<CountVoteSummary> dataList = dao.getFullVoteCountParty(inputBean);
            List<CountVoteSummary> fullDataList = dao.getFullDataParty(dataListDetailsLA, dataList);

            inputBean.setLa(la.getDescription());
            inputBean.setCountList(fullDataList);

        } catch (Exception e) {
            addActionError("LoginPARAction error occurred while processing");
            Logger.getLogger(LoginPARAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }
}
