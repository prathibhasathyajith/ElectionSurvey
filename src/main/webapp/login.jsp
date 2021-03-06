<%-- 
    Document   : login2
    Created on : Jan 4, 2018, 11:22:01 AM
    Author     : prathibha_s
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.png">
        <title>Local Government Election Campaign Review Login</title>
        <link href="${pageContext.request.contextPath}/resources/assets/Css/login.css" rel="stylesheet" />
        <script>
            function formSubmit() {
                window.localStorage.removeItem("item");
                $("#formES").submit();
            }
//            var ids = window.localStorage.getItem("item");
//            alert("login "+ ids);
//            $("#" + ids).removeClass("active");
            
        </script>
    </head>

    <body>
        <div class="es-mainContent">
            <div class="es-mainbox">
                <div class="es-textContent es-text">
                    <span>Local Government</span>
                    <span>Election Campaign Review</span>
                    <span>Login</span>
                </div>
                <form id="formES" action="CheckLogin" method="post" >
                    <div class="es-fieldContent">
                        <label class="es-label es-text2">Username(Admin/Party)</label>
                        <input type="text" class="es-text es-field" name="loginUserName" >
                        <label class="es-label es-text2">Password</label>
                        <input type="password" class="es-text es-field"  name="loginPassword">
                        <select class="es-field-select es-text" name="userType" required="true">
                            <option value="">Select Login Type</option>
                            <option value="party">Party</option>
                            <!--<option value="user">Candidate</option>-->
                            <option value="admin">Admin</option>
                        </select>

                    </div>
                </form>
                <div class="es-buttonContent es-text" onclick="formSubmit()"></div>
                <div class="es-messageContent es-text">
                    <span>
                        <s:if test="hasActionErrors()">
                            <div class="error-dis">
                                <i class="fa fa-remove-sign" style="color: #ff2222;">
                                    <s:actionerror cssStyle="list-style:none;"/></i> 
                            </div>
                        </s:if>
                        <s:if test="hasActionMessages()">
                            <div class="error-dis">
                                <i class="fa fa-remove-sign" style="color: green;">
                                    <s:actionmessage cssStyle="list-style:none"/></i>
                            </div>  
                        </s:if>
                    </span>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/resources/assets/Js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/assets/Js/main.js"></script>
    </body>

</html>
