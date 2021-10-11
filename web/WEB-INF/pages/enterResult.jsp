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
            <div class="table-responsive">
                <form class="form-horizontal" action="${pageContext.request.contextPath}/dispatcher" method="post">
                    <input type="hidden" name="command" value="enter_result">
                    <input type="hidden" name="raceId" value="${param.raceId}">
                    <div class="card card-body">
                        <div class="form-group row">
                            <div class="col-sm-3"><fmt:message key="label.location"/>
                            </div>
                            <div class="col-sm-9 text-secondary">${raceInfo.location}</div>
                        </div>
                        <hr>
                        <div class="form-group row">
                            <div class="col-sm-3"><fmt:message key="label.race.date"/>
                            </div>
                            <div class="col-sm-9 text-secondary">${raceInfo.date}</div>
                        </div>
                        <hr>
                        <div class="form-group row">
                            <div class="col-sm-3"><fmt:message key="label.race.count"/>
                            </div>
                            <div class="col-sm-9 text-secondary">${raceInfo.betCount}</div>
                        </div>
                        <hr>
                        <c:if test="${not empty raceInfo.betSum}">
                            <div class="form-group row">
                                <div class="col-sm-3"><fmt:message key="label.race.betSum"/>
                                </div>
                                <div class="col-sm-9 text-secondary">${raceInfo.betSum}</div>
                            </div>
                            <hr>
                            <div class="form-group row">
                                <div class="col-sm-3"><fmt:message key="label.customer.enterResult"/>:
                                </div>
                            </div>
                        </c:if>
                        <c:forEach var="i" begin="0" end="${horseSet.size()-1}">
                            <div class="form-group row">
                                <label for="horse-input" class="col-md-2 col-form-label">
                                    <fmt:message key="label.profile.horse"/> ${i+1}
                                </label>
                                <div class="col-md-10">
                                    <select class="custom-select" id="horse-input" name="horse${i}">
                                        <option disabled selected value="">
                                            <fmt:message key="label.race.choose"/>
                                        </option>
                                        <c:forEach var="horse" items="${horseSet}">
                                            <option value="${horse.id}">
                                                    ${horse.name}
                                            </option>
                                        </c:forEach>
                                        <script>
                                            $('#select_element').find('option[selected="selected"]').each(function () {
                                                $(this).prop('selected', true);
                                            });
                                        </script>
                                    </select>
                                </div>
                            </div>
                        </c:forEach>
                        <button class="btn btn-primary" type="submit" type="submit">
                            <fmt:message key="label.race.enterResult"/>
                        </button>
                    </div>
                </form>
            </div>
        </div>
        <%@include file="/WEB-INF/pages/fragments/footer.jsp" %>
    </div>
</body>
</html>