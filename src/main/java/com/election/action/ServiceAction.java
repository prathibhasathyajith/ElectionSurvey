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
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author prathibha
 */
public class ServiceAction extends ActionSupport implements ModelDriven<Object> {

    ServiceInputBean inputBean = new ServiceInputBean();

    private String conXLFileName;
    private File conXL;
    private String serverPath;

    private InputStream inputStream;
    private String fileName;
    private long contentLength;

    public ServiceInputBean getInputBean() {
        return inputBean;
    }

    public void setInputBean(ServiceInputBean inputBean) {
        this.inputBean = inputBean;
    }

    public String getConXLFileName() {
        return conXLFileName;
    }

    public void setConXLFileName(String conXLFileName) {
        this.conXLFileName = conXLFileName;
    }

    public File getConXL() {
        return conXL;
    }

    public void setConXL(File conXL) {
        this.conXL = conXL;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

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

    public String ViewPopupcsv() {
        String result = "viewpopupcsv";
        System.out.println("called ServiceAction : ViewPopupcsv");
        try {

        } catch (Exception ex) {
            addActionError("Merchant Mgt error occurred while processing");
            Logger.getLogger(ServiceAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String template() {
        System.out.println("called ServiceAction: template");
        try {
            ServletContext context = ServletActionContext.getServletContext();
            String destPath = context.getRealPath("/resources/csv_temp/service_list");
            File fileToDownload = new File(destPath, "serviceList.csv");
            inputStream = new FileInputStream(fileToDownload);
            fileName = fileToDownload.getName();
            contentLength = fileToDownload.length();
        } catch (Exception e) {
            Logger.getLogger(ServiceAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("ServiceAction error occurred while processing");
            return "message";
        }
        return "excelreport";
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

    public String upload() {
        System.out.println("called ServiceAction : upload");
        String result = "messagecsv";
        Scanner content = null;
        ServletContext context = ServletActionContext.getServletContext();
        this.serverPath = context.getRealPath("/resources/csv_temp/service_list");

        try {
            if (inputBean.getHiddenId() != null) {
                HttpServletRequest request = ServletActionContext.getRequest();
                System.err.println(request.getParameter("conXL"));
                ServiceDAO dao = new ServiceDAO();

                String message = "";
                String token = "";
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date();
                System.out.println();

                message = this.getFile(this.conXLFileName); // get file
                System.err.println("message :" + message);
                System.err.println(this.conXLFileName);
                inputBean.setFilename(this.conXLFileName);
                this.conXLFileName = dateFormat.format(date) + this.conXLFileName;

                File directory = new File(serverPath);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                if (-1 != this.conXLFileName.lastIndexOf("\\")) {
                    this.conXLFileName = this.conXLFileName.substring(this.conXLFileName.lastIndexOf("\\") + 1);
                }
                File filetoCreate = new File(serverPath, this.conXLFileName);

                if (message.isEmpty()) {

                    if (this.conXL == null) {
                    } else {
                        FileUtils.copyFile(this.conXL, filetoCreate);
                    }

                    content = new Scanner(this.conXL).useDelimiter("\\Z");
                    String[] parts;
                    int countrecord = 1;
                    int succesrec = 0;
                    boolean getline = false;
                    while (content.hasNextLine()) {
                        if (getline) {
                            token = content.nextLine();
                            System.err.println(token);
                            parts = token.split("\\,");
                            try {
                                inputBean.setCode(parts[0].trim());
                                inputBean.setName(parts[1].trim());
                                inputBean.setDescription(parts[2].trim());
                                inputBean.setStatus(parts[3].trim());
                            } catch (Exception ee) {
                                message = "File has incorrectly ordered records";
                            }
                            countrecord++;
                            if (parts.length == 4 && message.isEmpty()) {
                                message = this.validateUploads();
                                if (message.isEmpty()) {
                                    message = dao.insertupdatetSL(inputBean);
                                    if (message.isEmpty()) {
                                        succesrec++;
                                    }
                                } else {
                                    message = message + " at line number " + countrecord + ",success count :" + succesrec;
                                    break;
                                }

                            } else {
                                message = "File has incorrectly ordered records at line number " + countrecord + ",success count :" + succesrec;
                            }
                        } else {
                            getline = true;
                            content.nextLine();
                        }
                    }

                }
                if (message.isEmpty()) {
                    addActionMessage("File uploaded successfully");
                    System.err.println("File uploaded successfully");

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            addActionError("Service error occurred while processing");
            Logger.getLogger(ServiceAction.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (content != null) {
                content.close();
            }
        }
        return result;
    }

    public String getFile(String file) {
        String mesEx = "";
        if (file != null) {
            mesEx = this.isCSV(this.conXLFileName);
        } else {
            mesEx = "Please choose a file to upload.";
        }
        return mesEx;
    }

    public static String isCSV(String filename) {
        String message = "";

        List<String> extensions = new ArrayList<String>();
        extensions.add("csv");

        int pos = filename.lastIndexOf('.') + 1;
        String ext = filename.substring(pos);
        String final_ext = ext.toLowerCase();

        for (int i = 0; i < extensions.size(); i++) {
            if (extensions.get(i).equals(final_ext)) {
                return message;

            }
        }
        message = "Please upload file with following extension:";
        for (int i = 0; i < extensions.size(); i++) {
            message = message + extensions.get(i);

        }
        return message;
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

    private String validateUploads() {
        String message = "";
        if (inputBean.getCode() == null || inputBean.getCode().trim().isEmpty()) {
            message = "Service code cannot be empty";
        } else if (inputBean.getName() == null || inputBean.getName().trim().isEmpty()) {
            message = "Service Name cannot be empty";
        } else if (inputBean.getDescription() != null && inputBean.getDescription().isEmpty()) {
            message = "Service Description cannot be empty";
        } else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
            message = "Status cannot be empty";
        } else {
            if (inputBean.getCode().length() > 45) {
                message = "Service code length should be lower than 45";
            } else if (inputBean.getStatus().equals("ACT")) {
                message = "";
            } else if (inputBean.getStatus().equals("DEACT")) {
                message = "";
            }else{
                message = "Status code invalid";
            }
        }

        return message;
    }

}
