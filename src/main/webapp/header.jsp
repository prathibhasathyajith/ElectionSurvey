<%-- 
    Document   : header
    Created on : Dec 26, 2017, 8:59:36 AM
    Author     : prathibha_s
--%>
<header>
    <div class="cont"><i class="fa fa-cube" aria-hidden="true"></i> Some Header</div>
    <div class="coner">
        <div class="box"></div>
        <div class="box"></div>
        <div class="box"></div>
    </div>
</header>
<div class="dropdown">
    <s:form action="ViewCPSearch" method="post" id="f" theme="simple" cssStyle="float:left">
        <input type="submit" value="Change Password" class="changePassBut headerBut" style="margin-right: 5px" />
    </s:form>
    <s:form action="LogoutSearch" method="post" id="ff" theme="simple" cssStyle="float:left">
        <input type="submit" value="Logout" class="logoutBut headerBut" />
    </s:form>
</div>

