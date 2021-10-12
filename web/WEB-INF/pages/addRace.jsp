<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@include file="/WEB-INF/pages/util/localization.jsp" %>
    <%@include file="/WEB-INF/pages/util/bootstrap.jsp" %>
    <title><fmt:message key="label.title.profile"/></title>
</head>
<body data-topbar="dark" data-layout="horizontal">
<div id="layout-wrapper">
    <%@include file="/WEB-INF/pages/fragments/header.jsp" %>
    <div class="container-fluid  page-content ">
        <div class="col-lg-12 card  ">
            <div class="table-responsive">
                <form class="form-horizontal" action="${pageContext.request.contextPath}/dispatcher" method="post">
                    <input type="hidden" name="command" value="add_race">
                    <div class="card card-body">
                        <div class="form-group row">
                            <label for="example-text-input" class="col-md-2 col-form-label">
                                <fmt:message key="label.location"/>
                            </label>
                            <div class="col-md-10">
                                <input class="form-control" type="text" name="location" id="example-text-input"
                                       required pattern="^[ a-zA-Zа-яА-Я'-]{3,50}$">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="example-datetime-local-input" class="col-md-2 col-form-label">
                                <fmt:message key="label.race.date"/>
                            </label>
                            <div class="col-md-10">
                                <input class="form-control" type="datetime-local" name="date"
                                       id="example-datetime-local-input">
                            </div>
                        </div>
                        <c:forEach var="i" begin="0" end="5" step="1">
                            <div class="form-group row">
                                <label for="horse-input" class="col-md-2 col-form-label">
                                    <fmt:message key="label.profile.horse"/> ${i+1}
                                </label>
                                <div class="col-md-10">
                                    <select class="custom-select" id="horse-input" name="horse${i}">
                                        <option value="${selected}" selected>
                                            <fmt:message key="label.race.choose"/>
                                        </option>
                                        <c:forEach var="horse" items="${horseList}">
                                            <option value="${horse.id}">
                                                    ${horse.name}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </c:forEach>
                        <button class="btn btn-primary" type="submit" type="submit">
                            <fmt:message key="label.race.add"/>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/pages/fragments/footer.jsp" %>
</div>
</body>
</html>
