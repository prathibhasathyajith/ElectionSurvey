/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.dao;

import com.election.bean.LoginInputBean;
import com.election.common.dao.CommonDAO;
import com.election.listener.HibernateInit;
import com.election.mapping.Candidate;
import com.election.mapping.Party;
import com.election.mapping.PartyLa;
import com.election.mapping.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author prathibha
 */
public class LoginDAO {

    public String checkUser(LoginInputBean inputBean) throws Exception {
        List<User> laList = new ArrayList<User>();
        Session session = null;
        String des = "";
        try {
            session = HibernateInit.sessionFactory.openSession();

//            String hash = this.HashSHA256(inputBean.getLoginPassword());
            String hql = "from User as t where t.username =:username and LOWER(t.password)=:password and t.userType=:userType  order by Upper(t.username) asc";
            Query query = session.createQuery(hql).setString("username", inputBean.getLoginUserName())
                    //                    .setString("password", hash.toLowerCase())
                    .setString("password", inputBean.getLoginPassword())
                    .setString("userType", "USR");

            laList = (List<User>) query.list();

            if (laList.size() > 0) {
                if (laList.get(0).getUserStatus().equals("ACT")) {
                    des = "";
                } else if (laList.get(0).getUserStatus().equals("NEW")) {
                    des = "new";
                } else if (laList.get(0).getUserStatus().equals("DEACT")) {
                    des = "deact";
                }
            } else {
                des = "error";
            }

        } catch (Exception e) {
            des = "error";
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }

        return des;
    }

    public String HashSHA256(String password) throws NoSuchAlgorithmException {
        String hash = "";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public String checkParty(LoginInputBean inputBean) throws Exception {
        List<PartyLa> partyLa = new ArrayList<PartyLa>();
        Session sessionH = null;
        String des = "";
        try {
            sessionH = HibernateInit.sessionFactory.openSession();

//            String hash = this.HashSHA256(inputBean.getLoginPassword());
            String hql = "from PartyLa as p where p.username =:username and LOWER(p.password)=:password order by Upper(p.username) asc";
            Query query = sessionH.createQuery(hql).setString("username", inputBean.getLoginUserName())
                    //                    .setString("password", hash.toLowerCase())
                    .setString("password", inputBean.getLoginPassword());

            partyLa = (List<PartyLa>) query.list();

            if (partyLa.size() > 0) {
                
                //set candidate name
                HttpSession session = ServletActionContext.getRequest().getSession(true);
                session.setAttribute("PARTYLAOBJECT", partyLa.get(0));
                Party party = CommonDAO.getPartyID(partyLa.get(0).getPartyCode());
                session.setAttribute("PARTYOBJECT", party);
                
                if (partyLa.get(0).getStatus().equals("ACT")) {
                    des = "PAR";
                } else if (partyLa.get(0).getStatus().equals("DEACT")) {
                    des = "deact";
                }
            } else {
                des = "error";
            }

        } catch (Exception e) {
            des = "error";
            throw e;
        } finally {
            try {
                sessionH.flush();
                sessionH.close();
            } catch (Exception e) {
                throw e;
            }
        }

        return des;
    }

    public String checkCandidate(LoginInputBean inputBean) throws Exception {
        List<Candidate> candidate = new ArrayList<Candidate>();
        Session sessionH = null;
        String des = "";
        try {
            sessionH = HibernateInit.sessionFactory.openSession();

//            String hash = this.HashSHA256(inputBean.getLoginPassword());
            String hql = "from Candidate as c where c.username =:username and LOWER(c.password)=:password order by Upper(c.username) asc";
            Query query = sessionH.createQuery(hql).setString("username", inputBean.getLoginUserName())
                    //                    .setString("password", hash.toLowerCase())
                    .setString("password", inputBean.getLoginPassword());

            candidate = (List<Candidate>) query.list();

            if (candidate.size() > 0) {

                //set candidate name
                HttpSession session = ServletActionContext.getRequest().getSession(true);
                session.setAttribute("CANDIDATEOBJECT", candidate.get(0));

                if (candidate.get(0).getStatus().equals("ACT")) {
                    des = "CAN";
                } else if (candidate.get(0).getStatus().equals("DEACT")) {
                    des = "deact";
                }
            } else {
                des = "error";
            }

        } catch (Exception e) {
            des = "error";
            throw e;
        } finally {
            try {
                sessionH.flush();
                sessionH.close();
            } catch (Exception e) {
                throw e;
            }
        }

        return des;
    }
}
