<%-- 
    Document   : serviceupload
    Created on : Jan 4, 2018, 4:56:27 PM
    Author     : prathibha_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="resources/css/common_popup.css" rel="stylesheet"/>
        <title>Insert Merchant</title>

        <script>

            function resetAllDataCSV() {

                $('#conXL').val("");
                $('#csvmessage').text("");
            }

            function resetCSV() {

                $('#conXL').val("");
                refreshService();

            }
            function resetMessage() {

                $('#csvmessage').text("");
            }
            function todo() {
                form = document.getElementById('serviceCsv');
                form.action = 'templateService';
                form.submit();
            }

        </script>
        
    </head>
    <body style="font-family: Raleway">
        <s:div id="csvmessage">
            <s:actionerror theme="jquery" />
            <s:actionmessage theme="jquery"/>
        </s:div>

        <s:form id="serviceCsv" method="post"   theme="simple" cssClass="form" >
            <div class="row row_popup">
                <div class="row form-inline">
                    <div class="col-sm-12" style="margin-top: 10PX;">
                        <div class="form-group">
                            <input type="hidden" name="hiddenId" id="hiddenId" value="" />
                            <%--<s:hidden id="conXL" name="conXL" ></s:hidden>--%>
                            <span style="color: red">*</span><label >Upload CSV files</label>
                            <input type="file" id="conXL" name="conXL" />
                            <%--<s:file id="conXL" name="conXL" cssClass="form-control" />--%>
                        </div>
                    </div>

                </div>
                <div class="row">
                    <div class="horizontal_line_popup"></div>
                </div>

                <div class="row row_popup form-inline">
                    <div class="col-sm-4">
                        <div class="form-group">
                            <span class="mandatoryfield">Mandatory fields are marked with <span style="color:red">*</span></span>
                        </div>
                    </div>
                    <div class="col-sm-8 text-right">
                        <div class="form-group" style=" margin-left: 10px;margin-right: 0px;">
                            <sj:submit 
                                button="true" 
                                value="Template" 
                                onClick="todo()"
                                cssClass=""
                                />                          
                        </div>
                        <div class="form-group" style=" margin-left: 0px;margin-right: 0px;">
                            <sj:submit 
                                button="true" 
                                value="Reset" 
                                onClick="resetAllDataCSV()"
                                cssClass=""
                                />                          
                        </div>               
                        <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">
                            <s:url action="uploadService" var="uploadurl"/>
                            <sj:submit 
                                button="true"
                                href="%{uploadurl}"
                                value="Upload"
                                onClickTopics=""
                                targets="csvmessage"
                                id="uploadbtncsv"
                                cssClass="" 
                                />
                        </div>

                    </div>
                </div>        


            </div>





        </s:form>
    </body>
</html>