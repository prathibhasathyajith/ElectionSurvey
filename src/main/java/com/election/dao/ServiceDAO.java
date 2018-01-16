/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.dao;

import com.election.bean.ServiceBean;
import com.election.bean.ServiceInputBean;
import com.election.common.dao.CommonDAO;
import com.election.listener.HibernateInit;
import com.election.mapping.ServiceList;
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
public class ServiceDAO {

    public List<ServiceBean> getSearchList(ServiceInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<ServiceBean> dataList = new ArrayList<ServiceBean>();
        Session session = null;
        try {

            long count = 0;

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(id) from ServiceList as u ";
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {

                String sqlSearch = "from ServiceList u " + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    ServiceBean serviceBean = new ServiceBean();
                    ServiceList serviceList = (ServiceList) it.next();

                    try {
                        serviceBean.setId(serviceList.getId()+"");
                    } catch (NullPointerException npe) {
                        serviceBean.setId("--");
                    }
                    try {
                        serviceBean.setCode(serviceList.getCode());
                    } catch (NullPointerException npe) {
                        serviceBean.setCode("--");
                    }
                    try {
                        serviceBean.setName(serviceList.getName());
                    } catch (NullPointerException npe) {
                        serviceBean.setName("--");
                    }
                    try {
                        serviceBean.setDescription(serviceList.getDescription());
                    } catch (NullPointerException npe) {
                        serviceBean.setDescription("--");
                    }
                    try {
                        serviceBean.setStatus(serviceList.getStatus());
                    } catch (NullPointerException npe) {
                        serviceBean.setStatus("--");
                    }

                    serviceBean.setFullCount(count);

                    dataList.add(serviceBean);
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

    public String insertService(ServiceInputBean inputBean) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {

            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            ServiceList ser = CommonDAO.getServiceID(inputBean.getCode());

            if (ser == null) {
                txn = session.beginTransaction();

                ServiceList serviceList = new ServiceList();

                serviceList.setCode(inputBean.getCode());
                serviceList.setName(inputBean.getName());
                serviceList.setDescription(inputBean.getDescription());
                serviceList.setStatus(inputBean.getStatus());

                session.save(serviceList);
                txn.commit();

            } else {
                message = "Record already exists";

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

    public String updateService(ServiceInputBean inputBean) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {

            CommonDAO commonDAO = new CommonDAO();
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = commonDAO.getSystemDate(session);

            ServiceList ser = CommonDAO.getServiceID(inputBean.getCode());

            System.out.println("ser up " + ser.getId());

            ServiceList u = (ServiceList) session.get(ServiceList.class, ser.getId());

            if (u != null) {

                u.setCode(inputBean.getCode());
                u.setName(inputBean.getName());
                u.setDescription(inputBean.getDescription());
                u.setStatus(inputBean.getStatus());

                session.update(u);
                txn.commit();

            } else {
                message = "Record does not exists";
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

    public ServiceList findService(String id) throws Exception {
        ServiceList serviceList = null;
        Session session = null;

        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from ServiceList as s where s.code=:code";
            Query query = session.createQuery(sql).setString("code", id);
            serviceList = (ServiceList) query.list().get(0);

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
        return serviceList;
    }

    public String deleteService(ServiceInputBean inputBean) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            ServiceList u = (ServiceList) session.get(ServiceList.class, Integer.parseInt(inputBean.getId()));
            if (u != null) {

                session.delete(u);
                txn.commit();

            } else {
                message = "Record does not exists";
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

    public String insertupdatetSL(ServiceInputBean inputBean) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            System.out.println("code " + inputBean.getCode());
            System.out.println("name " + inputBean.getName());
            System.out.println("des " + inputBean.getDescription());
            System.out.println("status " + inputBean.getStatus());

            ServiceList sl = CommonDAO.getServiceListID(inputBean.getCode());
            ServiceList u = null;
            if (sl != null) {
                u = (ServiceList) session.get(ServiceList.class, sl.getId());
            }

            if (u == null) {
                txn = session.beginTransaction();

                ServiceList slst = new ServiceList();

                slst.setCode(inputBean.getCode().trim());
                slst.setName(inputBean.getName());
                slst.setDescription(inputBean.getDescription());
                slst.setStatus(inputBean.getStatus());

                session.save(slst);

                txn.commit();
            } else {
                txn = session.beginTransaction();

                u.setCode(inputBean.getCode().trim());
                u.setName(inputBean.getName());
                u.setDescription(inputBean.getDescription());
                u.setStatus(inputBean.getStatus());

                session.saveOrUpdate(u);

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

}
