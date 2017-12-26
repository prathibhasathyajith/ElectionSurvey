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
    </head>
    <body>
        <!--header-->
        <jsp:include page="/header.jsp"/>
        <!--nav bar-->
        <jsp:include page="/navbar.jsp"/>
        <!--body content-->
        <div class="cont-body">
            <h1>Party Page</h1>
        </div>
        <!--footer-->
        <jsp:include page="/footer.jsp"/>
    </body>
</html>
