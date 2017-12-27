<%-- 
    Document   : header
    Created on : Dec 26, 2017, 8:59:36 AM
    Author     : prathibha_s
--%>
<head>
    <script>
        $(document).ready(function () {
            $(document).ajaxStart(function () {
                $.blockUI({css: {
                        border: 'transparent',
                        backgroundColor: 'transparent',
                        zIndex: '100000000'
                    },
                    message: '<img height="136" width="136" src="${pageContext.request.contextPath}/resources/images/Ellipsis.svg" />',
                    baseZ: 20000
                });

            });

            $(document).ajaxStop(function () {
                $.unblockUI();
            });

        });

    </script>
</head>
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

