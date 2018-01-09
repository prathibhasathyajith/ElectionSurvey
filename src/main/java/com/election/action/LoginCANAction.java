/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.action;

import com.election.bean.LoginInputBean;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prathibha_s
 */
public class LoginCANAction extends ActionSupport implements ModelDriven<Object>{
    LoginInputBean inputBean = new LoginInputBean();

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
            System.out.println("call");

        } catch (Exception e) {
            addActionError("LoginCANAction error occurred while processing");
            Logger.getLogger(LoginCANAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }
}
