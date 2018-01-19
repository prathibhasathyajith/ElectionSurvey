/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.dao;

import com.election.bean.CountVoteSummary;
import com.election.bean.LoginPARInputBean;
import com.election.common.dao.CommonDAO;
import com.election.listener.HibernateInit;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author prathibha_s
 */
public class LoginPARDAO {

    public List<CountVoteSummary> getFullVoteCountParty(LoginPARInputBean inputBean) throws Exception {
        List<CountVoteSummary> dataList = new ArrayList<CountVoteSummary>();

        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "SELECT COUNT(*) "
                    + "FROM voting v "
                    + "GROUP BY v.la_code,v.ward_code "
                    + "order by v.la_code  ";

            Query queryCount = session.createSQLQuery(sqlCount);
            List countList = queryCount.list();

            if (countList.size() > 0) {

                String sqlSearch = "SELECT COUNT(*),v.la_code ,v.ward_code  "
                    + "FROM voting v "
                    + "GROUP BY v.la_code,v.ward_code "
                    + "order by v.la_code  ";

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
                    //la code
                    if (bean[1] != null) {
                        countVS.setColumName1(String.valueOf(bean[1]));
                    } else {
                        countVS.setColumName1("--");
                    }
                    //la desc
                    if (bean[1] != null) {
                        countVS.setColumName2(CommonDAO.getLAFromCode(String.valueOf(bean[1])).getDescription());
                    } else {
                        countVS.setColumName2("--");
                    }
                    
                    //ward code
                    if (bean[2] != null) {
                        countVS.setColumName3(String.valueOf(bean[2]));
                    } else {
                        countVS.setColumName3("--");
                    }
                    //ward desc
                    if (bean[2] != null) {
                        countVS.setColumName4(CommonDAO.getWardFromCode(String.valueOf(bean[2])).getDescription());
                    } else {
                        countVS.setColumName4("--");
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

    public List<CountVoteSummary> getDetailsParty(String partyCode, LoginPARInputBean inputBean) throws Exception {
        List<CountVoteSummary> dataList = new ArrayList<CountVoteSummary>();

        
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "SELECT COUNT(*) "
                    + "FROM voting v "
                    + "WHERE v.voted_party = '" + partyCode + "' "
                    + "GROUP BY v.la_code, v.ward_code "
                    + "order by v.la_code asc ";

            Query queryCount = session.createSQLQuery(sqlCount);
            List countList = queryCount.list();

            if (countList.size() > 0) {

                String sqlSearch = "SELECT COUNT(*), v.la_code, v.ward_code "
                        + "FROM voting v "
                        + "WHERE v.voted_party = '" + partyCode + "' "
                        + "GROUP BY v.la_code, v.ward_code "
                        + "order by v.la_code asc";

                Query querySearch = session.createSQLQuery(sqlSearch);

                List<Object[]> ObjetctList = querySearch.list();

                for (Object[] bean : ObjetctList) {
                    CountVoteSummary countVS = new CountVoteSummary();

                    if (bean[0] != null) {
                        countVS.setCount(String.valueOf(bean[0]));
                    } else {
                        countVS.setCount("--");
                    }
                    //la code
                    if (bean[1] != null) {
                        countVS.setColumName1(String.valueOf(bean[1]));
                    } else {
                        countVS.setColumName1("--");
                    }
                    //la desc
                    if (bean[1] != null) {
                        countVS.setColumName2(CommonDAO.getLAFromCode(String.valueOf(bean[1])).getDescription());
                    } else {
                        countVS.setColumName2("--");
                    }
                    
                    //ward code
                    if (bean[2] != null) {
                        countVS.setColumName3(String.valueOf(bean[2]));
                    } else {
                        countVS.setColumName3("--");
                    }
                    //ward desc
                    if (bean[2] != null) {
                        countVS.setColumName4(CommonDAO.getWardFromCode(String.valueOf(bean[2])).getDescription());
                    } else {
                        countVS.setColumName4("--");
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

    public List<CountVoteSummary> getDetailsPartyLA(String partyCode, LoginPARInputBean inputBean) throws Exception {
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

    public List<CountVoteSummary> getFullDataParty(List<CountVoteSummary> dataListDetails, List<CountVoteSummary> dataList) {

        if (dataListDetails.size() > 0 && dataList.size() > 0) {
            for (int i = 0; i < dataListDetails.size(); i++) {
                for (int j = 0; j < dataList.size(); j++) {
                    if (dataListDetails.get(i).getColumName3().equals(dataList.get(j).getColumName3())) {
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

}
