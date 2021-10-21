<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fld" uri="formatTag" %>
<html>
<head>
    <%@include file="/WEB-INF/pages/util/localization.jsp" %>
    <%@include file="/WEB-INF/pages/util/bootstrap.jsp" %>
    <title><fmt:message key="label.profile.bets"/></title>
</head>
<body data-topbar="dark" data-layout="horizontal">
<div id="layout-wrapper">
    <%@include file="/WEB-INF/pages/fragments/header.jsp" %>
    <div class="container-fluid  page-content ">
        <div class="col-lg-12 card  ">
            <div class="table-responsive">
                <table class="table table-centered datatable dt-responsive nowrap"
                       data-page-length="5"
                       style="border-collapse: collapse; border-spacing: 0; width: 100%;">
                    <thead class="thead-light">
                    <tr>
                        <th scope="col">№</th>
                        <th scope="col">
                            <fmt:message key="label.profile.status"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="label.profile.time"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="label.profile.location"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="label.profile.cash"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="label.profile.ratio"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="label.profile.type"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="label.profile.horse"/>
                        </th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="bet" items="${userBets}" varStatus="count">
                        <tr>
                            <td>${count.index+1} </td>
                            <c:choose>
                                <c:when test="${bet.resultStatus=='NOT_PROCESSED'}">
                                    <td bgcolor="#C9D6D3"><fmt:message key="label.profile.bet.processing"/></td>
                                </c:when>
                                <c:when test="${bet.resultStatus=='WIN'}">
                                    <td bgcolor="#8CD687"><fmt:message key="label.profile.bet.win"/></td>
                                </c:when>
                                <c:when test="${bet.resultStatus=='LOSE'}">
                                    <td bgcolor="#FC603E"><fmt:message key="label.profile.bet.lose"/></td>
                                </c:when>
                            </c:choose>
                            <td><fld:format-date dateTime="${bet.date}"/></td>
                            <td>${bet.race.hippodrome}</td>
                            <td>${bet.amountBet}</td>
                            <td>${bet.ratio}</td>
                            <td>${bet.betType}</td>
                            <td>${bet.horse.name}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/pages/fragments/footer.jsp" %>
</div>
</body>
</html>