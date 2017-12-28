/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.election.action;

import com.election.bean.PartyBean;
import com.election.bean.PartyInputBean;
import com.election.bean.Type;
import com.election.dao.PartyDAO;
import com.election.mapping.Party;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author prathibha
 */
public class PartyAction extends ActionSupport implements ModelDriven<Object> {

    PartyInputBean inputBean = new PartyInputBean();

    @Override
    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called PartyAction : execute");
        return SUCCESS;
    }

    public String view() {
        System.out.println("called PartyAction : view");
        String msg = "view";
        try {
            List<Type> statusList = new ArrayList<Type>();
            List<Type> typeList = new ArrayList<Type>();

            Type status = new Type();
            status.setCode("ACT");
            status.setDescription("Active");
            statusList.add(status);
            status = new Type();
            status.setCode("DEAT");
            status.setDescription("Deactive");
            statusList.add(status);

            inputBean.setStatusList(statusList);

            status = new Type();
            status.setCode("PARTY");
            status.setDescription("Party");
            typeList.add(status);
            status = new Type();
            status.setCode("INDE");
            status.setDescription("Independent");
            typeList.add(status);

            inputBean.setTypeList(typeList);

        } catch (Exception e) {
            addActionError("Party error occurred while processing");
            Logger.getLogger(PartyAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return msg;
    }

    public String list() {
        System.out.println("called PartyAction: List");
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
            PartyDAO dao = new PartyDAO();

            List<PartyBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);

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
            addActionError("Party list error occurred while processing");
            Logger.getLogger(PartyAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "list";
    }

    public String add() {
        System.out.println("called PartyAction : add");
        String result = "message";
        BufferedImage readImage = null;
        File destFile;
        ServletContext context = ServletActionContext.getServletContext();
        String destPath = context.getRealPath("/resources/images/logo");

        try {

            PartyDAO dao = new PartyDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                message = this.getImageLogo(inputBean.getLogoImgFileName()); // get file

                if (message.isEmpty()) {

                    destFile = new File(destPath, inputBean.getLogoImgFileName() + ".jpg");
                    FileUtils.copyFile(inputBean.getLogoImg(), destFile);
                    readImage = ImageIO.read(new File(destPath, inputBean.getLogoImgFileName() + ".jpg"));

                    int height = readImage.getHeight();
                    int width = readImage.getWidth();

                    System.err.println(height + " " + width);
//                    if (height != 92) {
//                        message = "Logo height should be 92 pixels";
//                    }
//                    if (width > 280) {
//                        message = "Logo width should be less than 280 pixels";
//                    }
                }

                if (message.isEmpty()) {

                    message = dao.insertParty(inputBean);

                    if (message.isEmpty()) {
                        addActionMessage("Party created successfully");
                    } else {
                        addActionError(message);
                    }
                }
            } else {
                addActionError(message);
            }

        } catch (Exception e) {
            addActionError("Party add error occurred while processing");
            Logger.getLogger(PartyAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String update() {
        System.out.println("called PartyAction : update");
        String result = "message";

        BufferedImage readImage = null;
        File destFile;
        ServletContext context = ServletActionContext.getServletContext();
        String destPath = context.getRealPath("/resources/images/party");

        try {
            if (inputBean.getPartyCode() != null && !inputBean.getPartyCode().isEmpty()) {

                String message = this.validateUpdates();

                if (message.isEmpty()) {

                    PartyDAO dao = new PartyDAO();

                    if (inputBean.getLogoImgFileName() != null) {

                        message = this.getImageLogo(this.inputBean.getLogoImgFileName()); // get file

                        if (message.isEmpty()) {

                            destFile = new File(destPath, inputBean.getLogoImgFileName() + ".jpg");
                            FileUtils.copyFile(inputBean.getLogoImg(), destFile);
                            readImage = ImageIO.read(new File(destPath, inputBean.getLogoImgFileName() + ".jpg"));

                            int height = readImage.getHeight();
                            int width = readImage.getWidth();

                            System.err.println(height + " " + width);
//                            if (height != 92) {
//                                message = "Logo height should be 92 pixels";
//                            }
//                            if (width > 92) {
//                                message = "Logo width should be less than 280 pixels";
//                            }
                        }

                    } else if (dao.isexsitsImg(inputBean.getPartyCode())) {
                        message = "Logo cannot be empty";
                    }
                    if (message.isEmpty()) {
                        message = dao.updateParty(inputBean);
                        if (message.isEmpty()) {
                            addActionMessage("Party updated successfully");
                        } else {
                            addActionError(message);
                        }
                    } else {
                        addActionError(message);
                    }
                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            addActionError("Party update error occurred while processing");
            Logger.getLogger(PartyAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public String find() {
        System.out.println("called PartyAction: find");
        Party party = null;
        try {
            if (inputBean.getPartyCode() != null && !inputBean.getPartyCode().isEmpty()) {

                PartyDAO dao = new PartyDAO();

                party = dao.findPartyByCode(inputBean.getPartyCode());

                inputBean.setPartyCode(party.getPartyCode().trim());
                inputBean.setName(party.getName().trim());
                inputBean.setDescription(party.getDescription().trim());
                inputBean.setType(party.getType().trim());
                inputBean.setContactNo(party.getContactNo().trim());
                inputBean.setEmail(party.getEmail().trim());
                inputBean.setAddress(party.getAddress().trim());
                inputBean.setStatus(party.getStatus().trim());

                try {
                    inputBean.setEditLogo(party.getImage());
                } catch (Exception ex) {
                }

            } else {
                inputBean.setMessage("Empty code");
            }
        } catch (Exception ex) {
            addActionError("Party find error occurred while processing");
            Logger.getLogger(PartyAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    public String delete() {
        System.out.println("called PartyAction : Delete");
        String message = null;
        String retType = "delete";
        try {

            PartyDAO dao = new PartyDAO();
            message = dao.deletePage(inputBean);
            if (message.isEmpty()) {
                message = "Party deleted successfully";
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            addActionError("Party delete error occurred while processing");
            Logger.getLogger(PartyAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return retType;
    }

    private String validateInputs() {
        String message = "";
        if (inputBean.getPartyCode() == null || inputBean.getPartyCode().trim().isEmpty()) {
            message = "Party code cannot be empty";
        } else if (inputBean.getName() == null || inputBean.getName().trim().isEmpty()) {
            message = "Name cannot be empty";
        } else if (inputBean.getDescription() != null && inputBean.getDescription().isEmpty()) {
            message = "Description cannot be empty";
        } else if (inputBean.getType() == null || inputBean.getType().trim().isEmpty()) {
            message = "Type cannot be empty";
        } else if (inputBean.getContactNo() == null || inputBean.getContactNo().trim().isEmpty()) {
            message = "Contact no cannot be empty";
        } else if (inputBean.getEmail() == null || inputBean.getEmail().trim().isEmpty()) {
            message = "Email cannot be empty";
        } else if (inputBean.getAddress() == null || inputBean.getAddress().trim().isEmpty()) {
            message = "Address cannot be empty";
        } else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
            message = "Status cannot be empty";
        } else if (inputBean.getLogoImgFileName() == null || inputBean.getLogoImgFileName().trim().isEmpty()) {
            message = "Logo cannot be empty";
        }
        return message;
    }

    private String validateUpdates() {
        String message = "";
        if (inputBean.getPartyCode() == null || inputBean.getPartyCode().trim().isEmpty()) {
            message = "Party code cannot be empty";
        } else if (inputBean.getName() == null || inputBean.getName().trim().isEmpty()) {
            message = "Name cannot be empty";
        } else if (inputBean.getDescription() != null && inputBean.getDescription().isEmpty()) {
            message = "Description cannot be empty";
        } else if (inputBean.getType() == null || inputBean.getType().trim().isEmpty()) {
            message = "Type cannot be empty";
        } else if (inputBean.getContactNo() == null || inputBean.getContactNo().trim().isEmpty()) {
            message = "Contact no cannot be empty";
        } else if (inputBean.getEmail() == null || inputBean.getEmail().trim().isEmpty()) {
            message = "Email cannot be empty";
        } else if (inputBean.getAddress() == null || inputBean.getAddress().trim().isEmpty()) {
            message = "Address cannot be empty";
        } else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
            message = "Status cannot be empty";
        }
//        else if (inputBean.getLogoImgFileName() == null || inputBean.getLogoImgFileName().trim().isEmpty()) {
//            message = "Logo cannot be empty";
//        }
        return message;
    }

    public String getImageLogo(String file) {
        String msgEx = "";
        if (file == null) {
            msgEx = "Please choose a file to upload.";
        } else {
            msgEx = this.isImageLogo(file);
        }
        return msgEx;
    }

    public static String isImageLogo(String filename) {
        String message = "";

        List<String> extensions = new ArrayList<String>();
        extensions.add("jpg");
        extensions.add("png");
        extensions.add("img");
        extensions.add("gif");

        int pos = filename.lastIndexOf('.') + 1;
        String ext = filename.substring(pos);
        String final_ext = ext.toLowerCase();

        for (int i = 0; i < extensions.size(); i++) {
            if (extensions.get(i).equals(final_ext)) {
                return message;

            }
        }
        message = "Logo : Please upload image with one of the following extensions:";
        for (int i = 0; i < extensions.size(); i++) {
            message = message + extensions.get(i) + ",";

        }
        return message;
    }

}
