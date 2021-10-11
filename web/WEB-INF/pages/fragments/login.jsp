<form class="form-horizontal" action="${pageContext.request.contextPath}/dispatcher"
      method="post">
    <input type="hidden" name="command" value="login">
    <div class="form-group">
        <input type="email"
               id="email"
               name="email"
               class="form-control"
               autofocus="autofocus"
               placeholder="<fmt:message key="label.header.email"/>"
               value="${email}">
    </div>
    <div class="form-group">
        <input type="password"
               id="password"
               name="password"
               class="form-control"
               placeholder="<fmt:message key="label.header.password"/>"
               value="${password}">
    </div>
    <button class="btn btn-primary" type="submit" type="submit">
        <fmt:message key="label.login"/>
    </button>
    </div>
</form>

<c:if test="${not empty emptyParams}">
    <div class="alert alert-primary" role="alert">
            ${emptyParams}
    </div>
</c:if>
<c:if test="${not empty incorrectData}">
    <div class="alert alert-primary" role="alert">
            ${incorrectData}
    </div>
</c:if>