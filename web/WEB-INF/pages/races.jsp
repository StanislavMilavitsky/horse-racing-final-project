<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fld" uri="formatTag" %>
<html>
<head>
    <%@include file="/WEB-INF/pages/util/localization.jsp" %>
    <%@include file="/WEB-INF/pages/util/bootstrap.jsp" %>
    <title>
        <fmt:message key="label.title.races"/>
    </title>
</head>
<c:set var="roleId" value="${sessionScope.userAuth.role.id}"/>
<body data-topbar="dark" data-layout="horizontal">
<div id="layout-wrapper">
    <%@include file="/WEB-INF/pages/fragments/header.jsp" %>
    <div class="container-fluid  page-content ">
        <div class="col-lg-12 card  ">

            <h4 class="card-title mb-4">
                <fmt:message key="label.title.races"/>
            </h4>
            <c:if test="${not empty user}">
                <form id="checkSwitch" action="${pageContext.request.contextPath}/dispatcher" method="post">
                    <input type="hidden" name="command" value="races">
                    <div class="custom-control custom-switch mb-2" dir="ltr">
                        <input type="checkbox" class="custom-control-input" id="customSwitch" name="checkAll"
                               onchange="getElementById('checkSwitch').submit()" ${pageContext.request.getAttribute('switch')  =='on' ? 'checked': ''}/>
                        <label class="custom-control-label" for="customSwitch">
                            <fmt:message key="label.race.check"/>
                        </label>
                    </div>
                </form>
            </c:if>
            <div class="table-responsive">
                <table class="table table-centered datatable dt-responsive nowrap"
                       data-page-length="5"
                       style="border-collapse: collapse; border-spacing: 0; width: 100%;">
                    <thead class="thead-light">
                    <tr>
                        <th scope="col">№</th>
                        <th scope="col">
                            <fmt:message key="label.location"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="label.date"/>
                        </th>
                        <c:if test="${roleId==3}">
                            <th scope="col">
                                <fmt:message key="label.user.action"/>
                            </th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="pageCount" value="${pageContext.request.getParameter('page')}"/>
                    <c:forEach var="race" items="${races}" varStatus="count">
                        <tr>
                            <td>
                                <c:if test="${empty pageCount}">
                                    <c:set var="pageCount" value="1"/>
                                </c:if>
                                    ${(pageCount-1)*6+1+count.index}
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/dispatcher?command=race&raceId=${race.id}">${race.location}</a>
                            </td>
                            <td>
                                <fld:format-date dateTime="${race.date}"/>
                            </td>
                            <c:if test="${roleId==3}">
                                <td>
                                    <%@include file="/WEB-INF/pages/role/customerAction.jsp" %>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <nav>
                <ul class="pagination pagination-lg">
                    <c:forEach var="page" begin="1" end="${pageNumber}">
                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/dispatcher?command=races&page=${page}${pageContext.request.getAttribute('switch')=='on' ? '&checkAll=on': ''}">${page}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </div>
        <%@include file="/WEB-INF/pages/fragments/footer.jsp" %>
    </div>
</body>
</html>