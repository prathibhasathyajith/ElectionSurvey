/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.dao;

import com.election.bean.CandidateBean;
import com.election.bean.CandidateListBean;
import com.election.bean.CandidateListInputBean;
import com.election.listener.HibernateInit;
import com.election.mapping.Candidate;
import com.election.mapping.CandidateList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

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
                 * -- IN shows candidate_list table data -- 
                 * -- NOT IN shows candidate table data --
                 *
                 */
                Query query = session.createQuery(sql).setString("ward", "KURKLUCKPT")
                        .setString("party", "test");

                Iterator it = query.iterate();

                while (it.hasNext()) {
                    CandidateBean candidateBean = new CandidateBean();
                    Candidate candidate = (Candidate) it.next();
                    CandidateListBean clb = new CandidateListBean();

                    clb.setName(candidate.getName());
                    clb.setCandidate(candidate.getCandidateId().toString());
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
                    clb2.setCandidate(candidate_not.getCandidateId().toString());
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

}
