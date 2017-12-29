<%-- 
    Document   : candidate
    Created on : Dec 26, 2017, 3:31:07 PM
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
        <title>Election Candidate</title>
        <%@include file="/stylelinks.jspf" %>
        <script>

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Edit' onClick='javascript:editCandidate(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#/' title='Delete' onClick='javascript:deleteCandidateInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function editCandidate(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findCandidate.action',
                    data: {candidateId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            $('#candidateId').val("");
                            $("#candidateId").css("color", "black");
                            $('#candidateId').attr('readOnly', false);
                            $('#party').val("");
                            $('#wardCode').val("");
                            $('#name').val("");
                            $('#nic').val("");
                            $('#contactNo').val("");
                            $('#address').val("");
                            $('#gender').val("");
                            $('#youth').text("");
                            $('#status').text("");
                            $('#updateButton').button("disable");

                        } else {

                            $('#candidateId').val(data.candidateId);
                            $("#candidateId").css("color", "#858585");
                            $('#candidateId').attr('readOnly', true);
                            $('#party').val(data.party);
                            $('#wardCode').val(data.wardCode);
                            $('#name').val(data.name);
                            $('#nic').val(data.nic);
                            $('#contactNo').val(data.contactNo);
                            $('#gender').val(data.gender);
                            $('#youth').val(data.youth);
                            $('#status').val(data.status);

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
                $("#deletedialog").html('Are you sure you want to delete Candidate : ' + keyval + ' ?');
                return false;
            }

            function deleteParty(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteCandidate.action',
                    data: {candidateId: keyval},
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
                    editCandidate(null);
                } else if (a == true && u == false) {
                    var candidateId = $('#candidateId').val();
                    $('#candidateId').attr('readOnly', true);
                    editCandidate(candidateId);
                } else if (a == false && u == true) {
                    editCandidate(null);
                }
            }

            function resetFieldData() {
                $('#candidateId').val("");
                $("#candidateId").css("color", "black");
                $('#candidateId').attr('readOnly', false);
                $('#party').val("");
                $('#wardCode').val("");
                $('#name').val("");
                $('#nic').val("");
                $('#contactNo').val("");
                $('#address').val("");
                $('#gender').val("");
                $('#youth').text("");
                $('#status').text("");

                $('#addButton').button("enable");
                $('#updateButton').button("disable");

                jQuery("#gridtable").trigger("reloadGrid");
            }
            function cancelPageAllData() {
                editCandidate(null);
                $('#addButton').button("enable");
                $('#updateButton').button("disable");
            }


            //functions for dropdown

            function changeDistrictFromProvince(keyval) {

                if (keyval == '') {
                    keyval = "empty";
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/findDistrictCandidate.action',
                    data: {province: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        var districtlist = data.districtList;
                        $("#district option").remove();
                        $('#district').append('<option value="">--Select District--</option>');
                        $.each(districtlist, function (index, item) {
                            $('#district').append("<option value='" + item.code + "'>" + item.description + "</option>");
                        });
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutLogin.action?";
                    }
                });
            }

            function changeLAFromDistrict(keyval) {

                if (keyval == '') {
                    keyval = "empty";
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/findLACandidate.action',
                    data: {district: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        if (keyval == "empty") {
                            var districtlist = data.districtList;
                            $("#district option").remove();
                            $('#district').append('<option value="">--Select District--</option>');
                            $.each(districtlist, function (index, item) {
                                $('#district').append("<option value='" + item.code + "'>" + item.description + "</option>");
                            });

                        }
                        var lalist = data.laList;
                        $("#la option").remove();
                        $('#la').append('<option value="">--Select Local Authority--</option>');
                        $.each(lalist, function (index, item) {
                            $('#la').append("<option value='" + item.code + "'>" + item.description + "</option>");
                        });

                        $("#province").val(data.province);
                        $("#district").val(data.district);

                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutLogin.action?";
                    }
                });
            }

            function changeWardFromLA(keyval) {

                if (keyval == '') {
                    keyval = "empty";
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/findWardCandidate.action',
                    data: {la: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        if (keyval == "empty") {
                            var districtlist = data.districtList;
                            $("#district option").remove();
                            $('#district').append('<option value="">--Select District--</option>');
                            $.each(districtlist, function (index, item) {
                                $('#district').append("<option value='" + item.code + "'>" + item.description + "</option>");
                            });


                            var lalist = data.laList;
                            $("#la option").remove();
                            $('#la').append('<option value="">--Select Local Authority--</option>');
                            $.each(lalist, function (index, item) {
                                $('#la').append("<option value='" + item.code + "'>" + item.description + "</option>");
                            });

                        }
                        var wardlist = data.wardList;
                        $("#ward option").remove();
                        $('#ward').append('<option value="">--Select Ward--</option>');
                        $.each(wardlist, function (index, item) {
                            $('#ward').append("<option value='" + item.code + "'>" + item.description + "</option>");
                        });

                        $("#district").val(data.district);
                        $("#province").val(data.province);

                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutLogin.action?";
                    }
                });
            }

            function changeAllFromWard(keyval) {

                if (keyval == '') {
                    keyval = "empty";
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/findAllCandidate.action',
                    data: {ward: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        if (keyval == "empty") {
                            var districtlist = data.districtList;
                            $("#district option").remove();
                            $('#district').append('<option value="">--Select District--</option>');
                            $.each(districtlist, function (index, item) {
                                $('#district').append("<option value='" + item.code + "'>" + item.description + "</option>");
                            });


                            var lalist = data.laList;
                            $("#la option").remove();
                            $('#la').append('<option value="">--Select Local Authority--</option>');
                            $.each(lalist, function (index, item) {
                                $('#la').append("<option value='" + item.code + "'>" + item.description + "</option>");
                            });


                            var wardlist = data.wardList;
                            $("#ward option").remove();
                            $('#ward').append('<option value="">--Select Ward--</option>');
                            $.each(wardlist, function (index, item) {
                                $('#ward').append("<option value='" + item.code + "'>" + item.description + "</option>");
                            });

                        }



                        $("#province").val(data.province);
                        $("#district").val(data.district);
                        $("#la").val(data.la);
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutLogin.action?";
                    }
                });
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
            <div class="cont-breadCrumb">Candidate Management</div>
            <div class="cont-msg">
                <s:div id="divmsg">
                    <s:actionerror theme="jquery"/>
                    <s:actionmessage theme="jquery"/>
                </s:div>
            </div>
            <div class="cont-form">
                <s:form id="CandidateForm" method="post" action="Candidate" theme="simple" cssClass="form" enctype="multipart/form-data">
                    <s:hidden id="oldvalue" name="oldvalue" ></s:hidden>

                        <div class="col-sm-3">
                            <div class="form-group">
                                <span style="color: red">*</span><label>Province</label>
                            <s:select onchange="changeDistrictFromProvince(this.value)" cssClass="form-control" id="province" list="%{provinceList}"  headerValue="--Select Party--" headerKey="" name="province" listKey="code" listValue="description" />
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>District</label>
                            <s:select onchange="changeLAFromDistrict(this.value)" cssClass="form-control" id="district" list="%{districtList}"  headerValue="--Select District--" headerKey="" name="district" listKey="code" listValue="description" />
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Local Authority</label>
                            <s:select onchange="changeWardFromLA(this.value)" cssClass="form-control" id="la" list="%{laList}"  headerValue="--Select Local Authority--" headerKey="" name="la" listKey="code" listValue="description" />
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Ward</label>
                            <s:select onchange="changeAllFromWard(this.value)" cssClass="form-control" id="ward" list="%{wardList}"  headerValue="--Select Ward--" headerKey="" name="ward" listKey="code" listValue="description" />
                        </div>
                    </div>

                    <div class="col-sm-12" style="height: 1px;width: 100%;background-color: #d8d8d8;margin: 15px 0;"></div>

                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Party</label>
                            <s:select  cssClass="form-control" id="party" list="%{partyList}"  headerValue="--Select Party--" headerKey="" name="party" listKey="partyCode" listValue="description" />
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Name</label>
                            <s:textfield cssClass="form-control" name="name" id="name" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g, ''))"/>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>NIC</label>
                            <s:textfield cssClass="form-control" name="nic" id="nic" maxLength="20" onkeyup="$(this).val($(this).val().replace(/[^0-9+#]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^0-9#+]/g, ''))"/>
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
                            <span style="color: red">*</span><label>Address</label>
                            <s:textfield cssClass="form-control" name="address" id="address" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g, ''))"/>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Gender</label>
                            <s:select  cssClass="form-control" id="gender" list="%{genderList}"  headerValue="--Gender--" headerKey="" name="gender" listKey="code" listValue="description" />
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Youth</label>
                            <s:select  cssClass="form-control" id="youth" list="%{youthList}"  headerValue="--Youth--" headerKey="" name="youth" listKey="code" listValue="description" />
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Status</label>
                            <s:select  cssClass="form-control" id="status" list="%{statusList}"  headerValue="--Select Status--" headerKey="" name="status" listKey="code" listValue="description" />
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
                'OK':function() { deleteCandidate($(this).data('keyval'));$( this ).dialog( 'close' ); },
                'Cancel':function() { $( this ).dialog( 'close' );} 
                }" 
                autoOpen="false" 
                modal="true" 
                title="Delete Candidate"                            
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
                <s:url var="listurl" action="listCandidate"/>
                <sjg:grid
                    id="gridtable"
                    caption="Candidate Management"
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
                    <sjg:gridColumn name="candidateId" index="u.partyCode" title="Edit" width="50" align="center" formatter="editformatter" sortable="false" />
                    <sjg:gridColumn name="candidateId" index="u.partyCode" title="Delete" width="40" align="center" formatter="deleteformatter" sortable="false" />  
                    <sjg:gridColumn name="candidateId" index="u.partyId" title="Candidate ID"  sortable="true" />
                    <sjg:gridColumn name="party" index="u.party.description" title="Party"  sortable="true" frozen="true"/>
                    <sjg:gridColumn name="wardCode" index="u.wardCode" title="Ward"  sortable="true"/>
                    <sjg:gridColumn name="name" index="u.name" title="Name"  sortable="true"/>
                    <sjg:gridColumn name="nic" index="u.nic" title="NIC"  sortable="true"/>
                    <sjg:gridColumn name="contactNo" index="u.contactNo" title="Contact No"  sortable="true" />
                    <sjg:gridColumn name="address" index="u.address" title="Address"  sortable="true" />
                    <sjg:gridColumn name="gender" index="u.gender" title="Gender"  sortable="true" />
                    <sjg:gridColumn name="youth" index="u.youth" title="Youth"  sortable="true" />
                    <sjg:gridColumn name="status" index="u.status" title="Status"  sortable="true" />
                </sjg:grid> 
            </div>
        </div>
        <!--footer-->
        <jsp:include page="/footer.jsp"/>
    </body>
</html>
