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

            $(document).ready(function () {

//
////                var ward = "KURKLUCKPT";
//                var ward = $("#ward").val();
//                var party = $("#party").val();
////                var party = "test";
//
//                $.ajax({
//                    url: '${pageContext.request.contextPath}/loadCandidateList.action',
//                    data: {ward: ward, party: party},
//                    dataType: "json",
//                    type: "POST",
//                    success: function (data) {
//                        var list_1 = data.list_1;
//                        $("#pre-selected-options option").remove();
//                        $.each(list_1, function (index, item) {
//                            $('#pre-selected-options').append("<option value='" + item.candidate + "'>" + item.name + "</option>");
//                        });
//
//                        var list_2 = data.list_2;
//                        $.each(list_2, function (index, item) {
//                            $('#pre-selected-options').append("<option value='" + item.candidate + "' selected>" + item.name + "</option>");
//                        });
//
//                        $('#pre-selected-options').multiSelect({
//                            afterSelect: function (values) {
//                                selected.push(values[0]);
//                                deseleted.remove(values[0]);
//
//                            },
//                            afterDeselect: function (values) {
//                                deseleted.push(values[0]);
//                                selected.remove(values[0]);
//                            }
//                        });
//
//                        countItems();
//                    },
//
//                    error: function (data) {
//                        window.location = "${pageContext.request.contextPath}/LogoutLogin.action?";
//                    }
//                })
            });

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

                        $('#pre-selected-options').multiSelect({
                            afterSelect: function (values) {
                                selected.push(values[0]);
                                deseleted.remove(values[0]);

                            },
                            afterDeselect: function (values) {
                                deseleted.push(values[0]);
                                selected.remove(values[0]);
                            }
                        });
                        $('#pre-selected-options').multiSelect('refresh');

                        countItems();
                    },

                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutLogin.action?";
                    }
                });

            }


            function assign() {
                var list2 = selected;
//                var ward = "KURKLUCKPT";
//                var party = "test";
                var ward = $("#ward").val();
                var party = $("#party").val();
                var list1 = deseleted;

                console.log("list2" +list2);
                console.log("list1" + list1);

//                var data = JSON.stringify(list_1);
                $.ajax({
                    url: '${pageContext.request.contextPath}/assignCandidateList.action',
                    data: {list1: list1, list2: list2, ward: ward, party: party},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        alert();
                        $('#pre-selected-options').multiSelect('refresh');
                    },

                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutLogin.action?";
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
                    url: '${pageContext.request.contextPath}/findLACandidateList.action',
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
                    url: '${pageContext.request.contextPath}/findWardCandidateList.action',
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
                        window.location = "${pageContext.request.contextPath}/LogoutLogin.action?";
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
        <style>
            #select-box{
                margin: 20px 0 20px 0;
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
                <s:div id="divmsg">
                    <s:actionerror theme="jquery"/>
                    <s:actionmessage theme="jquery"/>
                </s:div>
            </div>
            <div class="cont-form">

                <s:form id="CandidateListForm" method="post" action="CandidateList" theme="simple" cssClass="form" enctype="multipart/form-data">
                    <div class="col-sm-3">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Province</label>
                            <s:select onchange="changeDistrictFromProvince(this.value);" cssClass="form-control" id="province" list="%{provinceList}"  headerValue="--Select Party--" headerKey="" name="province" listKey="code" listValue="description" />
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
                            <s:select onchange="loadDetails()" cssClass="form-control" id="party" list="%{partyList}"  headerValue="--Select Party--" headerKey="" name="party" listKey="partyCode" listValue="partyCode" />
                        </div>
                    </div>

                </s:form>
                <div class="row col-sm-12" id="select-box">
                    <select id='pre-selected-options' multiple='multiple'></select>
                </div>

                <div class="row form-inline">
                    <div class="col-sm-12">
                        <div class="form-group">
                            <input type="button" class="btn btn-success" onclick="assign()"  value="Assign"/>
                        </div>
                        <div class="form-group">
                            <input type="button" class="btn btn-success" onclick="loadDetails()"  value="Reset"/>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <!--footer-->
        <jsp:include page="/footer.jsp"/>
    </body>
</html>
