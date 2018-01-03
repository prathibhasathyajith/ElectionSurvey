/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.action;

import com.election.bean.PartyLAInputBean;
import com.election.bean.Type;
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
public class PartyLAAction extends ActionSupport implements ModelDriven<Object>{
    PartyLAInputBean inputBean = new PartyLAInputBean();
    
    @Override
    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called PartyLAAction : execute");
        return SUCCESS;
    }
    
    public String view() {
        System.out.println("called PartyLAAction : view");
        String msg = "view";
        try {
          

        } catch (Exception e) {
            addActionError("PartyLA error occurred while processing");
            Logger.getLogger(PartyLAAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }
}
