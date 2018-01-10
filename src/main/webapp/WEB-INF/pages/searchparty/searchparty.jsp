<%-- 
    Document   : searchparty
    Created on : Jan 10, 2018, 1:35:14 PM
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
                    url: '${pageContext.request.contextPath}/findDistrictSearchPty.action',
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
                    url: '${pageContext.request.contextPath}/findLASearchPty.action',
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
                        window.location = "${pageContext.request.contextPath}/logoutLogin.action?";
                    }
                });
            }

            function findAllFromLA(keyval) {

                if (keyval == '') {
                    keyval = "empty";
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/findAllSearchPty.action',
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
                        $("#district").val(data.district);
                        $("#province").val(data.province);

                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/logoutLogin.action?";
                    }
                });
            }


            function load() {
                var la = $("#la").val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/loadSearchPty.action',
                    data: {la: la},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        if (data.message == null) {
                            var detais = data.countList;

                            $("#searchResults").hide();
                            $('#table-votingSummary').empty();
//                            $('#table-Summary').empty();
                            $("#errorMsg").empty();

                            if (Number(data.fullCount)>0) {

                                $('#table-votingSummary').append("<tr><th>Party</th><th>Vote Count</th><th>Percentage</th><th>Local Authority</th></tr>");
                                $.each(detais, function (index, item) {
                                    $('#table-votingSummary').append("<tr><td>" + item.columName1 + "</td><td>" + item.count + "</td><td>" + item.percentage1 + "</td><td>"+item.columName2+"</td></tr>");
                                });

//                                $('#table-Summary').append("<tr><td>Total number of valid votes</td><td>" + data.fullCount + "</tr>");
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
                $("#searchResults").show();
                $("#searchResults").text("Search results will show here");
                $('#table-votingSummary').empty();
                $('#table-Summary').empty();
                $("#errorMsg").empty();
                $("#province").val("");
                $("#district").val("");
                $("#la").val("");

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

                <s:form id="SearchPtyForm" method="post" action="SearchPty" theme="simple" cssClass="form" enctype="multipart/form-data">

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
                            <s:select onchange="findAllFromLA(this.value);" cssClass="form-control" id="la" list="%{laList}"  headerValue="--Select Local Authority--" headerKey="" name="la" listKey="code" listValue="description" />
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
            <!--            <div class="cont-summary">
                            <table id="table-Summary" >
            
                            </table>
                        </div>-->

        </div>
        <!--footer-->
        <jsp:include page="/footer.jsp"/>
    </body>
</html>
