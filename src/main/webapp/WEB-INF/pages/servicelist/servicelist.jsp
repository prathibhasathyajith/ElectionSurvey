<%-- 
    Document   : servicelist
    Created on : Dec 26, 2017, 3:31:31 PM
    Author     : prathibha_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Election</title>
        <%@include file="/stylelinks.jspf" %>
        <script>

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Edit' onClick='javascript:editService(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#/' title='Delete' onClick='javascript:deleteServiceInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function editService(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findService.action',
                    data: {code: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            $('#code').val("");
                            $("#code").css("color", "black");
                            $('#code').attr('readOnly', false);
                            $('#name').val("");
                            $('#description').val("");
                            $('#status').val("");
                            $('#updateButton').button("disable");

                        } else {

                            $('#code').val(data.code);
                            $("#code").css("color", "#858585");
                            $('#code').attr('readOnly', true);
                            $('#name').val(data.name);
                            $('#description').val(data.description);
                            $('#status').val(data.status);

                            $('#addButton').button("disable");
                            $('#updateButton').button("enable");

                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/logoutLogin.action?";
                    }
                });
            }

            function deleteServiceInit(keyval) {
                $('#divmsg').empty();

                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete service : ' + keyval + ' ?');
                return false;
            }

            function deleteService(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteService.action',
                    data: {id: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        resetFieldData();
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/logoutLogin.action?";
                    }
                });
            }


            function resetAllData() {
                a = $("#addButton").is(':disabled');
                u = $("#updateButton").is(':disabled');

                if (a == true && u == true) {
                    editService(null);
                } else if (a == true && u == false) {
                    var code = $('#code').val();
                    $('#code').attr('readOnly', true);
                    editService(code);
                } else if (a == false && u == true) {
                    editService(null);
                }
            }

            function resetFieldData() {

                $('#code').val("");
                $("#code").css("color", "black");
                $('#code').attr('readOnly', false);
                $('#name').val("");
                $('#description').val("");
                $('#status').val("");

                $('#addButton').button("enable");
                $('#updateButton').button("disable");

                jQuery("#gridtable").trigger("reloadGrid");
            }
            function cancelPageAllData() {
                editService(null);
                $('#addButton').button("enable");
                $('#updateButton').button("disable");
            }
            function refreshService() {
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }


        </script>
        <style>
            .ui-jqgrid .ui-jqgrid-title {
                font-size: 13px;
                padding-left: 10px;
            }
            .dialogclass {
                height: 100%;
                width: 100%;
                top: 0px;
                left: 0px;
                display: block;
                margin-left: 0px;
                z-index: 10000;
                margin-top: 0;
                position: fixed;
            }
        </style>
    </head>
    <body>
        <!--header-->
        <jsp:include page="/header.jsp"/>
        <!--nav bar-->
        <jsp:include page="/navbar.jsp"/>
        <!--body content-->
        <div class="cont-body">
            <div class="cont-breadCrumb">Service List Management</div>
            <div class="cont-msg">
                <s:div id="divmsg">
                    <s:actionerror theme="jquery"/>
                    <s:actionmessage theme="jquery"/>
                </s:div>
            </div>
            <div class="cont-form">
                <s:form id="serviceForm" method="post" action="Service" theme="simple" cssClass="form" enctype="multipart/form-data">
                    <s:hidden id="oldvalue" name="oldvalue" ></s:hidden>
                        <div class="col-sm-3">
                            <div class="form-group">
                                <span style="color: red">*</span><label>Service Code</label>
                            <s:textfield cssClass="form-control" name="code" id="code" maxLength="45" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Name</label>
                            <s:textfield cssClass="form-control" name="name" id="name" maxLength="255" />
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Description</label>
                            <s:textfield cssClass="form-control" name="description" id="description" maxLength="255" />
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Status</label>
                            <s:select  cssClass="form-control" id="status" list="%{statusList}"  headerValue="--Select Status--" headerKey="" name="status" listKey="code" listValue="description" />
                        </div>
                    </div>


                    <s:url var="addurl" action="addService"/>
                    <s:url var="updateurl" action="updateService"/>
                    <s:url var="uploadurl" action="ViewPopupcsvService"/>   

                    <div class="row row_1 form-inline">
                        <div class="col-sm-12">
                            <div class="form-group">
                                <sj:submit button="true" href="%{addurl}" value="Add" targets="divmsg" id="addButton" 
                                           cssClass="btn btn-success"/>
                            </div>
                            <div class="form-group">
                                <sj:submit button="true" href="%{updateurl}" value="Update" targets="divmsg" id="updateButton"
                                           cssClass="btn btn-primary" disabled="true"/>
                            </div>
                            <div class="form-group">
                                <sj:submit button="true" value="Reset" name="reset" onClick="resetAllData()" 
                                           cssClass="btn btn-default"/>
                            </div>
                            <div class="form-group">
                                <sj:submit button="true" value="Cancel" name="cancel" onClick="cancelPageAllData()"
                                           cssClass="btn btn-default"/>
                            </div>
                            <div class="form-group">
                                <sj:submit                                                      
                                    openDialog="remotedialog"
                                    button="true"
                                    href="%{uploadurl}"
                                    disabled="#vupload"
                                    value="Upload Service List"
                                    id="uploadButton"  
                                    cssClass="btn btn-warning"
                                    cssStyle="margin-left:30px"
                                    />
                            </div>

                        </div>
                    </div>
                </s:form>
            </div>

            <sj:dialog                                     
                id="remotedialog"                                 
                autoOpen="false" 
                modal="true" 
                title="Upload Service List"                            
                loadingText="Loading .."                            
                position="center"                            
                width="650"
                height="350"
                dialogClass= "dialogclass"
                />
            <!-- Start delete confirm dialog box -->
            <sj:dialog 
                id="deletedialog" 
                buttons="{ 
                'OK':function() { deleteService($(this).data('keyval'));$( this ).dialog( 'close' ); },
                'Cancel':function() { $( this ).dialog( 'close' );} 
                }" 
                autoOpen="false" 
                modal="true" 
                title="Delete Service"                            
                />
            <!-- Start delete process dialog box -->
            <sj:dialog 
                id="deletesuccdialog" 
                buttons="{
                'OK':function() { $( this ).dialog( 'close' );}
                }"  
                autoOpen="false" 
                modal="true" 
                title="Deleting Process." 
                />
            <!-- Start delete error dialog box -->
            <sj:dialog 
                id="deleteerrordialog" 
                buttons="{
                'OK':function() { $( this ).dialog( 'close' );}                                    
                }" 
                autoOpen="false" 
                modal="true" 
                title="Delete error."
                />

            <div class="cont-table">
                <s:url var="listurl" action="listService"/>
                <sjg:grid
                    id="gridtable"
                    caption="Service List Management"
                    dataType="json"
                    href="%{listurl}"
                    pager="true"
                    gridModel="gridModel"
                    rowList="10,15,20"
                    rowNum="10"
                    autowidth="true"
                    rownumbers="true"
                    onCompleteTopics="completetopics"
                    rowTotal="false"
                    viewrecords="true"
                    onErrorTopics="anyerrors" 
                    >
                    <sjg:gridColumn name="code" index="u.code" title="Edit" width="50" align="center" formatter="editformatter" sortable="false" />
                    <sjg:gridColumn name="id" index="u.id" title="Delete" width="40" align="center" formatter="deleteformatter" sortable="false" />  
                    <sjg:gridColumn name="id" index="u.id" title="ID"  sortable="true" hidden="true"/>
                    <sjg:gridColumn name="code" index="u.code" title="Code"  sortable="true"/>
                    <sjg:gridColumn name="name" index="u.name" title="Name"  sortable="true"/>
                    <sjg:gridColumn name="description" index="u.description" title="Description"  sortable="true"/>
                    <sjg:gridColumn name="status" index="u.status" title="Status"  sortable="true" />
                </sjg:grid> 
            </div>
        </div>
        <!--footer-->
        <jsp:include page="/footer.jsp"/>
    </body>
</html>