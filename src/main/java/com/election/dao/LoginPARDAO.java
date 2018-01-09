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
                    + "FROM elect_survey.party P "
                    + "INNER JOIN elect_survey.VOTING V ON P.PARTY_CODE = V.USER_ID "
                    + "GROUP BY V.WARD_CODE,V.USER_TYPE "
                    + "HAVING  V.USER_TYPE = 'PARTY' ";

            Query queryCount = session.createSQLQuery(sqlCount);
            List countList = queryCount.list();

            if (countList.size() > 0) {

                String sqlSearch = "SELECT COUNT(*) as Count, V.WARD_CODE "
                        + "FROM elect_survey.party P "
                        + "INNER JOIN elect_survey.VOTING V ON P.PARTY_CODE = V.USER_ID "
                        + "GROUP BY  V.WARD_CODE,V.USER_TYPE "
                        + "HAVING  V.USER_TYPE = 'PARTY' ";

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
                    + "FROM elect_survey.party P "
                    + "INNER JOIN elect_survey.VOTING V ON P.PARTY_CODE = V.USER_ID "
                    + "GROUP BY P.PARTY_CODE, V.WARD_CODE,V.USER_TYPE "
                    + "HAVING V.USER_TYPE = 'PARTY' and P.PARTY_CODE = '" + partyCode + "' order by V.WARD_CODE asc";

            Query queryCount = session.createSQLQuery(sqlCount);
            List countList = queryCount.list();

            if (countList.size() > 0) {

                String sqlSearch = "SELECT COUNT(*) as count, V.WARD_CODE "
                        + "FROM elect_survey.party P "
                        + "INNER JOIN elect_survey.VOTING V ON P.PARTY_CODE = V.USER_ID "
                        + "GROUP BY P.PARTY_CODE, V.WARD_CODE,V.USER_TYPE "
                        + "HAVING V.USER_TYPE = 'PARTY' and P.PARTY_CODE = '" + partyCode + "' order by V.WARD_CODE asc";

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

                double percentage = (Double.parseDouble(dataListDetails.get(i).getCount()) / Integer.parseInt(dataList.get(i).getCount())) * 100;
                percentage = (double) Math.round(percentage * 100) / 100;
                dataListDetails.get(i).setPercentage1(percentage + "");

            }

        }

        return dataListDetails;

    }

}
