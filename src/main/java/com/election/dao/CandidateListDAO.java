/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.dao;

import com.election.bean.CandidateBean;
import com.election.bean.CandidateListBean;
import com.election.bean.CandidateListInputBean;
import com.election.common.dao.CommonDAO;
import com.election.listener.HibernateInit;
import com.election.mapping.Candidate;
import com.election.mapping.CandidateList;
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
public class CandidateListDAO {

    public void findCandidateList(CandidateListInputBean inputBean) throws Exception {
        CandidateList candidateList = null;
        List<CandidateBean> dataList = new ArrayList<CandidateBean>();
        Session session = null;

        List<CandidateListBean> list_1 = new ArrayList<CandidateListBean>();
        List<CandidateListBean> list_2 = new ArrayList<CandidateListBean>();

        try {
            session = HibernateInit.sessionFactory.openSession();
            long count = 0;
            long count_not = 0;

            String sqlCount = "select count(c.name) from Candidate as c where c.ward.code=:ward and c.partyCode=:party "
                    + " and c.candidateId in "
                    + " (select d.candidate.candidateId from CandidateList as d) ";
            Query queryCount = session.createQuery(sqlCount).setString("ward", inputBean.getWard())
                    .setString("party", inputBean.getParty());

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            String sqlCount_not = "select count(c.name) from Candidate as c where c.ward.code=:ward and c.partyCode=:party "
                    + " and c.candidateId not in "
                    + " (select d.candidate.candidateId from CandidateList as d) ";
            Query queryCount_not = session.createQuery(sqlCount_not).setString("ward", inputBean.getWard())
                    .setString("party", inputBean.getParty());

            Iterator itCount_not = queryCount_not.iterate();
            count_not = (Long) itCount_not.next();

//
//            String sqlCount = "select count(id) from candidate_list as d ";
//            Query queryCount = session.createSQLQuery(sqlCount);
//            if (queryCount.uniqueResult() != null) {
//                count = ((Number) queryCount.uniqueResult()).intValue();
//            }
            System.out.println("count " + count);
            System.out.println("count not " + count_not);

            if (count > 0) {

                System.out.println("inside in");

                String sql = "from Candidate as c where c.ward.code=:ward and c.partyCode=:party "
                        + " and c.candidateId in "
                        + " (select d.candidate.candidateId from CandidateList as d) ";

                /**
                 *
                 * -- IN shows candidate_list table data -- -- NOT IN shows
                 * candidate table data --
                 *
                 */
                Query query = session.createQuery(sql).setString("ward", inputBean.getWard())
                        .setString("party", inputBean.getParty());

                Iterator it = query.iterate();

                while (it.hasNext()) {
                    CandidateBean candidateBean = new CandidateBean();
                    Candidate candidate = (Candidate) it.next();
                    CandidateListBean clb = new CandidateListBean();

                    clb.setName(candidate.getName());
                    clb.setCandidate(candidate.getCandidateId()+"");
                    list_2.add(clb);

                    System.out.println("candidate " + candidate.getName());
                }

                inputBean.setList_2(list_2);

            }

            if (count_not > 0) {

                System.out.println("inside not in");

                String sql_not = "from Candidate as c where c.ward.code=:ward and c.partyCode=:party "
                        + " and c.candidateId not in "
                        + " (select d.candidate.candidateId from CandidateList as d) ";

                /**
                 *
                 * -- IN shows candidate_list table data -- -- NOT IN shows
                 * candidate table data --
                 *
                 */
                Query query_not = session.createQuery(sql_not).setString("ward", inputBean.getWard())
                        .setString("party", inputBean.getParty());

                Iterator it_not = query_not.iterate();

                while (it_not.hasNext()) {
                    CandidateBean candidateBean_not = new CandidateBean();
                    Candidate candidate_not = (Candidate) it_not.next();
                    CandidateListBean clb2 = new CandidateListBean();

                    clb2.setName(candidate_not.getName());
                    clb2.setCandidate(candidate_not.getCandidateId()+"");
                    list_1.add(clb2);

                    System.out.println("candidate not " + candidate_not.getName());
                }

                inputBean.setList_1(list_1);

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
//        return candidate;
    }

    public String assignFields(CandidateListInputBean inputBean) throws Exception {
        String[] list_1 = null;
        String[] list_2 = null;
        Session session = null;
        Transaction txn = null;
        String message = "";

        CandidateList canL = new CandidateList();

        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);
            if (inputBean.getList2() != null) {
                list_2 = inputBean.getList2().split(",");
                txn = session.beginTransaction();

                for (int i = 0; i < list_2.length; i++) {
                    canL = CommonDAO.getCandidateListID(list_2[i].trim());

                    if (canL == null) {

                        System.out.println("insde assign");

                        CandidateList candidateList = new CandidateList();

                        Ward ward = (Ward) session.get(Ward.class, inputBean.getWard().trim());
                        candidateList.setWard(ward);

                        Candidate candidate = (Candidate) session.get(Candidate.class, Integer.parseInt(list_2[i].trim()));
                        candidateList.setCandidate(candidate);

                        candidateList.setType(inputBean.getType());

                        session.save(candidateList);
                    } else {
                        message = "already in table";
                    }
                }
                txn.commit();
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

    public String deleteCandidateListByWard(CandidateListInputBean inputBean) throws Exception {

        List<CandidateList> clist = new ArrayList<CandidateList>();
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            String sql = "from CandidateList as c where c.ward.code=:ward";
            Query query = session.createQuery(sql).setString("ward", inputBean.getWard());
            clist = (List<CandidateList>) query.list();

            if (clist.size() > 0) {
                for (int i = 0; i < clist.size(); i++) {
                    System.out.println("id " + clist.get(i).getId());
                    System.out.println("can_id " + clist.get(i).getCandidate().getCandidateId());

                    CandidateList u = (CandidateList) session.get(CandidateList.class, clist.get(i).getId());
                    if (u != null) {
                        session.delete(u);
                    }
                }
            } else {
                message = "No candidates assigned";
            }

            txn.commit();

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
        return message;
    }

    public void getProvinceList(CandidateListInputBean inputBean) throws Exception {

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

    public void getDistrictList(CandidateListInputBean inputBean) throws Exception {

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

    public void getDistrictListFromProvince(CandidateListInputBean inputBean) throws Exception {

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

    public void getLAList(CandidateListInputBean inputBean) throws Exception {

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

    public void getLAListFromDistrict(CandidateListInputBean inputBean) throws Exception {

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

    public void getWardList(CandidateListInputBean inputBean) throws Exception {

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

    public void getWardListFromLA(CandidateListInputBean inputBean) throws Exception {

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

    public void getAllListFromWard(CandidateListInputBean inputBean) throws Exception {

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

    public void getPartyList(CandidateListInputBean inputBean) throws Exception, Exception {
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
