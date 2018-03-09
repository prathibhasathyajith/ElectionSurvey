<%-- 
    Document   : navbar
    Created on : Dec 26, 2017, 9:00:00 AM
    Author     : prathibha_s
--%>
<div class="nav-bar">
    <span>Main Menu</span>
    <% if (session.getAttribute("SYSTEMUSERTYPE").equals("admin")) {%>
    <ul>
        <!--<li><a id="nav1" href="#">${SYSTEMUSERTYPE}</a></li>-->
        <li><a id="nav2" href="${pageContext.request.contextPath}/viewParty.action">Party Management</a></li>
        <li><a id="nav3" href="${pageContext.request.contextPath}/viewPartyLA.action">Party-Local Authority Management</a></li>
        <li><a id="nav4" href="${pageContext.request.contextPath}/viewCandidate.action">Candidate Management</a></li>
        <li><a id="nav5" href="${pageContext.request.contextPath}/viewCandidateList.action">Candidate List Management</a></li>
        <li><a id="nav6" href="${pageContext.request.contextPath}/viewService.action">Service List Management</a></li>
        <li><a id="nav7" href="${pageContext.request.contextPath}/viewVotingSummary.action">Voting Summary</a></li>
        <li><a id="nav8" href="${pageContext.request.contextPath}/viewServiceSummary.action">Service Summary</a></li>
        <!--<li><a id="nav8" href="#about">About</a></li>-->
    </ul>
    <% } else if (session.getAttribute("SYSTEMUSERTYPE").equals("user")) {%>
    <ul>
        <li><a id="nav9" href="${pageContext.request.contextPath}/viewLoginCAN.action">${SYSTEMUSERTYPE} - ${SYSTEMUSER}</a></li>
        <!--<li><a id="nav10" href="${pageContext.request.contextPath}/viewSearchCan.action">Search Results</a></li>-->
    </ul>
    <% } else {%>
    <ul>
        <li><a id="nav12" href="${pageContext.request.contextPath}/viewLoginPAR.action">${SYSTEMUSERTYPE}- ${SYSTEMUSER}</a></li>
        <li><a id="nav13" href="${pageContext.request.contextPath}/viewVotingSummary.action">Search Results</a></li>
    </ul>    
    <% }%>


</div>

