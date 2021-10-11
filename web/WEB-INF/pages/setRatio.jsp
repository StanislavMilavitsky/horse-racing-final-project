<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@include file="/WEB-INF/pages/util/localization.jsp" %>
    <%@include file="/WEB-INF/pages/util/bootstrap.jsp" %>
    <title><fmt:message key="label.race"/></title>
</head>
<body>
<%@include file="/WEB-INF/pages/fragments/header.jsp" %>
<body data-topbar="dark" data-layout="horizontal">
<div id="layout-wrapper">
    <%@include file="/WEB-INF/pages/fragments/header.jsp" %>
    <div class="container-fluid  page-content ">
        <div class="col-lg-12 card  ">

            <h4 class="card-title mb-4">
                <fmt:message key="label.title.races"/>
            </h4>

            <div class="table-responsive">
                <form class="form-horizontal" action="${pageContext.request.contextPath}/dispatcher" method="post">
                    <input type="hidden" name="command" value="set_ratio">
                    <table class="table table-centered datatable dt-responsive nowrap"
                           style="border-collapse: collapse; border-spacing: 0; width: 100%;">
                        <thead class="thead-light">
                        <tr>
                            <th scope="col">№</th>
                            <th scope="col">
                                <fmt:message key="label.race.name"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="label.race.age"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="label.race.jockey"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="label.race.ratioType.win"/>
                            </th>
                            <th>
                                <fmt:message key="label.race.ratioType.prize"/>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="horse" items="${horseSet}" varStatus="count">
                            <tr>
                                <td>${count.index+1} </td>
                                <td> ${horse.name} </td>
                                <td>${horse.age}</td>
                                <td>${horse.jockey}</td>
                                <td>
                                    <input class="form-control" id="winCol" type="text"
                                           name="${pageContext.request.getParameter('raceId')}|${horse.id}|win"
                                           required pattern="-?(?:\d+(?:\.\d+)?|\.\d+)">
                                </td>
                                <td>
                                    <input class="form-control" id="prizeCol" type="text"
                                           name="${pageContext.request.getParameter('raceId')}|${horse.id}|show"
                                           required pattern="-?(?:\d+(?:\.\d+)?|\.\d+)">
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <button class="btn btn-primary" type="submit" type="submit">
                        <fmt:message key="label.race.setRatio"/>
                    </button>
                </form>
            </div>
        </div>
        <%@include file="/WEB-INF/pages/fragments/footer.jsp" %>
    </div>
</body>
</html>