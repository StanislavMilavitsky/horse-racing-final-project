<c:choose>
    <c:when test="${empty cookie.locale}">
        <fmt:setLocale value="ru_RU"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="${cookie.locale.value}"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="international\message"/>