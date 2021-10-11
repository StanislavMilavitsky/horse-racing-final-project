<c:set var="user" value="${sessionScope.userAuth}"/>
<header id="page-topbar">
    <div class="navbar-header">
        <div class="d-flex">
            <!-- LOGO -->
            <div class="navbar-brand-box">
                <a href="${pageContext.request.contextPath}/index.jsp" class="logo logo-light">
                    <span class="logo-lg">
                        <img src="${pageContext.request.contextPath}/assets/images/logo.png" height="80">
                    </span>
                </a>
            </div>
        </div>

        <div class="d-flex">
            <div class="dropdown d-inline-block">
                <!--localization-->
                <button type="button" class="btn header-item waves-effect">
                    <a href="${pageContext.request.contextPath}/dispatcher?command=cookie&locale=en">
                        <img src="${pageContext.request.contextPath}/assets/images/flags/us.jpg" height="16">
                    </a>
                </button>

                <button type="button" class="btn header-item waves-effect">
                    <a href="${pageContext.request.contextPath}/dispatcher?command=cookie&locale=ru">
                        <img src="${pageContext.request.contextPath}/assets/images/flags/russia.jpg" class="mr-1"
                             height="16">
                    </a>
                </button>
                <c:if test="${empty user }">
                    <!--login-->
                    <button type="button" class="btn header-item waves-effect"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span class="noti-dot"><fmt:message key="label.login"/></span>
                    </button>
                    <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right p-0"
                         aria-labelledby="page-header-notifications-dropdown">
                        <div class="p-3">
                            <%@include file="/WEB-INF/pages/fragments/login.jsp" %>
                        </div>
                    </div>

                    <!--registration-->
                    <button type="button" class="btn header-item waves-effect">
                        <a class="text-reset notification-item"
                           href="${pageContext.request.contextPath}/dispatcher?command=forward_registration">
                            <span class="noti-dot"><fmt:message key="label.registration.title"/></span>
                        </a>
                    </button>
                </c:if>
                <c:if test="${ not empty user}">
                    <c:if test="${user.role.id==1}">
                        <%@include file="/WEB-INF/pages/role/adminHeader.jsp" %>
                    </c:if>
                    <c:if test="${user.role.id==2}">
                        <%@include file="/WEB-INF/pages/role/userHeader.jsp" %>
                    </c:if>
                    <c:if test="${user.role.id==3}">
                        <%@include file="/WEB-INF/pages/role/customerHeader.jsp" %>
                    </c:if>
                </c:if>
            </div>
        </div>
    </div>
</header>