/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.action;

import com.election.bean.CountVoteSummary;
import com.election.bean.LoginCANInputBean;
import com.election.dao.LoginCANDAO;
import com.election.mapping.Candidate;
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
public class LoginCANAction extends ActionSupport implements ModelDriven<Object> {

    LoginCANInputBean inputBean = new LoginCANInputBean();

    @Override
    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called LoginCANAction : execute");
        return SUCCESS;
    }

    public String view() {
        System.out.println("called LoginCANAction : view");
        String msg = "view";
        try {
            HttpSession session = ServletActionContext.getRequest().getSession(true);
            String sysType = (String) session.getAttribute("SYSTEMUSERTYPE");
            Candidate candidateObject = (Candidate) session.getAttribute("CANDIDATEOBJECT");

            System.out.println("sysType " + sysType);
            System.out.println("candidateObject " + candidateObject.getUsername());
            System.out.println("candidateObject " + candidateObject.getPartyCode());

            LoginCANDAO dao = new LoginCANDAO();
            List<CountVoteSummary> dataListDetails = dao.getDetailsCandidate(candidateObject.getUsername(), inputBean);
            List<CountVoteSummary> dataList = dao.getPercentageCandidate(candidateObject.getUsername(), candidateObject.getWard().getCode(), inputBean);
            List<CountVoteSummary> fullDataList = dao.getFullDataCandidate(dataListDetails, inputBean.getCandidateVotePercentage());

            System.out.println("dataListDetails " + dataListDetails.get(0).getCount());
            System.out.println("dataListDetails " + dataListDetails.get(0).getColumName1());
            System.out.println("dataListDetails " + dataListDetails.get(0).getColumName2());
            System.out.println("dataListDetails " + dataListDetails.get(0).getColumName3());

            inputBean.setCountList(fullDataList);

            System.out.println("dataList " + inputBean.getCandidateFullVoteCount());
            System.out.println("dataList " + inputBean.getCandidateVotePercentage());
            System.out.println("dataList " + dataList.get(0).getColumName2());
            System.out.println("dataList " + dataList.get(0).getColumName3());

        } catch (Exception e) {
            addActionError("LoginCANAction error occurred while processing");
            Logger.getLogger(LoginCANAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }
}
