/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.dao;

import com.election.bean.CountVoteSummary;
import com.election.bean.LoginPARInputBean;
import com.election.bean.SearchPartyInputBean;
import com.election.common.dao.CommonDAO;
import com.election.listener.HibernateInit;
import com.election.mapping.District;
import com.election.mapping.LocalAuthority;
import com.election.mapping.Province;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author prathibha_s
 */
public class SearchPartyDAO {

    public List<CountVoteSummary> getFullDataParty(List<CountVoteSummary> dataListDetails, List<CountVoteSummary> dataList) {

        if (dataListDetails.size() > 0 && dataList.size() > 0) {
            for (int i = 0; i < dataListDetails.size(); i++) {
                for (int j = 0; j < dataList.size(); j++) {
                    if (dataListDetails.get(i).getColumName1().equals(dataList.get(j).getColumName1())) {
                        double percentage = (Double.parseDouble(dataListDetails.get(i).getCount()) / Integer.parseInt(dataList.get(j).getCount())) * 100;
                        percentage = (double) Math.round(percentage * 100) / 100;
                        dataListDetails.get(i).setPercentage1(percentage + "%");
                        break;
                    }
                }
            }
        }

        return dataListDetails;

    }

    public List<CountVoteSummary> getFullVoteCountParty(SearchPartyInputBean inputBean) throws Exception {
        List<CountVoteSummary> dataList = new ArrayList<CountVoteSummary>();

        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "SELECT COUNT(*) "
                    + "FROM party p "
                    + "INNER JOIN voting v ON p.party_code = v.user_id "
                    + "GROUP BY v.ward_code,v.user_type "
                    + "HAVING  v.user_type = 'PARTY' ";

            Query queryCount = session.createSQLQuery(sqlCount);
            List countList = queryCount.list();

            if (countList.size() > 0) {

                String sqlSearch = "SELECT COUNT(*) as Count, v.ward_code "
                        + "FROM party p "
                        + "INNER JOIN voting v ON p.party_code = v.user_id "
                        + "GROUP BY  v.ward_code,v.user_type "
                        + "HAVING  v.user_type = 'PARTY' ";

                Query querySearch = session.createSQLQuery(sqlSearch);

                List<Object[]> ObjetctList = querySearch.list();

                for (Object[] bean : ObjetctList) {
                    CountVoteSummary countVS = new CountVoteSummary();

                    if (bean[0] != null) {
                        //Full count
                        countVS.setCount(String.valueOf(bean[0]));
                    } else {
                        countVS.setCount("--");
                    }
                    if (bean[1] != null) {
                        //ward cord
                        countVS.setColumName1(String.valueOf(bean[1]));
                    } else {
                        countVS.setColumName1("--");
                    }

                    dataList.add(countVS);

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

    public List<CountVoteSummary> getDetailsParty(String partyCode, SearchPartyInputBean inputBean) throws Exception {
        List<CountVoteSummary> dataList = new ArrayList<CountVoteSummary>();

        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();

            String wardSql = "";
            if (inputBean.getLa() == null || inputBean.getLa().trim().isEmpty()) {
                wardSql = "";
            } else {
                wardSql = " and w.la_code = '" + inputBean.getLa() + "' ";
            }

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "SELECT COUNT(*) "
                    + "FROM party p "
                    + "INNER JOIN voting v ON p.party_code = v.user_id "
                    + "INNER JOIN ward w ON v.ward_code = w.code "
                    + "GROUP BY p.party_code, v.ward_code, v.user_type, w.la_code "
                    + "HAVING v.user_type = 'PARTY' and p.party_code = '" + partyCode + "'" + wardSql + " order by v.ward_code asc";
            System.out.println("sql " + sqlCount);
            Query queryCount = session.createSQLQuery(sqlCount);
            List countList = queryCount.list();

            if (countList.size() > 0) {

                String sqlSearch = "SELECT COUNT(*) as count, v.ward_code, w.la_code "
                        + "FROM party p "
                        + "INNER JOIN voting v ON p.party_code = v.user_id "
                        + "INNER JOIN ward w ON v.ward_code = w.code "
                        + "GROUP BY p.party_code, v.ward_code, v.user_type, w.la_code "
                        + "HAVING v.user_type = 'PARTY' and p.party_code = '" + partyCode + "'" + wardSql + " order by v.ward_code asc";

                Query querySearch = session.createSQLQuery(sqlSearch);

                List<Object[]> ObjetctList = querySearch.list();

                for (Object[] bean : ObjetctList) {
                    CountVoteSummary countVS = new CountVoteSummary();

                    if (bean[0] != null) {
                        countVS.setCount(String.valueOf(bean[0]));
                    } else {
                        countVS.setCount("--");
                    }
                    //ward code
                    if (bean[1] != null) {
                        countVS.setColumName1(String.valueOf(bean[1]));
                    } else {
                        countVS.setColumName1("--");
                    }
                    //la code
                    if (bean[2] != null) {
                        countVS.setColumName2(String.valueOf(bean[2]));
                    } else {
                        countVS.setColumName2("--");
                    }
                    //la description
                    if (bean[2] != null) {
                        countVS.setColumName4(CommonDAO.getLAFromCode(String.valueOf(bean[2])).getDescription());
                    } else {
                        countVS.setColumName4("--");
                    }
                    //ward description
                    if (bean[1] != null) {
                        countVS.setColumName5(CommonDAO.getWardFromCode(String.valueOf(bean[1])).getDescription());
                    } else {
                        countVS.setColumName5("--");
                    }

                    dataList.add(countVS);

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

    public void getProvinceList(SearchPartyInputBean inputBean) throws Exception {

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

    public void getDistrictList(SearchPartyInputBean inputBean) throws Exception {

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

    public void getDistrictListFromProvince(SearchPartyInputBean inputBean) throws Exception {

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

    public void getLAList(SearchPartyInputBean inputBean) throws Exception {

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

    public void getLAListFromDistrict(SearchPartyInputBean inputBean) throws Exception {

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

    public void getALLFromLA(SearchPartyInputBean inputBean) throws Exception {

        List<LocalAuthority> laList = new ArrayList<LocalAuthority>();
        List<District> district = new ArrayList<District>();

        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
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
}
