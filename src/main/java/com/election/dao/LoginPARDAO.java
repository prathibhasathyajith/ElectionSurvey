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

    public List<CountVoteSummary> getDetailsParty(String partyCode, LoginPARInputBean inputBean) throws Exception {
        List<CountVoteSummary> dataList = new ArrayList<CountVoteSummary>();

        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "SELECT COUNT(*) "
                    + "FROM party p "
                    + "INNER JOIN voting v ON p.party_code = v.user_id "
                    + "GROUP BY p.party_code, v.ward_code,v.user_type "
                    + "HAVING v.user_type = 'PARTY' and p.party_code = '" + partyCode + "' order by v.ward_code asc";

            Query queryCount = session.createSQLQuery(sqlCount);
            List countList = queryCount.list();

            if (countList.size() > 0) {

                String sqlSearch = "SELECT COUNT(*) as count, v.ward_code "
                        + "FROM party p "
                        + "INNER JOIN voting v ON p.party_code = v.user_id "
                        + "GROUP BY p.party_code, v.ward_code,v.user_type "
                        + "HAVING v.user_type = 'PARTY' and p.party_code = '" + partyCode + "' order by v.ward_code asc";

                Query querySearch = session.createSQLQuery(sqlSearch);

                List<Object[]> ObjetctList = querySearch.list();

                for (Object[] bean : ObjetctList) {
                    CountVoteSummary countVS = new CountVoteSummary();

                    if (bean[0] != null) {
                        countVS.setCount(String.valueOf(bean[0]));
                    } else {
                        countVS.setCount("--");
                    }
                    if (bean[1] != null) {
                        countVS.setColumName1(String.valueOf(bean[1]));
                    } else {
                        countVS.setColumName1("--");
                    }
                    //ward description
                    if (bean[1] != null) {
                        countVS.setColumName2(CommonDAO.getWardFromCode(String.valueOf(bean[1])).getDescription());
                    } else {
                        countVS.setColumName2("--");
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

}
