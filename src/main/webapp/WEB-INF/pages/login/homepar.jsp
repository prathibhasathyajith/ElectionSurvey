<%-- 
    Document   : home
    Created on : Dec 25, 2017, 10:04:51 AM
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
        <link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet"/>
        <%@include file="/stylelinks.jspf" %>
    </head>
    <body>
        <!--header-->
        <jsp:include page="/header.jsp"/>
        <!--nav bar-->
        <jsp:include page="/navbar.jsp"/>
        <!--body content-->
        <div class="cont-body">
            <div class="cont-breadCrumb">Party details</div>
            <div class="cont-summarycan">
                <table id="table-Summary" >
                    <tr>
                        <th>Logged Candidate Details</th>
                        <th></th>
                    </tr>
                    <tr>
                        <td>Logged Username (Party)</td>
                        <td>${PARTYLAOBJECT.username}</td>
                    </tr>
                    <tr>
                        <td>Party</td>
                        <td>${PARTYOBJECT.name}</td>
                    </tr>
                    <tr>
                        <td>Local Authority (For username -${PARTYLAOBJECT.username})</td>
                        <td><s:property value="la"/></td>
                    </tr>
                    
                </table>
            </div>
           
            <div class="cont-table">
                <table id="partyLogin">
                    <tr>
                        <th>Vote Count</th>  
                        <th>Ward</th>  
                        <th>Percentage</th>
                    </tr>
                    <s:iterator  value="countList">  
                        <tr>
                            <td><s:property value="count"/></td>
                            <td><s:property value="columName5"/></td>
                            <td><s:property value="percentage1"/></td>
                        </tr>
                    </s:iterator>  
                </table>
            </div>
        </div>
        <!--footer-->
        <jsp:include page="/footer.jsp"/>
    </body>
</html>
