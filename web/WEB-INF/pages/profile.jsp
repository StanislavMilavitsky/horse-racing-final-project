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


        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class=" card-body">
                        <div class="row">
                            <div class="col-sm-3"><h6 class="mb-0"><fmt:message
                                    key="label.registration.name"/></h6>
                            </div>
                            <div class="col-sm-9 text-secondary">${sessionScope.userAuth.name}</div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3"><h6 class="mb-0"><fmt:message
                                    key="label.registration.surname"/></h6>
                            </div>
                            <div class="col-sm-9 text-secondary"> ${sessionScope.userAuth.surname}</div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3"><h6 class="mb-0"><fmt:message key="label.user.email"/></h6>
                            </div>
                            <div class="col-sm-9 text-secondary"> ${sessionScope.userAuth.email}</div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3"><h6 class="mb-0"><fmt:message key="label.user.cash"/></h6>
                            </div>
                            <div class="col-sm-9 text-secondary"> ${sessionScope.userAuth.cash}</div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3"><h6 class="mb-0"><fmt:message
                                    key="label.user.role.name"/></h6></div>
                            <div class="col-sm-9 text-secondary">${sessionScope.userAuth.role.name}</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class=" card">
                    <div class="card-body">
                        <button class="btn btn-primary mr-1" type="submit" type="submit">
                            <a class="text-reset notification-item"
                               href="${pageContext.request.contextPath}/dispatcher?command=update_cash&addCash=10">
                                <span class="noti-dot"><fmt:message key="label.addCash.10"/></span>
                            </a>
                        </button>

                        <button class="btn btn-primary mr-1" type="submit" type="submit">
                            <a class="text-reset notification-item"
                               href="${pageContext.request.contextPath}/dispatcher?command=update_cash&addCash=25">
                                <span class="noti-dot"><fmt:message key="label.addCash.25"/></span>
                            </a>
                        </button>

                        <button class="btn btn-primary mr-1" type="submit" type="submit">
                            <a class="text-reset notification-item"
                               href="${pageContext.request.contextPath}/dispatcher?command=update_cash&addCash=100">
                                <span class="noti-dot"><fmt:message key="label.addCash.100"/></span>
                            </a>
                        </button>
                    </div>
                    <div class="card-body">
                        <button class="btn btn-primary mr-1" type="submit" type="submit">
                            <a class="text-reset notification-item"
                               href="${pageContext.request.contextPath}/dispatcher?command=show_user_bets">
                                <span class="noti-dot"> <fmt:message key="label.registration.bets"/></span>
                            </a>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/pages/fragments/footer.jsp" %>
</div>
</div>
</body>
</html>