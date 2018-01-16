/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.dao;

import com.election.bean.PartyLABean;
import com.election.bean.PartyLAInputBean;
import com.election.common.dao.CommonDAO;
import com.election.listener.HibernateInit;
import com.election.mapping.LocalAuthority;
import com.election.mapping.Party;
import com.election.mapping.PartyLa;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author prathibha_s
 */
public class PartyLADAO {

    public List<PartyLABean> getSearchList(PartyLAInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<PartyLABean> dataList = new ArrayList<PartyLABean>();
        Session session = null;
        try {

            if (orderBy.equals("") || orderBy == null) {
                orderBy = "order by u.id desc";
            }

            long count = 0;

            String where = this.makeWhereClause(inputBean);

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(id) from PartyLa as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {

                String sqlSearch = "from PartyLa u where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    PartyLABean partyLABean = new PartyLABean();
                    PartyLa partyLa = (PartyLa) it.next();

                    try {
                        partyLABean.setId(partyLa.getId()+"");
                    } catch (NullPointerException npe) {
                        partyLABean.setId("--");
                    }
                    try {
                        partyLABean.setLaCode(partyLa.getLocalAuthority().getDescription());
                    } catch (NullPointerException npe) {
                        partyLABean.setLaCode("--");
                    }
                    try {
                        partyLABean.setPartyCode(partyLa.getPartyCode());
                    } catch (NullPointerException npe) {
                        partyLABean.setPartyCode("--");
                    }
                    try {
                        partyLABean.setContactNo(partyLa.getContactNo());
                    } catch (NullPointerException npe) {
                        partyLABean.setContactNo("--");
                    }
                    try {
                        partyLABean.setAddress(partyLa.getAddress());
                    } catch (NullPointerException npe) {
                        partyLABean.setAddress("--");
                    }
                    try {
                        partyLABean.setStatus(partyLa.getStatus());
                    } catch (NullPointerException npe) {
                        partyLABean.setStatus("--");
                    }
                    try {
                        partyLABean.setUsername(partyLa.getUsername());
                    } catch (NullPointerException npe) {
                        partyLABean.setUsername("--");
                    }

                    partyLABean.setFullCount(count);

                    dataList.add(partyLABean);
                }
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
        return dataList;
    }

    public String insertPartyLA(PartyLAInputBean inputBean) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {

            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            PartyLa pla = CommonDAO.getIDFromLA_Party(inputBean.getLa(), inputBean.getParty());

            System.out.println("pla " + pla);

            if (pla == null) {
                txn = session.beginTransaction();

                PartyLa partyLa = new PartyLa();

                LocalAuthority la = (LocalAuthority) session.get(LocalAuthority.class, inputBean.getLa().trim());
                partyLa.setLocalAuthority(la);

                Party pty = CommonDAO.getPartyID(inputBean.getParty().trim());
                Party party = (Party) session.get(Party.class, pty.getPartyId());
                
                partyLa.setPartyCode(inputBean.getParty());

                partyLa.setContactNo(inputBean.getContactNo());
                partyLa.setAddress(inputBean.getAddress());
                partyLa.setStatus(inputBean.getStatus());

                session.save(partyLa);
                txn.commit();

            } else {
                message = "Record already exists";

            }

        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return message;

    }

    public String updatePartyLA(PartyLAInputBean inputBean) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {

            CommonDAO commonDAO = new CommonDAO();
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = commonDAO.getSystemDate(session);

            PartyLa pla = CommonDAO.getIDFromLA_Party(inputBean.getLa(), inputBean.getParty());

            System.out.println("pla up " + pla.getId());

            PartyLa u = (PartyLa) session.get(PartyLa.class, pla.getId());
            Party pty = CommonDAO.getPartyID(inputBean.getParty().trim());

            if (u != null) {

                LocalAuthority la = (LocalAuthority) session.get(LocalAuthority.class, inputBean.getLa().trim());
                u.setLocalAuthority(la);

                Party party = (Party) session.get(Party.class, pty.getPartyId());
                u.setPartyCode(inputBean.getParty());

                u.setContactNo(inputBean.getContactNo());
                u.setAddress(inputBean.getAddress());
                u.setStatus(inputBean.getStatus());

                session.update(u);
                txn.commit();

            } else {
                message = "Record does not exists";
            }
        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return message;
    }

    public PartyLa findPartyLaById(String id) throws Exception {
        PartyLa partyLa = null;
        Session session = null;

        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from PartyLa as p where p.id=:id";
            Query query = session.createQuery(sql).setInteger("id", Integer.parseInt(id));
            partyLa = (PartyLa) query.list().get(0);

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
        return partyLa;
    }

    public String deletePartyLA(PartyLAInputBean inputBean) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            PartyLa u = (PartyLa) session.get(PartyLa.class, Integer.parseInt(inputBean.getId()));
            if (u != null) {

                session.delete(u);
                txn.commit();

            } else {
                message = "Record does not exists";
            }
        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return message;
    }

    public void getPartyList(PartyLAInputBean inputBean) throws Exception {

        List<Party> partyList = new ArrayList<Party>();
        List<Party> inputPList = new ArrayList<Party>();
        inputBean.setPartyList(inputPList);
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Party as p where p.status =:status order by p.description asc";
            Query query = session.createQuery(hql).setString("status", "ACT");
            partyList = (List<Party>) query.list();

            for (Iterator<Party> it = partyList.iterator(); it.hasNext();) {

                Party party = it.next();
                Party partyObj = new Party();
                partyObj.setPartyId(party.getPartyId());
                partyObj.setPartyCode(party.getPartyCode());
                partyObj.setName(party.getName());
                partyObj.setDescription(party.getDescription());
                inputBean.getPartyList().add(partyObj);
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
    }

    public void getLAList(PartyLAInputBean inputBean) throws Exception {

        List<LocalAuthority> localAuthorityList = new ArrayList<LocalAuthority>();
        List<LocalAuthority> inputLAList = new ArrayList<LocalAuthority>();
        inputBean.setLaList(inputLAList);
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from LocalAuthority as l order by l.description asc";
            Query query = session.createQuery(hql);
            localAuthorityList = (List<LocalAuthority>) query.list();

            for (Iterator<LocalAuthority> it = localAuthorityList.iterator(); it.hasNext();) {

                LocalAuthority localAuthority = it.next();
                LocalAuthority localAuthorityObj = new LocalAuthority();
                localAuthorityObj.setCode(localAuthority.getCode());
                localAuthorityObj.setDescription(localAuthority.getDescription());
                inputBean.getLaList().add(localAuthorityObj);
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
    }

    private String makeWhereClause(PartyLAInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getLa() != null && !inputBean.getLa().isEmpty()) {
            where += " and lower(u.localAuthority.code) like lower('%" + inputBean.getLa() + "%')";
        }
        if (inputBean.getParty() != null && !inputBean.getParty().isEmpty()) {
            where += " and lower(u.partyCode) like lower('%" + inputBean.getParty() + "%')";
        }
        return where;
    }

}
