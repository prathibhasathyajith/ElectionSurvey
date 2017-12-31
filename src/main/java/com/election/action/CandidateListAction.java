/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.action;

import com.election.bean.CandidateListInputBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 *
 * @author prathibha
 */
public class CandidateListAction extends ActionSupport implements ModelDriven<Object>{
    
    CandidateListInputBean inputBean = new CandidateListInputBean();
    
    @Override
    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called CandidateListAction : execute");
        return SUCCESS;
    }
}
