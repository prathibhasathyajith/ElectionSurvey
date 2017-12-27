/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.dao;

import com.election.bean.PartyBean;
import com.election.bean.PartyInputBean;
import com.election.common.dao.CommonDAO;
import com.election.listener.HibernateInit;
import com.election.mapping.Party;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author prathibha
 */
public class PartyDAO {
    
    public List<PartyBean> getSearchList(PartyInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<PartyBean> dataList = new ArrayList<PartyBean>();
        Session session = null;
        try {

            long count = 0;

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(partyCode) from Party as u ";
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {

                String sqlSearch = "from Party u " + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    PartyBean partyBean = new PartyBean();
                    Party party = (Party) it.next();

                    try {
                        partyBean.setPartyId(party.getPartyId().toString());
                    } catch (NullPointerException npe) {
                        partyBean.setPartyId("--");
                    }
                    try {
                        partyBean.setPartyCode(party.getPartyCode());
                    } catch (NullPointerException npe) {
                        partyBean.setPartyCode("--");
                    }
                    try {
                        partyBean.setName(party.getName());
                    } catch (NullPointerException npe) {
                        partyBean.setName("--");
                    }
                    try {
                        partyBean.setDiscription(party.getDescription());
                    } catch (NullPointerException npe) {
                        partyBean.setDiscription("--");
                    }
                    try {
                        partyBean.setContactNo(party.getContactNo());
                    } catch (NullPointerException npe) {
                        partyBean.setContactNo("--");
                    }
                    try {
                        partyBean.setType(party.getType());
                    } catch (NullPointerException npe) {
                        partyBean.setType("--");
                    }
                    try {
                        partyBean.setEmail(party.getEmail());
                    } catch (NullPointerException npe) {
                        partyBean.setEmail("--");
                    }
                    try {
                        partyBean.setStatus(party.getStatus());
                    } catch (NullPointerException npe) {
                        partyBean.setStatus("--");
                    }
                    try {
                        partyBean.setAddress(party.getAddress());
                    } catch (NullPointerException npe) {
                        partyBean.setAddress("--");
                    }
                    
                    partyBean.setFullCount(count);

                    dataList.add(partyBean);
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

    public String insertParty(PartyInputBean inputBean) throws Exception {
        Session session = null;
        Transaction txn = null;
        FileInputStream fileInputStream = null;
        String message = "";

        try {

            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            Party pty = CommonDAO.getPartyID(inputBean.getPartyCode());
            
            System.out.println("party " + pty);

            if (pty == null) {
                txn = session.beginTransaction();

                Party party = new Party();
                party.setPartyCode(inputBean.getPartyCode().trim());
                party.setName(inputBean.getName().trim());
                party.setDescription(inputBean.getDescription().trim());
                party.setType(inputBean.getType().trim());
                party.setContactNo(inputBean.getContactNo().trim());
                party.setEmail(inputBean.getEmail().trim());
                party.setAddress(inputBean.getAddress().trim());
                party.setStatus(inputBean.getStatus().trim());

                /**
                 * insert logo
                 */
                File logoImgFile = inputBean.getLogoImg();
                byte[] bLogoFile = new byte[(int) logoImgFile.length()];
                try {
                    fileInputStream = new FileInputStream(logoImgFile);
                    fileInputStream.read(bLogoFile);
                    fileInputStream.close();

                    party.setImage(bLogoFile);
                } catch (Exception ex) {

                } finally {
                    try {
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        } else {
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                }
                session.save(party);
                txn.commit();
                //start newly changed
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

    public Party findPartyByCode(String partyCode) throws Exception {

        Party party = null;
        Session session = null;

        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Party as u where u.partyCode=:code";
            Query query = session.createQuery(sql).setString("code", partyCode);
            party = (Party) query.list().get(0);

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
        return party;

    }

    public boolean isexsitsImg(String code) throws Exception {
        Party party = new Party();
        boolean valid = true;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Party as u where u.partyCode=:code";
            Query query = session.createQuery(sql).setString("code", code);
            party = (Party) query.list().get(0);

            if (party.getImage() != null) {
                valid = false;
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
        return valid;

    }

    public String updateParty(PartyInputBean inputBean) throws Exception {
        Session session = null;
        Transaction txn = null;
        FileInputStream fileInputStream = null;
        String message = "";
        String imageName = "";

        try {

            CommonDAO commonDAO = new CommonDAO();
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = commonDAO.getSystemDate(session);

            Party pty = CommonDAO.getPartyID(inputBean.getPartyCode());

            Party u = (Party) session.get(Party.class, pty.getPartyId());

            if (u != null) {

                u.setPartyCode(inputBean.getPartyCode().trim());
                u.setName(inputBean.getName().trim());
                u.setDescription(inputBean.getDescription().trim());
                u.setType(inputBean.getType().trim());
                u.setContactNo(inputBean.getContactNo().trim());
                u.setEmail(inputBean.getEmail().trim());
                u.setAddress(inputBean.getAddress().trim());
                u.setStatus(inputBean.getStatus().trim());
                /**
                 * insert web logo
                 */
                try {
                    if (inputBean.getLogoImg().length() != 0) {

                        imageName = inputBean.getLogoImgFileName();

                        File logoWebImgFile = inputBean.getLogoImg();
                        byte[] bLogoWebFile = new byte[(int) logoWebImgFile.length()];
                        try {
                            fileInputStream = new FileInputStream(logoWebImgFile);
                            fileInputStream.read(bLogoWebFile);
                            fileInputStream.close();
                            u.setImage(bLogoWebFile);
                        } catch (Exception ex) {

                        }
                    }
                } catch (NullPointerException ex) {
                } finally {
                    try {
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        } else {
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                }
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

    public String deletePage(PartyInputBean inputBean) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            
            Party pty = CommonDAO.getPartyID(inputBean.getPartyCode());

            Party u = (Party) session.get(Party.class, pty.getPartyId());
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

}
