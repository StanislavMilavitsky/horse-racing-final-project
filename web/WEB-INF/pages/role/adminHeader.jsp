<!--users-->
<button type="button" class="btn header-item waves-effect">
    <a class="text-reset notification-item"
       href="${pageContext.request.contextPath}/dispatcher?command=show_users">
        <span class="noti-dot"><fmt:message key="label.title.users"/></span>
    </a>
</button>
<!--profile-->
<button type="button" class="btn header-item waves-effect">
    <a class="text-reset notification-item"
       href="${pageContext.request.contextPath}/dispatcher?command=profile">
        <span class="noti-dot"><fmt:message key="label.profile"/></span>
    </a>
</button>
<!--logout-->
<button type="button" class="btn header-item waves-effect">
    <a class="text-reset notification-item"
       href="${pageContext.request.contextPath}/dispatcher?command=logout">
        <span class="noti-dot"><fmt:message key="label.logout"/></span>
    </a>
</button>