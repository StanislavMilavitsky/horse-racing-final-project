<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html >
<head>
    <%@include file="/WEB-INF/pages/util/localization.jsp" %>
    <meta charset="utf-8" />
    <title>500 Error</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta content="Premium Multipurpose Admin & Dashboard Template" name="description" />
    <meta content="Themesdesign" name="author" />
    <!-- App favicon -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/images/favicon.ico">

    <!-- Bootstrap Css -->
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css" />
    <!-- Icons Css -->
    <link href="${pageContext.request.contextPath}/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
    <!-- App Css-->
    <link href="${pageContext.request.contextPath}/assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css" />

</head>

<body>

<div class="my-5 pt-5">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="text-center my-5">
                    <h1 class="font-weight-bold text-error">5 <span class="error-text">0<img src="${pageContext.request.contextPath}/assets/images/error-img.png" alt="" class="error-img"></span> 0</h1>
                    <h3 class="text-uppercase">Internal Server Error</h3>
                    <div class="mt-5 text-center">
                        <a class="btn btn-primary waves-effect waves-light" href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="label.home"/></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- JAVASCRIPT -->
<script src="${pageContext.request.contextPath}/assets/libs/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/libs/metismenu/metisMenu.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/libs/simplebar/simplebar.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/libs/node-waves/waves.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/js/app.js"></script>

</body>
</html>