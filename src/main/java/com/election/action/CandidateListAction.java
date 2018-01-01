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

            List<CandidateListBean> list_1 = new ArrayList<CandidateListBean>();
            List<CandidateListBean> list_2 = new ArrayList<CandidateListBean>();

            CandidateListBean cl = new CandidateListBean();
            cl.setName("test-1");
            cl.setCandidate("id-1");
            list_1.add(cl);
            cl = new CandidateListBean();
            cl.setName("test-2");
            cl.setCandidate("id-2");
            list_1.add(cl);
//            inputBean.setList_1(list_1);

            cl = new CandidateListBean();
            cl.setName("test-3");
            cl.setCandidate("id-3");
            list_2.add(cl);
            cl = new CandidateListBean();
            cl.setName("test-4");
            cl.setCandidate("id-4");
            list_2.add(cl);
//            inputBean.setList_2(list_2);

//            inputBean.setWard("KURKLUCKPT");
//            inputBean.setParty("test");
            System.out.println("ward " + inputBean.getWard());
            System.out.println("party " + inputBean.getParty());

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

            System.out.println("ward " + inputBean.getWard());
            System.out.println("party " + inputBean.getParty());

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

        try {

            System.out.println("ward " + inputBean.getWard());
            System.out.println("party " + inputBean.getParty());

            System.out.println("list 1 " + inputBean.getList1());
            System.out.println("list 2 " + inputBean.getList2());

            if (inputBean.getWard() != null && !inputBean.getWard().isEmpty()) {

                CandidateListDAO dao = new CandidateListDAO();

//                dao.findCandidateList(inputBean);
            } else {
                inputBean.setMessage("Empty detail");
            }
        } catch (Exception ex) {
            addActionError("Candidate List assign error occurred while processing");
            Logger.getLogger(CandidateListAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "list";
    }
}
