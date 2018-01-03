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
                return "<a href='#' title='Edit' onClick='javascript:editPartyLA(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#/' title='Delete' onClick='javascript:deletePartyLAInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function editPartyLA(keyval) {
                
                $("#id").val(keyval);
                
                $.ajax({
                    url: '${pageContext.request.contextPath}/findPartyLA.action',
                    data: {id: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {

                            $('#la').val("");
                            $('#party').val("");
                            $('#contactNo').val("");
                            $('#address').val("");
                            $('#status').val("");
                            $('#updateButton').button("disable");

                        } else {

                            $('#la').val(data.la);
                            $('#party').val(data.party);
                            $('#contactNo').val(data.contactNo);
                            $('#address').val(data.address);
                            $('#status').val(data.status);

                            $('#addButton').button("disable");
                            $('#updateButton').button("enable");
                            searchParams();
                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutLogin.action?";
                    }
                });
            }

            function deletePartyLAInit(keyval) {
                $('#divmsg').empty();

                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete party - LA : ' + keyval + ' ?');
                return false;
            }

            function deletePartyLA(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deletePartyLA.action',
                    data: {id: keyval},
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


            function resetAllData() {
                a = $("#addButton").is(':disabled');
                u = $("#updateButton").is(':disabled');

                if (a == true && u == true) {
                    editPartyLA(null);
                } else if (a == true && u == false) {
                    var id = $("#id").val();
                    alert(id);
                    editPartyLA(id);
                } else if (a == false && u == true) {
                    editPartyLA(null);
                }
            }

            function resetFieldData() {

                $('#la').val("");
                $('#party').val("");
                $('#contactNo').val("");
                $('#address').val("");
                $('#status').val("");
                
                $('#addButton').button("enable");
                $('#updateButton').button("disable");

                jQuery("#gridtable").trigger("reloadGrid");
            }
            function cancelPageAllData() {
                editPartyLA(null);
                emptySearchParams();
                $('#addButton').button("enable");
                $('#updateButton').button("disable");
            }
            
            function searchParams() {
                var localAuthority = $('#la').val();
                var party = $('#party').val();
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        la: localAuthority,
                        party: party,
                        search: true
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }
            
            function emptySearchParams() {

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        la: '',
                        party: '',
                        search: false
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
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
            <div class="cont-breadCrumb">Party - Local Authority Management</div>
            <div class="cont-msg">
                <s:div id="divmsg">
                    <s:actionerror theme="jquery"/>
                    <s:actionmessage theme="jquery"/>
                </s:div>
            </div>
            <div class="cont-form">
                <s:form id="partyLAForm" method="post" action="PartyLA" theme="simple" cssClass="form" enctype="multipart/form-data">
                    <s:hidden id="oldvalue" name="oldvalue" ></s:hidden>
                    <s:hidden id="id" name="id" ></s:hidden>
                        <div class="col-sm-3">
                            <div class="form-group">
                                <span style="color: red">*</span><label>Local Authority</label>
                            <s:select onchange="searchParams();" cssClass="form-control" id="la" list="%{laList}"  headerValue="--Select Local Authority--" headerKey="" name="la" listKey="code" listValue="description" />
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Party</label>
                            <s:select onchange="" cssClass="form-control" id="party" list="%{partyList}"  headerValue="--Select Party--" headerKey="" name="party" listKey="partyCode" listValue="partyCode" />
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Contact No</label>
                            <s:textfield cssClass="form-control" name="contactNo" id="contactNo" maxLength="15" onkeyup="$(this).val($(this).val().replace(/[^0-9+#]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9#+]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Address</label>
                            <s:textfield cssClass="form-control" name="address" id="address" maxLength="255" />
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Status</label>
                            <s:select  cssClass="form-control" id="status" list="%{statusList}"  headerValue="--Select Status--" headerKey="" name="status" listKey="code" listValue="description" />
                        </div>
                    </div>


                    <s:url var="addurl" action="addPartyLA"/>
                    <s:url var="updateurl" action="updatePartyLA"/>

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
                'OK':function() { deletePartyLA($(this).data('keyval'));$( this ).dialog( 'close' ); },
                'Cancel':function() { $( this ).dialog( 'close' );} 
                }" 
                autoOpen="false" 
                modal="true" 
                title="Delete PartyLA"                            
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
                <s:url var="listurl" action="listPartyLA"/>
                <sjg:grid
                    id="gridtable"
                    caption="Party-LA List Management"
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
                    <sjg:gridColumn name="id" index="u.id" title="Edit" width="50" align="center" formatter="editformatter" sortable="false" />
                    <sjg:gridColumn name="id" index="u.id" title="Delete" width="40" align="center" formatter="deleteformatter" sortable="false" />  
                    <sjg:gridColumn name="id" index="u.id" title="ID"  sortable="true" hidden="true"/>
                    <sjg:gridColumn name="laCode" index="u.localAuthority.description" title="Local Autority"  sortable="true"/>
                    <sjg:gridColumn name="partyCode" index="u.party.description" title="Party"  sortable="true"/>
                    <sjg:gridColumn name="contactNo" index="u.contactNo" title="Contact No"  sortable="true"/>
                    <sjg:gridColumn name="address" index="u.address" title="Address"  sortable="true"/>
                    <sjg:gridColumn name="status" index="u.status" title="Status"  sortable="true" />
                </sjg:grid> 
            </div>
        </div>
        <!--footer-->
        <jsp:include page="/footer.jsp"/>
    </body>
</html>