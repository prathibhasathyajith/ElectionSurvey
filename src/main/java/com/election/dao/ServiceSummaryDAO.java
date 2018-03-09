/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.dao;

import com.election.bean.CountVoteSummary;
import com.election.bean.DataServiceSummary;
import com.election.bean.ServiceSummaryInputBean;
//import com.election.bean.VotingSummaryInputBean;
import com.election.common.dao.CommonDAO;
import com.election.listener.HibernateInit;
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
public class ServiceSummaryDAO {

    public void getProvinceList(ServiceSummaryInputBean inputBean) throws Exception {

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

    public void getDistrictList(ServiceSummaryInputBean inputBean) throws Exception {

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

    public void getDistrictListFromProvince(ServiceSummaryInputBean inputBean) throws Exception {

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

    public void getLAList(ServiceSummaryInputBean inputBean) throws Exception {

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

    public void getLAListFromDistrict(ServiceSummaryInputBean inputBean) throws Exception {

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

    public void getWardList(ServiceSummaryInputBean inputBean) throws Exception {

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

    public void getWardListFromLA(ServiceSummaryInputBean inputBean) throws Exception {

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

    public void getAllListFromWard(ServiceSummaryInputBean inputBean) throws Exception {

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

    public List<CountVoteSummary> loadDeatils(ServiceSummaryInputBean inputBean) throws Exception {
        List<CountVoteSummary> dataList = new ArrayList<CountVoteSummary>();

        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "";

            if (inputBean.getType().equals("PARTY")) {
                System.out.println("PARTY");

                if (!inputBean.getWard().equals("EMPTY")) {
                    System.out.println("------with ward c------");
                    sqlCount = "SELECT COUNT(*) "
                            + "FROM voting v "
                            + "WHERE v.ward_code = '" + inputBean.getWard() + "'";
                } else {
                    System.out.println("------without ward c------");
                    sqlCount = "SELECT COUNT(*) "
                            + "FROM voting v "
                            + "WHERE v.la_code = '" + inputBean.getLa() + "' "
                            + "GROUP BY v.la_code , v.voted_party ,v.ward_code ";
                }
            }

            Query queryCount = session.createSQLQuery(sqlCount);
            List countList = queryCount.list();

            if (countList.size() > 0) {

                String sqlSearch = "";
                String sqlSearch_forCount = "";

                if (inputBean.getType().equals("PARTY")) {
                    System.out.println("PARTY");

                    if (!inputBean.getWard().equals("EMPTY")) {
                        System.out.println("------with ward------");
                        sqlSearch = "SELECT COUNT(*) AS count, v.ward_code, v.voted_party ,v.la_code "
                                + "FROM voting v "
                                + "WHERE v.vote='YES' and v.ward_code = '" + inputBean.getWard() + "' "
                                + "GROUP BY v.ward_code , v.voted_party ,v.la_code "
                                + "ORDER BY v.ward_code ";

                        sqlSearch_forCount = "SELECT COUNT(*) AS count, v.ward_code, v.voted_party ,v.la_code "
                                + "FROM voting v "
                                + "WHERE v.ward_code = '" + inputBean.getWard() + "' "
                                + "GROUP BY v.ward_code , v.voted_party ,v.la_code "
                                + "ORDER BY v.ward_code ";

                    } else {
                        System.out.println("------without ward------");
                        sqlSearch = "SELECT COUNT(*) AS count ,v.ward_code, v.voted_party , v.la_code "
                                + "FROM voting v "
                                + "WHERE v.vote='YES' and v.la_code = '" + inputBean.getLa() + "' "
                                + "GROUP BY v.la_code , v.voted_party, v.ward_code "
                                + "ORDER BY v.la_code ";

                        sqlSearch_forCount = "SELECT COUNT(*) AS count ,v.ward_code, v.voted_party , v.la_code "
                                + "FROM voting v "
                                + "WHERE v.la_code = '" + inputBean.getLa() + "' "
                                + "GROUP BY v.la_code , v.voted_party, v.ward_code "
                                + "ORDER BY v.la_code ";
                    }
                }

                Query querySearch = session.createSQLQuery(sqlSearch);
                Query querySearchCount = session.createSQLQuery(sqlSearch_forCount);

                List<Object[]> ObjetctList = querySearch.list();
                List<Object[]> ObjetctListCount = querySearchCount.list();

                int fullCount_new = 0;

                for (Object[] bean : ObjetctListCount) {
                    fullCount_new += Integer.parseInt(String.valueOf(bean[0]));

                }

                for (Object[] bean : ObjetctList) {
                    CountVoteSummary countVS = new CountVoteSummary();

                    if (inputBean.getType().equals("PARTY")) {

                        if (bean[0] != null) {
                            countVS.setCount(String.valueOf(bean[0]));
                            countVS.setYesCount(String.valueOf(bean[0]));
                            int nocount = fullCount_new - Integer.parseInt(String.valueOf(bean[0]));
                            countVS.setNoCount(nocount + "");
                        } else {
                            countVS.setCount("--");
                        }
                        //party code
                        if (bean[2] != null) {
                            countVS.setColumName1(CommonDAO.getPartyID(String.valueOf(bean[2])).getName());
                        } else {
                            countVS.setColumName1("--");
                        }
                        if (bean[2] != null) {
                            countVS.setColumName2(String.valueOf(bean[1]));
                        } else {
                            countVS.setColumName2("--");
                        }
                        //ward des
                        if (bean[1] != null) {
                            countVS.setColumName3(CommonDAO.getWardFromCode(String.valueOf(bean[1])).getDescription());
                        } else {
                            countVS.setColumName3("--");
                        }
                        //la code
                        if (bean[3] != null) {
                            countVS.setColumName4(String.valueOf(bean[3]));
                        } else {
                            countVS.setColumName4("--");
                        }
                        //ward code
                        if (bean[1] != null) {
                            countVS.setColumName5(String.valueOf(bean[1]));
                        } else {
                            countVS.setColumName5("--");
                        }

                        double percentage = (Double.parseDouble(String.valueOf(bean[0])) / fullCount_new) * 100;
                        percentage = (double) Math.round(percentage * 100) / 100;
                        countVS.setPercentage1(percentage + "%");

                    }

                    dataList.add(countVS);

                }

                inputBean.setFullCount(fullCount_new + "");
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

    public List<DataServiceSummary> loadServiceDeatils(ServiceSummaryInputBean inputBean) throws Exception {
        List<DataServiceSummary> dataList = new ArrayList<DataServiceSummary>();

        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "";

            sqlCount = "SELECT "
                    + "COUNT(*)"
                    + this.joinQuery(inputBean.getWard(),inputBean.getLa());

            Query queryCount = session.createSQLQuery(sqlCount);
            List countList = queryCount.list();

            if (countList.size() > 0) {

                String sqlSearch = "";

                sqlSearch = "SELECT "
                        + "COUNT(*) AS d, "
                        + "CONCAT(ROUND((COUNT(*) / (SELECT COUNT(*) AS tt " + this.joinQuery(inputBean.getWard(),inputBean.getLa()) + ")) * 100,2),'%') AS percentage_la, "
                        + "CONCAT(ROUND((COUNT(*) / (SELECT COUNT(*) AS td " + this.joinQuery(inputBean.getWard(),"1=1") + ")) * 100,2),'%') AS percentage_ward, "                        
                        + "sl.name "
                        + this.joinQuery(inputBean.getWard(),inputBean.getLa())
                        + "GROUP BY sl.name ";

                Query querySearch = session.createSQLQuery(sqlSearch);

                List<Object[]> ObjetctList = querySearch.list();

                for (Object[] bean : ObjetctList) {
                    DataServiceSummary summary = new DataServiceSummary();

                    if (bean[0] != null) {
                        summary.setCount(bean[0].toString());
                    } else {
                        summary.setWardcode("--");
                    }

                    if (bean[1] != null) {
                        summary.setLApercentage(bean[1].toString());
                    } else {
                        summary.setLApercentage("--");
                    }
                    
                    if (bean[2] != null) {
                        summary.setWARDpercentage(bean[2].toString());
                    } else {
                        summary.setWARDpercentage("--");
                    }

                    if (bean[3] != null) {
                        summary.setServicename(bean[3].toString());
                    } else {
                        summary.setServicename("--");
                    }

                    dataList.add(summary);
                    inputBean.setFullCount(countList.get(0).toString()+"");

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

    private String joinQuery(String wardcode, String lacode) {
        String full_query = "";

        full_query = "FROM "
                + "election_survey_info esi "
                + "LEFT OUTER JOIN "
                + "election_survey el ON el.id = esi.survey_id "
                + "AND el.is_party = 'Y' "
                + "LEFT OUTER JOIN "
                + "election_survey el2 ON el2.id = esi.survey_id "
                + "AND el2.is_party = 'N' "
                + "LEFT OUTER JOIN "
                + "service_list sl ON sl.id = esi.service_list_id "
                + "LEFT OUTER JOIN "
                + "candidate c ON c.candidate_id = el2.user_id "
                + "AND el2.is_party = 'N' "
                + "LEFT OUTER JOIN "
                + "party_la p ON p.id = el.user_id AND el.is_party = 'Y' "
                + "LEFT OUTER JOIN "
                + "ward w ON w.code = c.ward_code "
                + "WHERE "
                + "c.ward_code = '" + wardcode + "' "
                + "OR p.la_code = '" + lacode + "' ";

        return full_query;
    }
}
