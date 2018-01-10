/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.action;

import com.election.bean.SearchPartyInputBean;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        } catch (Exception e) {
            addActionError("Search party error occurred while processing");
            Logger.getLogger(SearchPartyAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }

}
