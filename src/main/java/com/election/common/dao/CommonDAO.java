/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.common.dao;

import com.election.mapping.Party;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import com.election.listener.HibernateInit;
import com.election.mapping.Candidate;

/**
 *
 * @author prathibha
 */
public class CommonDAO {
    public static Date getSystemDate(Session session) throws Exception {
        Date sysDateTime = null;
        try {

            String sql = "SELECT NOW()";
            Query query = session.createSQLQuery(sql);
            List l = query.list();
            sysDateTime = (Date) l.get(0);
//            sysDateTime = Timestamp.valueOf(stime);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
        }
        return sysDateTime;
    }
    
    public static Party getPartyID(String partyCode) throws Exception {

        Party partyBean = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Party as s where s.partyCode =:partyCode";
            Query query = session.createQuery(sql).setString("partyCode", partyCode);
//            partyBean = (Party) query.list().get(0);
            
            if(query.list().size()>0){
                 partyBean = (Party) query.list().get(0);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return partyBean;
    }
    
    public static Candidate getCandidateID(String nic) throws Exception {

        Candidate candidateBean = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Candidate as c where c.nic =:nic";
            Query query = session.createQuery(sql).setString("nic", nic);
//            partyBean = (Party) query.list().get(0);
            
            if(query.list().size()>0){
                 candidateBean = (Candidate) query.list().get(0);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return candidateBean;
    }
}
