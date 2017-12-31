<%-- 
    Document   : party
    Created on : Dec 25, 2017, 11:06:56 AM
    Author     : prathibha
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
                return "<a href='#' title='Edit' onClick='javascript:editParty(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#/' title='Delete' onClick='javascript:deletePartyInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function editParty(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findParty.action',
                    data: {partyCode: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            $('#partyCode').val("");
                            $("#partyCode").css("color", "black");
                            $('#partyCode').attr('readOnly', false);
                            $('#name').val("");
                            $('#description').val("");
                            $('#type').val("");
                            $('#address').val("");
                            $('#status').val("");
                            $('#email').val("");
                            $('#contactNo').val("");
                            $('#divmsg').text("");
                            $('#updateButton').button("disable");
                            $('#logoImg').val("");
                        } else {

                            $('#partyCode').val(data.partyCode);
                            $("#partyCode").css("color", "#858585");
                            $('#partyCode').attr('readOnly', true);
                            $('#name').val(data.name);
                            $('#description').val(data.description);
                            $('#type').val(data.type);
                            $('#address').val(data.address);
                            $('#status').val(data.status);
                            $('#email').val(data.email);
                            $('#contactNo').val(data.contactNo);
                            $('#logoImg').val("")

                            $("#logo_add").attr("src", "data:image/jpeg;base64," + data.editLogoImg);

                            $('#addButton').button("disable");
                            $('#updateButton').button("enable");

                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutLogin.action?";
                    }
                });
            }

            function deletePartyInit(keyval) {
                $('#divmsg').empty();

                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete party : ' + keyval + ' ?');
                return false;
            }

            function deleteParty(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteParty.action',
                    data: {partyCode: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        resetFieldData();
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutLogin.action?";
                    }
                });
            }

            function changeLogo() {
                $("#logoImg").change(function (event) {
                    var tmppath = URL.createObjectURL(event.target.files[0]);
                    $("#logo_add").attr("src", tmppath);
                });
            }
            function resetAllData() {
                a = $("#addButton").is(':disabled');
                u = $("#updateButton").is(':disabled');

                if (a == true && u == true) {
                    editParty(null);
                } else if (a == true && u == false) {
                    var partyCode = $('#partyCode').val();
                    $('#partyCode').attr('readOnly', true);
                    editParty(partyCode);
                } else if (a == false && u == true) {
                    editParty(null);
                }
            }

            function resetFieldData() {

                $('#partyCode').val("");
                $("#partyCode").css("color", "black");
                $('#partyCode').attr('readOnly', false);
                $('#name').val("");
                $('#description').val("");
                $('#type').val("");
                $('#address').val("");
                $('#status').val("");
                $('#email').val("");
                $('#contactNo').val("");

                $('#logoImg').val("");
                $("#logo_add").attr("src", "${pageContext.request.contextPath}/resources/images/logo/image.png");

                $('#addButton').button("enable");
                $('#updateButton').button("disable");

                jQuery("#gridtable").trigger("reloadGrid");
            }
            function cancelPageAllData() {
                editParty(null);
                $('#addButton').button("enable");
                $('#updateButton').button("disable");
                $("#logo_add").attr("src", '${pageContext.request.contextPath}/resources/images/logo/image.png');
            }


        </script>
    </head>
    <body>
        <!--header-->
        <jsp:include page="/header.jsp"/>
        <!--nav bar-->
        <jsp:include page="/navbar.jsp"/>
        <!--body content-->
        <div class="cont-body">
            <div class="cont-breadCrumb">Party Management</div>
            <div class="cont-msg">
                <s:div id="divmsg">
                    <s:actionerror theme="jquery"/>
                    <s:actionmessage theme="jquery"/>
                </s:div>
            </div>
            <div class="cont-form">
                <s:form id="partyForm" method="post" action="Party" theme="simple" cssClass="form" enctype="multipart/form-data">
                    <s:hidden id="oldvalue" name="oldvalue" ></s:hidden>
                        <div class="col-sm-3">
                            <div class="form-group">
                                <span style="color: red">*</span><label>Party Code</label>
                            <s:textfield cssClass="form-control" name="partyCode" id="partyCode" maxLength="45" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Name</label>
                            <s:textfield cssClass="form-control" name="name" id="name" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Description</label>
                            <s:textfield cssClass="form-control" name="description" id="description" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Type</label>
                            <s:select  cssClass="form-control" id="type" list="%{typeList}"  headerValue="--Select Type--" headerKey="" name="type" listKey="code" listValue="description" />
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Contact No</label>
                            <s:textfield cssClass="form-control" name="contactNo" id="contactNo" maxLength="12" onkeyup="$(this).val($(this).val().replace(/[^0-9+#]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9#+]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Email</label>
                            <s:textfield cssClass="form-control" name="email" id="email" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Address</label>
                            <s:textfield cssClass="form-control" name="address" id="address" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Status</label>
                            <s:select  cssClass="form-control" id="status" list="%{statusList}"  headerValue="--Select Status--" headerKey="" name="status" listKey="code" listValue="description" />
                        </div>
                    </div>

                    <div class="col-sm-8">
                        <div class="form-group">
                            <span style="color: red">*</span><label >Party Logo </label><br><label>(Maximum size (w*h): 100x100 pixels)</label>
                            <div class="row">
                                <div class="col-sm-2">
                                    <img class="image_card" src='${pageContext.request.contextPath}/resources/images/logo/image.png' id="logo_add" width="100" height="100">
                                </div>
                                <div class="col-sm-6" style="margin-top: 40px;">
                                    <s:file name="logoImg" id="logoImg" onclick="changeLogo()"/>                               
                                </div>
                            </div>
                        </div>
                    </div>

                    <s:url var="addurl" action="addParty"/>
                    <s:url var="updateurl" action="updateParty"/>

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

                        </div>
                    </div>
                </s:form>
            </div>

            <!-- Start delete confirm dialog box -->
            <sj:dialog 
                id="deletedialog" 
                buttons="{ 
                'OK':function() { deleteParty($(this).data('keyval'));$( this ).dialog( 'close' ); },
                'Cancel':function() { $( this ).dialog( 'close' );} 
                }" 
                autoOpen="false" 
                modal="true" 
                title="Delete Party"                            
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
                <s:url var="listurl" action="listParty"/>
                <sjg:grid
                    id="gridtable"
                    caption="Party Management"
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
                    <%--<sjg:gridColumn name="partyId" index="u.partyId" title="partyId"  sortable="true" hidden="false"/>--%>
                    <sjg:gridColumn name="partyCode" index="u.partyCode" title="Edit" width="50" align="center" formatter="editformatter" sortable="false" />
                    <sjg:gridColumn name="partyCode" index="u.partyCode" title="Delete" width="40" align="center" formatter="deleteformatter" sortable="false" />  
                    <sjg:gridColumn name="partyCode" index="u.partyCode" title="Party Code"  sortable="true" frozen="true"/>
                    <sjg:gridColumn name="name" index="u.name" title="Name"  sortable="true"/>
                    <sjg:gridColumn name="description" index="u.description" title="Description"  sortable="true"/>
                    <sjg:gridColumn name="type" index="u.type" title="Type"  sortable="true"/>
                    <sjg:gridColumn name="contactNo" index="u.contactNo" title="Contact No"  sortable="true" />
                    <sjg:gridColumn name="email" index="u.email" title="Email"  sortable="true" />
                    <sjg:gridColumn name="address" index="u.address" title="Address"  sortable="true" />
                    <sjg:gridColumn name="status" index="u.status" title="Status"  sortable="true" />
                </sjg:grid> 
            </div>
        </div>
        <!--footer-->
        <jsp:include page="/footer.jsp"/>
    </body>
</html>
