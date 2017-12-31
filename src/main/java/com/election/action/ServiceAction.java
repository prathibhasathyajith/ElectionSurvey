/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.action;

import com.election.bean.PartyBean;
import com.election.bean.ServiceBean;
import com.election.bean.ServiceInputBean;
import com.election.bean.Type;
import com.election.dao.ServiceDAO;
import com.election.mapping.ServiceList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prathibha
 */
public class ServiceAction extends ActionSupport implements ModelDriven<Object> {

    ServiceInputBean inputBean = new ServiceInputBean();

    @Override
    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called ServiceAction : execute");
        return SUCCESS;
    }

    public String view() {
        System.out.println("called ServiceAction : view");
        String msg = "view";
        try {
            List<Type> statusList = new ArrayList<Type>();

            Type status = new Type();
            status.setCode("ACT");
            status.setDescription("Active");
            statusList.add(status);
            status = new Type();
            status.setCode("DEAT");
            status.setDescription("Deactive");
            statusList.add(status);

            inputBean.setStatusList(statusList);

        } catch (Exception e) {
            addActionError("Service view error occurred while processing");
            Logger.getLogger(ServiceAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }

    public String list() {
        System.out.println("called ServiceAction: List");
        try {

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }
            ServiceDAO dao = new ServiceDAO();

            List<ServiceBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);

            if (!dataList.isEmpty()) {
                records = dataList.get(0).getFullCount();
                inputBean.setRecords(records);
                inputBean.setGridModel(dataList);
                int total = (int) Math.ceil((double) records / (double) rows);
                inputBean.setTotal(total);
            } else {
                inputBean.setRecords(0L);
                inputBean.setTotal(0);
            }

        } catch (Exception e) {
            addActionError("Service list error occurred while processing");
            Logger.getLogger(ServiceAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    public String add() {
        System.out.println("called ServiceAction : add");
        String result = "message";

        try {

            ServiceDAO dao = new ServiceDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                message = dao.insertService(inputBean);

                if (message.isEmpty()) {
                    addActionMessage("Service created successfully");
                } else {
                    addActionError(message);
                }

            } else {
                addActionError(message);
            }

        } catch (Exception e) {
            addActionError("Service add error occurred while processing");
            Logger.getLogger(ServiceAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String update() {
        System.out.println("called ServiceAction : update");
        String result = "message";
        try {
            if (inputBean.getCode() != null && !inputBean.getCode().isEmpty()) {

                ServiceDAO dao = new ServiceDAO();
                String message = this.validateInputs();

                if (message.isEmpty()) {
                    message = dao.updateService(inputBean);
                    if (message.isEmpty()) {
                        addActionMessage("Service updated successfully");
                    } else {
                        addActionError(message);
                    }
                } else {
                    addActionError(message);
                }

            }
        } catch (Exception ex) {
            addActionError("Service update error occurred while processing");
            Logger.getLogger(ServiceAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public String find() {
        System.out.println("called ServiceAction: find");
        ServiceList serviceList = null;
        try {

            System.out.println("serviceList id " + inputBean.getId());
            System.out.println("serviceList code " + inputBean.getCode());

            if (inputBean.getCode() != null && !inputBean.getCode().isEmpty()) {

                ServiceDAO dao = new ServiceDAO();

                serviceList = dao.findService(inputBean.getCode());

                inputBean.setCode(serviceList.getCode());
                inputBean.setName(serviceList.getName());
                inputBean.setDescription(serviceList.getDescription());
                inputBean.setStatus(serviceList.getStatus());

            } else {
                inputBean.setMessage("Empty detail");
            }
        } catch (Exception ex) {
            addActionError("Service find error occurred while processing");
            Logger.getLogger(ServiceAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    public String delete() {
        System.out.println("called ServiceAction : Delete");
        String message = null;
        String retType = "delete";
        try {

            ServiceDAO dao = new ServiceDAO();
            message = dao.deleteService(inputBean);
            if (message.isEmpty()) {
                message = "Service deleted successfully";
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            addActionError("Service delete error occurred while processing");
            Logger.getLogger(ServiceAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return retType;
    }

    private String validateInputs() {
        String message = "";
        if (inputBean.getCode() == null || inputBean.getCode().trim().isEmpty()) {
            message = "Service code cannot be empty";
        } else if (inputBean.getName() == null || inputBean.getName().trim().isEmpty()) {
            message = "Service Name cannot be empty";
        } else if (inputBean.getDescription() != null && inputBean.getDescription().isEmpty()) {
            message = "Service Description cannot be empty";
        } else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
            message = "Status cannot be empty";
        }

        return message;
    }

}
