<%-- 
    Document   : login.jsp
    Created on : Jun 13, 2017, 9:39:43 AM
    Author     : prathibha_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<!DOCTYPE html>
<html lang="en" >

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.png">
        <title>Election Survey Login</title>
        <link href="resources/fontawesome/css/font-awesome.min.css" rel="stylesheet"/>
        <link rel="stylesheet" href="resources/login/css/style.css">


    </head>

    <body>

        <div class="login-form">
            <h1>Election</h1>
            <form action="CheckLogin"post" >
                <div class="form-group ">
                    <input type="text" class="form-control" placeholder="Username " id="UserName">
                    <i class="fa fa-user"></i>
                </div>
                <div class="form-group log-status">
                    <input type="password" class="form-control" placeholder="Password" id="Passwod">
                    <i class="fa fa-lock"></i>
                </div>
                <span class="alert">
                    <s:if test="hasActionMessages()">
                        <div class="col-md-offset-2 col-md-6 alert alert-dismissible alert-success" style="margin-top: 75px; height: 50px;position: absolute">

                            <p><s:actionmessage/></p>
                        </div>
                    </s:if>

                    <s:if test="hasActionErrors()">

                        <div class="col-md-offset-2 col-md-6 alert alert-dismissible alert-warning" style="margin-top: 25px; height: 50px;position: absolute">

                            <p><s:actionerror/></p>
                        </div>
                    </s:if>
                </span>
                <!--<a class="link" href="#">Lost your password?</a>-->
                <button type="submit" class="log-btn" >Log in</button>

            </form>
        </div>
        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
        <script  src="resources/login/js/index.js"></script>




    </body>

</html>
