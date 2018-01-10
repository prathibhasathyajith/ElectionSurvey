/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.action;

import com.election.bean.SearchCandidateInputBean;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prathibha_s
 */
public class SearchCandidateAction extends ActionSupport implements ModelDriven<Object> {

    SearchCandidateInputBean inputBean = new SearchCandidateInputBean();

    @Override
    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called SearchCandidateAction : execute");
        return SUCCESS;
    }

    public String view() {
        System.out.println("called SearchCandidateAction : view");
        String msg = "view";
        try {
        } catch (Exception e) {
            addActionError("Search candidate Party error occurred while processing");
            Logger.getLogger(SearchCandidateAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }

}
