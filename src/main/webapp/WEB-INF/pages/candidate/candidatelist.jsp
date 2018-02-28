<%-- 
    Document   : candidatelist
    Created on : Dec 26, 2017, 3:31:17 PM
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
        <title>Election Candidate List</title>
        <%@include file="/stylelinks.jspf" %>
        <link href="${pageContext.request.contextPath}/resources/css/multi-select.css" rel="stylesheet"/>
        <script src="${pageContext.request.contextPath}/resources/javascript/jquery.multi-select.js" ></script>
        <script>


            var selected = [];
            var deseleted = [];

            function loadDetails() {
                var ward = $("#ward").val();
                var party = $("#party").val();

                $.ajax({
                    url: '${pageContext.request.contextPath}/loadCandidateList.action',
                    data: {ward: ward, party: party},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        var list_1 = data.list_1;
                        $("#pre-selected-options option").remove();
                        $.each(list_1, function (index, item) {
                            $('#pre-selected-options').append("<option value='" + item.candidate + "'>" + item.name + "</option>");
                        });

                        var list_2 = data.list_2;
                        $.each(list_2, function (index, item) {
                            $('#pre-selected-options').append("<option value='" + item.candidate + "' selected>" + item.name + "</option>");
                        });

                        $("#types").val(data.type);

                        $('#pre-selected-options').multiSelect({
                            afterSelect: function (values) {
                                selected.push(values[0]);
                                deseleted.remove(values[0]);

                            },
                            afterDeselect: function (values) {
                                deseleted.push(values[0]);
                                selected.remove(values[0]);
                            },
                            selectableHeader: "<div class='custom-header'>Candidate List 1</div>",
                            selectionHeader: "<div class='custom-header'>Candidate List 2</div>"
                        });
                        $('#pre-selected-options').multiSelect('refresh');


                        if ($("#party").val() != "") {
                            $("#select-box").css("display", "block");
                            $("#assignBut").attr("disabled", false);

                        } else {
                            $("#types").val("");
                            $("#ward").val("");

                            changeAllFromWard($("#ward").val());
                        }
                        $("#successMsg").css("display","none");
                        $("#errorMsg").css("display","none");
//                        $("#resetBut").attr("disabled", false);

                        countItems();
                    },

                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/logoutLogin.action?";
                    }
                });

            }


            function assign() {
                var list2 = selected;
                var ward = $("#ward").val();
                var party = $("#party").val();
                var type = $("#types").val();
                var list1 = deseleted;

                $.ajax({
                    url: '${pageContext.request.contextPath}/assignCandidateList.action',
                    data: {list1: list1, list2: list2, ward: ward, party: party, type: type},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        if (data.message == "") {
                            $("#successMsg").css("display","block");
                        } else {
                            $("#errorMsg").css("display","block");
                        }

                        $('#pre-selected-options').multiSelect('refresh');
                    },

                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/logoutLogin.action?";
                    }
                });

            }

            function countItems() {
                var values = $('#pre-selected-options').val();
                selected = [];
                if (values != null) {
                    for (var i = 0; i < values.length; i++) {
                        selected.push(values[i]);
                    }
                }
            }

            function changeDistrictFromProvince(keyval) {

                if (keyval == '') {
                    keyval = "empty";
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/findDistrictCandidateList.action',
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

                        $("#la").val("");
                        $("#ward").val("");
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/logoutLogin.action?";
                    }
                });
            }

            function changeLAFromDistrict(keyval) {

                if (keyval == '') {
                    keyval = "empty";
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/findLACandidateList.action',
                    data: {district: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

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
                        var lalist = data.laList;
                        $("#la option").remove();
                        $('#la').append('<option value="">--Select Local Authority--</option>');
                        $.each(lalist, function (index, item) {
                            $('#la').append("<option value='" + item.code + "'>" + item.description + "</option>");
                        });

                        $("#province").val(data.province);
                        $("#district").val(data.district);

                        $("#ward").val("");

                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/logoutLogin.action?";
                    }
                });
            }

            function changeWardFromLA(keyval) {

                if (keyval == '') {
                    keyval = "empty";
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/findWardCandidateList.action',
                    data: {la: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        var districtlist = data.districtList;
                        $("#district option").remove();
                        $('#district').append('<option value="">--Select District--</option>');
                        $.each(districtlist, function (index, item) {
                            $('#district').append("<option value='" + item.code + "'>" + item.description + "</option>");
                        });

                        var wardlist = data.wardList;
                        $("#ward option").remove();
                        $('#ward').append('<option value="">--Select Ward--</option>');
                        $.each(wardlist, function (index, item) {
                            $('#ward').append("<option value='" + item.code + "'>" + item.description + "</option>");
                        });


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
                        window.location = "${pageContext.request.contextPath}/logoutLogin.action?";
                    }
                });
            }

            function changeAllFromWard(keyval) {

                if (keyval == '') {
                    keyval = "empty";
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/findAllCandidateList.action',
                    data: {ward: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {


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

                        $("#party").prop("disabled", false);
                        $("#party").val("");
                        $("#select-box").css("display", "none");

                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/logoutLogin.action?";
                    }
                });
            }


        </script>
        <style>
            #select-box{
                margin: 20px 0 20px 0;
                display: none;
            }
            .custom-header{
                text-align: center;
                margin: 3px;
                color:gray;
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
            <div class="cont-breadCrumb">Candidate List Management</div>
            <div class="cont-msg">
                <div class="ui-widget actionMessage" id="successMsg" style="display: none">
                    <div class="ui-state-highlight ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> 
                        <p><span class="ui-icon ui-icon-info" style="float: left; margin-right: 0.3em;"></span>
                            <span>Candidate updated successfully</span></p>
                    </div>
                </div>
                <div class="ui-widget actionError" id="errorMsg"  style="display: none">
                    <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> 
                        <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>
                            <span>Province cannot be empty</span></p>
                    </div>
                </div>


            </div>
            <div class="cont-form">

                <s:form id="CandidateListForm" method="post" action="CandidateList" theme="simple" cssClass="form" enctype="multipart/form-data">
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Province</label>
                            <s:select onchange="changeDistrictFromProvince(this.value);" cssClass="form-control" id="province" list="%{provinceList}"  headerValue="--Select Province--" headerKey="" name="province" listKey="code" listValue="description" />
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>District</label>
                            <s:select onchange="changeLAFromDistrict(this.value);" cssClass="form-control" id="district" list="%{districtList}"  headerValue="--Select District--" headerKey="" name="district" listKey="code" listValue="description" />
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Local Authority</label>
                            <s:select onchange="changeWardFromLA(this.value);" cssClass="form-control" id="la" list="%{laList}"  headerValue="--Select Local Authority--" headerKey="" name="la" listKey="code" listValue="description" />
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Ward</label>
                            <s:select onchange="changeAllFromWard(this.value);" cssClass="form-control" id="ward" list="%{wardList}"  headerValue="--Select Ward--" headerKey="" name="ward" listKey="code" listValue="description" />
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Party</label>
                            <s:select disabled="true" onchange="loadDetails()" cssClass="form-control" id="party" list="%{partyList}"  headerValue="--Select Party--" headerKey="" name="party" listKey="partyCode" listValue="partyCode" />
                        </div>
                    </div>
                    <!--                    <div class="col-sm-3">
                                            <div class="form-group">
                                                <span style="color: red">*</span><label>List Type</label>
                    <%--<s:select  cssClass="form-control" id="types" list="%{typeList}"  headerValue="--List Type--" headerKey="" name="type" listKey="code" listValue="description" />--%>
                </div>
            </div>-->

                </s:form>
                <div class="row col-sm-12" id="select-box">
                    <select id='pre-selected-options' multiple='multiple'></select>
                </div>

                <div class="row form-inline">
                    <div class="col-sm-12" style="margin-left: 15px;">
                        <div class="form-group">
                            <input type="button" id="assignBut" class="btn btn-success" onclick="assign()"  value="Assign" disabled="true"/>
                        </div>
                        <div class="form-group">
                            <input type="button" id="resetBut" class="btn btn-success" onclick="loadDetails()"  value="Reset" />
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <!--footer-->
        <jsp:include page="/footer.jsp"/>
    </body>
</html>
