/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.dao;

import com.election.bean.CandidateBean;
import com.election.bean.CandidateInputBean;
import com.election.mapping.Candidate;
import java.util.List;

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
    
}
