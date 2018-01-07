/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.action;

import com.election.bean.LoginInputBean;
import com.election.dao.LoginDAO;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author prathibha
 */
public class LoginAction extends ActionSupport implements ModelDriven<Object> {

    LoginInputBean inputBean = new LoginInputBean();

    @Override
    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called LoginAction : execute");
        return SUCCESS;
    }

    public String Check() throws Exception {
        System.out.println("called LoginAction : Check");

        String msg = "loginsuccess";

        try {

            HttpSession sessionPrevious = ServletActionContext.getRequest().getSession(false);
            if (sessionPrevious != null) {
                sessionPrevious.invalidate();
            }
            //set user to the session
            HttpSession session = ServletActionContext.getRequest().getSession(true);
            session.setAttribute("SYSTEMUSER", inputBean.getLoginUserName());
            session.setAttribute("SYSTEMUSERPASS", inputBean.getLoginPassword());
            session.setAttribute("SYSTEMUSERTYPE", inputBean.getUserType());

            LoginDAO dao = new LoginDAO();
            String m = "";
            if (inputBean.getUserType().equals("user")) {
                m = dao.checkUser(inputBean);
            } else if (inputBean.getUserType().equals("party")) {
                m = dao.checkParty(inputBean);
            } else {
                m = "NOT";
            }

            System.out.println("username " + inputBean.getLoginUserName());
            System.out.println("password " + inputBean.getLoginPassword());
            System.out.println("userType " + inputBean.getUserType());
            System.out.println("userTypeR " + inputBean.getUserTypeRadio());

            if (m.equals("")) {
                msg = "loginsuccess";
            } else if (m.equals("new")) {
                inputBean.setUsername(inputBean.getLoginUserName());
                msg = "firstattempt";
            } else if (m.equals("deact")) {
                addActionError("User not Activated");
                msg = "loginmessage";
            } else if (m.equals("NOT")) {
                addActionError("Please select user type");
                msg = "loginmessage";
            } else {
                addActionError("Username or Password incorrect");
                msg = "loginmessage";
            }

            System.out.println("ms 2 " + m);

        } catch (Exception e) {
            System.out.println("check " + e);
            Logger.getLogger(LoginAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("Error Occured");
            msg = "loginmessage";
        }

        return msg;

    }

    public String logout() {
        String result = "";
        System.out.println("called LoginAction : logout");
        try {
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            session.setAttribute("SYSTEMUSERTYPE", null);
            session.invalidate();
            result = "logout";
        } catch (Exception e) {
            System.out.println("Logout " + e);
            Logger.getLogger(LoginAction.class.getName()).log(Level.SEVERE, null, e);
            result = "loginsuccess";
        }

        return result;
    }

}
