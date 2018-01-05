/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.dao;

import com.election.bean.CandidateBean;
import com.election.bean.CandidateInputBean;
import com.election.common.dao.CommonDAO;
import com.election.listener.HibernateInit;
import com.election.mapping.Candidate;
import com.election.mapping.District;
import com.election.mapping.LocalAuthority;
import com.election.mapping.Party;
import com.election.mapping.Province;
import com.election.mapping.Ward;
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
public class CandidateDAO {

    public List<CandidateBean> getSearchList(CandidateInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<CandidateBean> dataList = new ArrayList<CandidateBean>();
        Session session = null;
        try {

            if (orderBy.equals("") || orderBy == null) {
                orderBy = "order by u.name desc";
            }
            
            long count = 0;
            
            String where = this.makeWhereClause(inputBean);

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(nic) from Candidate as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {

                String sqlSearch = "from Candidate u where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    CandidateBean candidateBean = new CandidateBean();
                    Candidate candidate = (Candidate) it.next();

                    try {
                        candidateBean.setCandidateId(candidate.getCandidateId().toString());
                    } catch (NullPointerException npe) {
                        candidateBean.setCandidateId("--");
                    }
                    try {
                        candidateBean.setParty(candidate.getPartyCode());
                    } catch (NullPointerException npe) {
                        candidateBean.setParty("--");
                    }
                    try {
                        candidateBean.setWard(candidate.getWard().getDescription());
                    } catch (NullPointerException npe) {
                        candidateBean.setWard("--");
                    }
                    try {
                        candidateBean.setName(candidate.getName());
                    } catch (NullPointerException npe) {
                        candidateBean.setName("--");
                    }
                    try {
                        candidateBean.setNic(candidate.getNic());
                    } catch (NullPointerException npe) {
                        candidateBean.setNic("--");
                    }
                    try {
                        candidateBean.setContactNo(candidate.getContactNo());
                    } catch (NullPointerException npe) {
                        candidateBean.setContactNo("--");
                    }
                    try {
                        candidateBean.setAddress(candidate.getAddress());
                    } catch (NullPointerException npe) {
                        candidateBean.setAddress("--");
                    }
                    try {
                        candidateBean.setGender(candidate.getGender());
                    } catch (NullPointerException npe) {
                        candidateBean.setGender("--");
                    }
                    try {
                        candidateBean.setYouth(candidate.getYouth());
                    } catch (NullPointerException npe) {
                        candidateBean.setYouth("--");
                    }
                    try {
                        candidateBean.setStatus(candidate.getStatus());
                    } catch (NullPointerException npe) {
                        candidateBean.setStatus("--");
                    }
                    
                    candidateBean.setFullCount(count);

                    dataList.add(candidateBean);
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
    
    private String makeWhereClause(CandidateInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getProvince()!= null && !inputBean.getProvince().isEmpty()) {
//            where += " and lower(u.ward.localAuthority.district.province.code) like lower('%" + inputBean.getProvince() + "%')";
            where += " and u.ward.localAuthority.district.province.code = '" + inputBean.getProvince() + "'";
        }
        if (inputBean.getDistrict()!= null && !inputBean.getDistrict().isEmpty()) {
//            where += " and lower(u.ward.localAuthority.district.code) like lower('%" + inputBean.getDistrict() + "%')";
            where += " and u.ward.localAuthority.district.code = '" + inputBean.getDistrict() + "'";
        }
        if (inputBean.getLa()!= null && !inputBean.getLa().isEmpty()) {
//            where += " and lower(u.ward.localAuthority.code) like lower('%" + inputBean.getLa() + "%')";
            where += " and u.ward.localAuthority.code = '" + inputBean.getLa() + "'";
        }
        if (inputBean.getWard()!= null && !inputBean.getWard().isEmpty()) {
//            where += " and lower(u.ward.code) like lower('%" + inputBean.getWard() + "%')";
            where += " and u.ward.code = '" + inputBean.getWard() + "'";
        }
        if (inputBean.getParty()!= null && !inputBean.getParty().isEmpty()) {
//            where += " and lower(u.partyCode) like lower('%" + inputBean.getParty() + "%')";
            where += " and u.partyCode = '" + inputBean.getParty() + "'";
        }
        return where;
    }
    

    public String insertCandidate(CandidateInputBean inputBean) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {

            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            Candidate can = CommonDAO.getCandidateID(inputBean.getNic());

            System.out.println("can " + can);

            if (can == null) {
                txn = session.beginTransaction();

                Candidate candidate = new Candidate();

                Ward ward = (Ward) session.get(Ward.class, inputBean.getWard().trim());
                candidate.setWard(ward);
                Party pty = CommonDAO.getPartyID(inputBean.getParty().trim());
                Party party = (Party) session.get(Party.class, pty.getPartyId());
                candidate.setPartyCode(party.getPartyCode());

                candidate.setName(inputBean.getName());
                candidate.setNic(inputBean.getNic());
                candidate.setContactNo(inputBean.getContactNo());
                candidate.setAddress(inputBean.getAddress());
                candidate.setGender(inputBean.getGender());
                candidate.setYouth(inputBean.getYouth());
                candidate.setStatus(inputBean.getStatus());

                session.save(candidate);
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

    public String updateCandidate(CandidateInputBean inputBean) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {

            CommonDAO commonDAO = new CommonDAO();
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = commonDAO.getSystemDate(session);

            Candidate can = CommonDAO.getCandidateID(inputBean.getNic());
            
            System.out.println("can "+can.getCandidateId());

            Candidate u = (Candidate) session.get(Candidate.class, can.getCandidateId());
            Party pty = CommonDAO.getPartyID(inputBean.getParty().trim());

            if (u != null) {

                Ward ward = (Ward) session.get(Ward.class, inputBean.getWard().trim());
                u.setWard(ward);
                
                Party party = (Party) session.get(Party.class, pty.getPartyId());

                u.setPartyCode(party.getPartyCode());
                u.setName(inputBean.getName());
                u.setNic(inputBean.getNic());
                u.setContactNo(inputBean.getContactNo());
                u.setAddress(inputBean.getAddress());
                u.setGender(inputBean.getGender());
                u.setYouth(inputBean.getYouth());
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

    public Candidate findCandidateById(String candidateId) throws Exception {
        Candidate candidate = null;
        Session session = null;

        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Candidate as c where c.candidateId=:candidateId";
            Query query = session.createQuery(sql).setString("candidateId", candidateId);
            candidate = (Candidate) query.list().get(0);

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
        return candidate;
    }
    
    public Candidate findCandidateByNIC(String nic) throws Exception {
        Candidate candidate = null;
        Session session = null;

        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Candidate as c where c.nic=:nic";
            Query query = session.createQuery(sql).setString("nic", nic);
            candidate = (Candidate) query.list().get(0);

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
        return candidate;
    }

    public String getPartyCode(String partyId) throws Exception {
        String partyCode = null;
        Session session = null;

        try {
            session = HibernateInit.sessionFactory.openSession();
            Party party = (Party) session.get(Party.class, Integer.parseInt(partyId.trim()));

            if (party != null) {
                partyCode = party.getPartyCode();

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
        return partyCode;
    }

    public String deleteCandidate(CandidateInputBean inputBean) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Candidate u = (Candidate) session.get(Candidate.class, Integer.parseInt(inputBean.getCandidateId()));
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

    public void getProvinceList(CandidateInputBean inputBean) throws Exception {

        List<Province> provinceList = new ArrayList<Province>();
        List<Province> inputPList = new ArrayList<Province>();
        inputBean.setProvinceList(inputPList);
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Province as p order by p.description asc";
            Query query = session.createQuery(hql);
            provinceList = (List<Province>) query.list();

            for (Iterator<Province> it = provinceList.iterator(); it.hasNext();) {

                Province province = it.next();
                Province provinceObj = new Province();
                provinceObj.setCode(province.getCode());
                provinceObj.setDescription(province.getDescription());
                inputBean.getProvinceList().add(provinceObj);
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

    public void getDistrictList(CandidateInputBean inputBean) throws Exception {

        List<District> districtList = new ArrayList<District>();
        List<District> inputDList = new ArrayList<District>();
        inputBean.setDistrictList(inputDList);
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from District as d order by d.description asc";
            Query query = session.createQuery(hql);
            districtList = (List<District>) query.list();

            for (Iterator<District> it = districtList.iterator(); it.hasNext();) {

                District district = it.next();
                District districtObj = new District();
                districtObj.setCode(district.getCode());
                districtObj.setDescription(district.getDescription());
                inputBean.getDistrictList().add(districtObj);
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

    public void getDistrictListFromProvince(CandidateInputBean inputBean) throws Exception {

        List<District> districtList = new ArrayList<District>();
        List<District> districtInput = new ArrayList< District>();
        inputBean.setDistrictList(districtInput);
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from District as d where d.province.code =:pcode  order by Upper(d.description) asc";
            Query query = session.createQuery(hql).setString("pcode", inputBean.getProvince());
            districtList = (List<District>) query.list();

            for (Iterator<District> it = districtList.iterator(); it.hasNext();) {

                District district = it.next();
                District districtObj = new District();
                districtObj.setCode(district.getCode());
                districtObj.setDescription(district.getDescription());
                inputBean.getDistrictList().add(districtObj);
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

    public void getLAList(CandidateInputBean inputBean) throws Exception {

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

    public void getLAListFromDistrict(CandidateInputBean inputBean) throws Exception {

        List<LocalAuthority> localAuthorityList = new ArrayList<LocalAuthority>();
        List<LocalAuthority> localAuthorityInput = new ArrayList< LocalAuthority>();

        List<District> district = new ArrayList<District>();

        inputBean.setLaList(localAuthorityInput);
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from LocalAuthority as l where l.district.code =:dcode  order by Upper(l.description) asc";
            Query query = session.createQuery(hql).setString("dcode", inputBean.getDistrict());
            localAuthorityList = (List<LocalAuthority>) query.list();

            for (Iterator<LocalAuthority> it = localAuthorityList.iterator(); it.hasNext();) {

                LocalAuthority localAuthority = it.next();
                LocalAuthority localAuthorityObj = new LocalAuthority();
                localAuthorityObj.setCode(localAuthority.getCode());
                localAuthorityObj.setDescription(localAuthority.getDescription());
                inputBean.getLaList().add(localAuthorityObj);
            }

            String hql2 = "from District as d where d.code =:dcode  order by Upper(d.description) asc";
            Query query2 = session.createQuery(hql2).setString("dcode", inputBean.getDistrict());
            district = (List<District>) query2.list();

            inputBean.setProvince(district.get(0).getProvince().getCode());

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

    public void getWardList(CandidateInputBean inputBean) throws Exception {

        List<Ward> wardList = new ArrayList<Ward>();
        List<Ward> inputWardList = new ArrayList<Ward>();
        inputBean.setWardList(inputWardList);
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Ward as w order by w.description asc";
            Query query = session.createQuery(hql);
            wardList = (List<Ward>) query.list();

            for (Iterator<Ward> it = wardList.iterator(); it.hasNext();) {

                Ward ward = it.next();
                Ward wardObj = new Ward();
                wardObj.setCode(ward.getCode());
                wardObj.setDescription(ward.getDescription());
                inputBean.getWardList().add(wardObj);
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

    public void getWardListFromLA(CandidateInputBean inputBean) throws Exception {

        List<Ward> wardList = new ArrayList<Ward>();
        List<Ward> wardInput = new ArrayList<Ward>();

        List<LocalAuthority> laList = new ArrayList<LocalAuthority>();

        List<District> district = new ArrayList<District>();

        inputBean.setWardList(wardInput);
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Ward as w where w.localAuthority.code =:lacode  order by Upper(w.description) asc";
            Query query = session.createQuery(hql).setString("lacode", inputBean.getLa());
            wardList = (List<Ward>) query.list();

            for (Iterator<Ward> it = wardList.iterator(); it.hasNext();) {

                Ward ward = it.next();
                Ward wardObj = new Ward();
                wardObj.setCode(ward.getCode());
                wardObj.setDescription(ward.getDescription());
                inputBean.getWardList().add(wardObj);
            }

            String hql2 = "from LocalAuthority as l where l.code =:lcode  order by Upper(l.description) asc";
            Query query2 = session.createQuery(hql2).setString("lcode", inputBean.getLa());
            laList = (List<LocalAuthority>) query2.list();

            inputBean.setDistrict(laList.get(0).getDistrict().getCode());

            String hql3 = "from District as d where d.code =:dcode  order by Upper(d.description) asc";
            Query query3 = session.createQuery(hql3).setString("dcode", laList.get(0).getDistrict().getCode());
            district = (List<District>) query3.list();

            inputBean.setProvince(district.get(0).getProvince().getCode());

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

    public void getAllListFromWard(CandidateInputBean inputBean) throws Exception {

        List<Ward> ward = new ArrayList<Ward>();
        List<LocalAuthority> la = new ArrayList<LocalAuthority>();
        List<District> district = new ArrayList<District>();

        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Ward as w where w.code =:wcode  order by Upper(w.description) asc";
            Query query = session.createQuery(hql).setString("wcode", inputBean.getWard());
            ward = (List<Ward>) query.list();

            inputBean.setLa(ward.get(0).getLocalAuthority().getCode());

            String hql2 = "from LocalAuthority as l where l.code =:lcode  order by Upper(l.description) asc";
            Query query2 = session.createQuery(hql2).setString("lcode", ward.get(0).getLocalAuthority().getCode());
            la = (List<LocalAuthority>) query2.list();

            inputBean.setDistrict(la.get(0).getDistrict().getCode());

            String hql3 = "from District as d where d.code =:dcode  order by Upper(d.description) asc";
            Query query3 = session.createQuery(hql3).setString("dcode", la.get(0).getDistrict().getCode());
            district = (List<District>) query3.list();

            inputBean.setProvince(district.get(0).getProvince().getCode());

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

    public void getPartyList(CandidateInputBean inputBean) throws Exception {

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
}
