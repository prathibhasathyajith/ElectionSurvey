<%-- 
    Document   : votingsummary
    Created on : Jan 8, 2018, 10:22:49 AM
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
        <title>Voting Summary</title>
        <%@include file="/stylelinks.jspf" %>
        <link href="${pageContext.request.contextPath}/resources/css/multi-select.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet"/>
        <script src="${pageContext.request.contextPath}/resources/javascript/jquery.multi-select.js" ></script>
        <script>

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

            function load() {
                var ward = $("#ward").val();
//                var ward = 'KURKGMCKGW';
                var type = $("#types").val();
//                var type = 'USER';

                $.ajax({
                    url: '${pageContext.request.contextPath}/loadVotingSummary.action',
                    data: {ward: ward, type: type},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        if (data.message == null) {
                            var detais = data.countList;

                            $("#searchResults").hide();
                            $('#table-votingSummary').empty();
                            $('#table-Summary').empty();
                            $("#errorMsg").empty();

                            if (data.fullCount != null) {
                                if (type == 'PARTY') {
                                    $('#table-votingSummary').append("<tr><th>Party</th><th>Vote Count</th><th>Percentage</th></tr>");
                                    $.each(detais, function (index, item) {
                                        $('#table-votingSummary').append("<tr><td>" + item.columName1 + "</td><td>" + item.count + "</td><td>" + item.percentage1 + "</td></tr>");
                                    });
                                } else if (type == 'USER') {
                                    $('#table-votingSummary').append("<tr><th>Candidate</th><th>Vote Count</th><th>Percentage</th><th>Party</th></tr>");
                                    $.each(detais, function (index, item) {
                                        $('#table-votingSummary').append("<tr><td>" + item.columName1 + "</td><td>" + item.count + "</td><td>" + item.percentage1 + "</td><td>" + item.columName3 + "</td></tr>");
                                    });
                                }
                                $('#table-Summary').append("<tr><td>Total number of valid votes</td><td>" + data.fullCount + "</tr>");
                            } else {
                                $("#searchResults").show();
                                $("#searchResults").text("No results found");
                            }




                        } else {
                            $("#messageRes").text(data.message);
                            $("#errorMsg").show();
                        }


                    },

                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/logoutLogin.action?";
                    }
                });

            }

            function resetAll() {
                changeAllFromWard('');
                $("#searchResults").show();
                $("#searchResults").text("Search results will show here");
                $('#table-votingSummary').empty();
                $('#table-Summary').empty();
                $("#errorMsg").empty();
                $("#province").val("");
                $("#district").val("");
                $("#la").val("");
                $("#ward").val("");
                $("#types").val("");
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
            <div class="cont-breadCrumb">Voting Summary</div>
            <div class="cont-msg">
                <div class="ui-widget actionError" id="errorMsg"  style="display: none">
                    <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> 
                        <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>
                            <span id="messageRes">Voting error</span></p>
                    </div>
                </div>

            </div>
            <div class="cont-form">

                <s:form id="VotingSummaryForm" method="post" action="VotingSummary" theme="simple" cssClass="form" enctype="multipart/form-data">
                    <div class="row" style="margin-left: 0px;">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <span style="color: red">*</span><label>Search Type</label>
                                <s:select  cssClass="form-control" id="types" list="%{typeList}"  headerValue="--Select Search Type--" headerKey="" name="type" listKey="code" listValue="description" />
                            </div>
                        </div>
                    </div>


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

                </s:form>



                <div class="row form-inline">
                    <div class="col-sm-12" style="margin-left: 15px;">
                        <div class="form-group">
                            <input type="button" id="assignBut" class="btn btn-success" onclick="load()"  value="Search" />
                        </div>
                        <div class="form-group">
                            <input type="button" id="resetBut" class="btn btn-success" onclick="resetAll()"  value="Reset" />
                        </div>
                    </div>
                </div>
            </div>

            <div class="cont-table">
                <div id="searchResults" style="text-align: center;font-family: Raleway">Search results will show here</div>
                <table id="table-votingSummary" >

                </table>
            </div>
            <div class="">
                <table id="table-Summary" >

                </table>
            </div>

        </div>
        <!--footer-->
        <jsp:include page="/footer.jsp"/>
    </body>
</html>
