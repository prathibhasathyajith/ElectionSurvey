/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.action;

import com.election.bean.PartyInputBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 *
 * @author prathibha
 */
public class PartyAction extends ActionSupport implements ModelDriven<Object> {

    PartyInputBean inputBean = new PartyInputBean();

    @Override
    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called PartyAction : execute");
        return SUCCESS;
    }
    
    public String view(){
        System.out.println("called PartyAction : execute");
        String msg = "view";
        return msg;
    }

}
