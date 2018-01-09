/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.dao;

import com.election.bean.CountVoteSummary;
import com.election.bean.LoginCANInputBean;
import com.election.bean.VotingSummaryInputBean;
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
public class LoginCANDAO {

    public List<CountVoteSummary> getPercentageCandidate(String username, String ward, LoginCANInputBean inputBean) throws Exception {
        List<CountVoteSummary> dataList = new ArrayList<CountVoteSummary>();

        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "SELECT COUNT(*) "
                    + "FROM elect_survey.candidate c "
                    + "INNER JOIN elect_survey.VOTING V ON c.username = V.USER_ID "
                    + "GROUP BY c.username, V.WARD_CODE,V.USER_TYPE "
                    + "HAVING V.WARD_CODE = '" + ward + "' and V.USER_TYPE = 'USER' ";

            Query queryCount = session.createSQLQuery(sqlCount);
            List countList = queryCount.list();

            if (countList.size() > 0) {

                String sqlSearch = "SELECT COUNT(*), c.username, V.WARD_CODE,c.party_code "
                        + "FROM elect_survey.candidate c "
                        + "INNER JOIN elect_survey.VOTING V ON c.username = V.USER_ID "
                        + "GROUP BY c.username, V.WARD_CODE,V.USER_TYPE "
                        + "HAVING V.WARD_CODE = '" + ward + "' and V.USER_TYPE = 'USER' ";

                Query querySearch = session.createSQLQuery(sqlSearch);

                List<Object[]> ObjetctList = querySearch.list();

                int fullCount = 0;

                for (Object[] bean : ObjetctList) {
                    fullCount += Integer.parseInt(String.valueOf(bean[0]));

                }

                for (Object[] bean : ObjetctList) {
                    CountVoteSummary countVS = new CountVoteSummary();

                    if (bean[0] != null) {
                        countVS.setCount(String.valueOf(bean[0]));
                    } else {
                        countVS.setCount("--");
                    }
                    if (bean[1] != null) {
                        countVS.setColumName1(CommonDAO.getCandidateName(String.valueOf(bean[1])).getName());
                    } else {
                        countVS.setColumName1("--");
                    }
                    if (bean[2] != null) {
                        countVS.setColumName2(String.valueOf(bean[2]));
                    } else {
                        countVS.setColumName2("--");
                    }
                    if (bean[3] != null) {
                        countVS.setColumName3(CommonDAO.getPartyID(String.valueOf(bean[3])).getName());
                    } else {
                        countVS.setColumName3("--");
                    }

                    double percentage = (Double.parseDouble(String.valueOf(bean[0])) / fullCount) * 100;
                    percentage = (double) Math.round(percentage * 100) / 100;
                    countVS.setPercentage1(percentage + "%");

                    if (String.valueOf(bean[1]).equals(username)) {
                        inputBean.setCandidateVotePercentage(percentage + "");
                    }

                    dataList.add(countVS);

                }

                inputBean.setCandidateFullVoteCount(fullCount + "");
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

    public List<CountVoteSummary> getDetailsCandidate(String username, LoginCANInputBean inputBean) throws Exception {
        List<CountVoteSummary> dataList = new ArrayList<CountVoteSummary>();

        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "SELECT COUNT(*) "
                    + "FROM elect_survey.candidate c "
                    + "INNER JOIN elect_survey.VOTING V ON c.username = V.USER_ID "
                    + "GROUP BY c.username, V.WARD_CODE,V.USER_TYPE "
                    + "HAVING V.USER_TYPE = 'USER' and c.username = '" + username + "' ";

            Query queryCount = session.createSQLQuery(sqlCount);
            List countList = queryCount.list();

            if (countList.size() > 0) {

                String sqlSearch = "SELECT COUNT(*), c.username, V.WARD_CODE,c.party_code "
                        + "FROM elect_survey.candidate c "
                        + "INNER JOIN elect_survey.VOTING V ON c.username = V.USER_ID "
                        + "GROUP BY c.username, V.WARD_CODE,V.USER_TYPE "
                        + "HAVING V.USER_TYPE = 'USER' and c.username = '" + username + "' ";

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
                        countVS.setColumName1(CommonDAO.getCandidateName(String.valueOf(bean[1])).getName());
                    } else {
                        countVS.setColumName1("--");
                    }
                    if (bean[2] != null) {
                        countVS.setColumName2(String.valueOf(bean[2]));
                    } else {
                        countVS.setColumName2("--");
                    }
                    if (bean[3] != null) {
                        countVS.setColumName3(CommonDAO.getPartyID(String.valueOf(bean[3])).getName());
                    } else {
                        countVS.setColumName3("--");
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

    public List<CountVoteSummary> getFullDataCandidate(List<CountVoteSummary> dataListDetails, String percentage) {
        if (dataListDetails.size() > 0) {
            for (int i = 0; i < dataListDetails.size(); i++) {
                dataListDetails.get(i).setPercentage1(percentage);

            }

        }

        return dataListDetails;
    }

}
