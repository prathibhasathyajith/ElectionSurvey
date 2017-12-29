/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.dao;

import com.election.bean.CandidateBean;
import com.election.bean.CandidateInputBean;
import com.election.listener.HibernateInit;
import com.election.mapping.Candidate;
import com.election.mapping.District;
import com.election.mapping.LocalAuthority;
import com.election.mapping.Province;
import com.election.mapping.Ward;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author prathibha
 */
public class CandidateDAO {

    public List<CandidateBean> getSearchList(CandidateInputBean inputBean, int rows, int from, String orderBy) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String insertCandidate(CandidateInputBean inputBean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String updateCandidate(CandidateInputBean inputBean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Candidate findCandidateById(String candidateId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String deleteCandidate(CandidateInputBean inputBean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
}
