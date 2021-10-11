<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@include file="/WEB-INF/pages/util/bootstrap.jsp" %>
    <%@include file="/WEB-INF/pages/util/localization.jsp" %>
    <title>
        <fmt:message key="label.registration.title"/>
    </title>
</head>
<body data-topbar="dark" data-layout="horizontal">
<div id="layout-wrapper">
    <%@include file="/WEB-INF/pages/fragments/header.jsp" %>
    <div class="container-fluid  page-content ">

        <div class="row">
            <div class="col-12">
                <div class="page-title-box d-flex align-items-center justify-content-between">
                    <h4 class="mb-0"><fmt:message key="label.registration.name"/></h4>
                </div>
            </div>
        </div>
        <div class="col-lg-12 card  ">

            <form class="form-horizontal" action="${pageContext.request.contextPath}/dispatcher" method="post">
                <input type="hidden" name="command" value="registration">

                <div class="form-group row">
                    <label for="nameCol" class="col-md-2 col-form-label">
                        <fmt:message key="label.registration.name"/>
                    </label>
                    <div class="col-md-10">
                        <input class="form-control" id="nameCol" type="text" name="name"
                               required pattern="^[a-zA-ZАаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщъЫыьЭэЮюЯя'-]{3,20}$"
                               oninvalid="<fmt:message key="label.registration.incorrect.name"/>">
                        <script>
                            document.getElementById("nameCol").value = "<c:out value="${userMap['name']}"/>";
                        </script>
                        <c:if test="${not empty  userMap &&empty userMap['name']}">
                            <div class="alert alert-primary" role="alert">
                                <fmt:message key="label.registration.incorrect.name"/>
                            </div>
                        </c:if>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="surnameCol" class="col-md-2 col-form-label">
                        <fmt:message key="label.registration.surname"/>
                    </label>
                    <div class="col-md-10">
                        <input class="form-control" id="surnameCol" type="text" name="surname"
                               required pattern="^[a-zA-ZАаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщъЫыьЭэЮюЯя'-]{3,40}$"
                               oninvalid="<fmt:message key="label.registration.incorrect.surname"/>">
                        <script>
                            document.getElementById("surnameCol").value = "<c:out value="${userMap['surname']}"/>";
                        </script>
                        <c:if test="${not empty  userMap &&empty userMap['surname']}">
                            <div class="alert alert-primary" role="alert">
                                <fmt:message key="label.registration.incorrect.surname"/>
                            </div>
                        </c:if>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="emailCol" class="col-md-2 col-form-label">
                        <fmt:message key="label.header.email"/>
                    </label>
                    <div class="col-md-10">
                        <input class="form-control" id="emailCol"type="email"
                               oninvalid="<fmt:message key="label.registration.incorrect.email"/>">
                        <script>
                            document.getElementById("emailCol").value = "<c:out value="${userMap['email']}"/>";
                        </script>
                        <c:if test="${not empty  userMap &&empty userMap['email']}">
                            <div class="alert alert-primary" role="alert">
                                <fmt:message key="label.registration.incorrect.email"/>
                            </div>
                        </c:if>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="passwordCol" class="col-md-2 col-form-label">
                        <fmt:message key="label.header.password"/>
                    </label>
                    <div class="col-md-10">
                        <input class="form-control" id="passwordCol" type="text" name="password"
                               required pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,}$"
                               oninvalid="<fmt:message key="label.registration.incorrect.password"/>">
                        <script>
                            document.getElementById("passwordCol").value = "<c:out value="${userMap['password']}"/>";
                        </script>
                        <c:if test="${not empty  userMap &&empty userMap['password']}">
                            <div class="alert alert-primary" role="alert">
                                <fmt:message key="label.registration.incorrect.password"/>
                            </div>
                        </c:if>
                    </div>
                </div>

                <c:if test="${not empty emptyParams}">
                    <div class="alert alert-primary" role="alert">
                            ${emptyParams}
                    </div>
                </c:if>
                <button class="btn btn-primary" type="submit" type="submit">
                    <fmt:message key="label.registration.submit"/>
                </button>
            </form>

        </div>
        <%@include file="/WEB-INF/pages/fragments/footer.jsp" %>
    </div>
</body>
</html>