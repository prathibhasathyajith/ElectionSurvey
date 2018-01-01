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
                
                var ward = "KURKLUCKPT";
                var party = "test";
                
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

                        countItems();
                    },

                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutLogin.action?";
                    }
                })
            });



            function assign() {
                var list2 = selected;
                var ward = $("#ward").val("KURKLUCKPT");
                var party = $("#party").val("test");
                var list1 = deseleted;

                console.log(list2);

//                var data = JSON.stringify(list_1);
                $.ajax({
                    url: '${pageContext.request.contextPath}/assignCandidateList.action',
                    data: {list1: list1, list2: list2, ward: ward, party: party},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        alert();
                      
                        
                    },

                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutLogin.action?";
                    }
                });

            }

            function countItems() {
                var values = $('#pre-selected-options').val();
                if (values != null) {
                    for (var i = 0; i < values.length; i++) {
                        selected.push(values[i]);
                    }
                }
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
            <div class="cont-breadCrumb">Candidate List Management</div>
            <div class="cont-msg">
                <s:div id="divmsg">
                    <s:actionerror theme="jquery"/>
                    <s:actionmessage theme="jquery"/>
                </s:div>
            </div>
            <div class="cont-form">
                <select id='pre-selected-options' multiple='multiple'></select>


                <input type="button" onclick="assign()"  value="assign"/>
            </div>
        </div>
        <script>

//            $('#pre-selected-options').multiSelect();



        </script>
        <!--footer-->

        <jsp:include page="/footer.jsp"/>
    </body>
</html>
