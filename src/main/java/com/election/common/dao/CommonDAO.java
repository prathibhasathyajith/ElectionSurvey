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
import com.election.mapping.CandidateList;
import com.election.mapping.LocalAuthority;
import com.election.mapping.PartyLa;
import com.election.mapping.ServiceList;
import com.election.mapping.Ward;

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

            if (query.list().size() > 0) {
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

            if (query.list().size() > 0) {
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
    
    public static Candidate getCandidateName(String username) throws Exception {
        
        Candidate candidateBean = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Candidate as c where c.username =:username";
            Query query = session.createQuery(sql).setString("username", username);
//            partyBean = (Party) query.list().get(0);

            if (query.list().size() > 0) {
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
    
    public static ServiceList getServiceListID(String code) throws Exception {
        
        ServiceList serviceList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from ServiceList as s where s.code =:code";
            Query query = session.createQuery(sql).setString("code", code);
//            partyBean = (Party) query.list().get(0);

            if (query.list().size() > 0) {
                serviceList = (ServiceList) query.list().get(0);
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
        return serviceList;
    }
    
    public static ServiceList getServiceID(String code) throws Exception {
        ServiceList serviceList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from ServiceList as s where s.code =:code";
            Query query = session.createQuery(sql).setString("code", code);
//            partyBean = (Party) query.list().get(0);

            if (query.list().size() > 0) {
                serviceList = (ServiceList) query.list().get(0);
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
        return serviceList;
    }
    
    public static LocalAuthority getLAFromCode(String code) throws Exception {
        LocalAuthority la = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from LocalAuthority as l where l.code =:code";
            Query query = session.createQuery(sql).setString("code", code);

            if (query.list().size() > 0) {
                la = (LocalAuthority) query.list().get(0);
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
        return la;
    }
    
    public static Ward getWardFromCode(String code) throws Exception {
        Ward ward = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ward as w where w.code =:code";
            Query query = session.createQuery(sql).setString("code", code);

            if (query.list().size() > 0) {
                ward = (Ward) query.list().get(0);
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
        return ward;
    }
    
    
    
    public static CandidateList getCandidateListID(String candidate) throws Exception {
        CandidateList candidateList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from CandidateList as c where c.candidate.candidateId =:candidateId";
            Query query = session.createQuery(sql).setInteger("candidateId", Integer.parseInt(candidate));
            
            if (query.list().size() > 0) {
                candidateList = (CandidateList) query.list().get(0);
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
        return candidateList;
    }
    
    public static PartyLa getIDFromLA_Party(String la, String party) throws Exception {
        PartyLa partyla = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from PartyLa as p where p.localAuthority.code =:laCode and p.partyCode =:partyCode";
            Query query = session.createQuery(sql)
                    .setString("laCode", la)
                    .setString("partyCode", party);

            if (query.list().size() > 0) {
                partyla = (PartyLa) query.list().get(0);
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
        return partyla;
    }
}
