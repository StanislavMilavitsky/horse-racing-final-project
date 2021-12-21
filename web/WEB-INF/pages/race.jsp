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
        <div class="row">
            <div class="col-lg-12">
                <div class="card ">
                    <h4 class="card-title mb-4">
                        <fmt:message key="label.title.races"/>
                    </h4>
                    <div class="table-responsive">
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
                                    <fmt:message key="label.race.win"/>
                                </th>
                                <th scope="col">
                                    <fmt:message key="label.race.participation"/>
                                </th>
                                <th scope="col">
                                    <fmt:message key="label.race.percentageOfWin"/>
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
                                    <td>${horse.win}</td>
                                    <td>${horse.participation}</td>
                                    <td>${horse.percentageOfWin}</td>
                                    <td>${horse.jockey}</td>
                                    <c:forEach var="winRatio" items="${ratioList}">
                                        <c:if test="${winRatio.horseId==horse.id&&winRatio.typeId=='1'}">
                                            <td onmouseover="this.style.backgroundColor='#5FC1F5';"
                                                onmouseout="this.style.backgroundColor='white';"
                                                onclick="
                                                <c:if test="${not empty sessionScope.userAuth}">showOrder('${winRatio}')</c:if>">
                                                    ${winRatio.ratio}</td>
                                            <script>
                                                function showOrder(ratio) {
                                                    document.getElementById('order').style.visibility = "visible";
                                                    var array = ratio.split(',');
                                                    var betRatio = array[array.length - 1].slice(0, -1).replace('ratio=', '');
                                                    document.getElementById('betInfo').innerText = betRatio;
                                                    document.getElementById('add-parameter-ratio').value = ratio;
                                                }
                                            </script>
                                        </c:if>
                                    </c:forEach>
                                    <c:forEach var="winRatio" items="${ratioList}">
                                        <c:if test="${winRatio.horseId==horse.id&&winRatio.typeId=='2'}">
                                            <td onmouseover="this.style.backgroundColor='#5FC1F5';"
                                                onmouseout="this.style.backgroundColor='white';"
                                                onclick="
                                                <c:if test="${not empty sessionScope.userAuth}">showOrder('${winRatio}')</c:if>">
                                                    ${winRatio.ratio}</td>
                                        </c:if>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
            <div class="col-lg-6" style="visibility:hidden" id="order">
                <form class="form-horizontal" action="${pageContext.request.contextPath}/dispatcher" method="post">
                    <input id="add-parameter-bet" type="hidden" name="command" value="bet">
                    <input id="add-parameter-ratio" type="hidden" name="info">
                    <input id="add-parameter-cash" type="hidden" name="cash">
                    <input id="add-parameter-race" type="hidden" name="command" value="/race">//todo
                    <div class="card ">
                        <div class="card-body">
                            <div class="media">
                                <div class="media-body">
                                    <p class="text-truncate font-size-24 mb-2">
                                        <fmt:message key="label.order.title"/>
                                    </p>
                                </div>
                                <div class="text-primary">
                                    <i onclick="document.getElementById('order').style.visibility='hidden'"
                                       class="ri-close-circle-line font-size-24"></i>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-3"><h6 class="mb-0">
                                    <fmt:message key="label.user.cash"/>:</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">${sessionScope.userAuth.cash}</div>
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-3"><h6 class="mb-0">
                                    <fmt:message key="label.bet.info"/>:</h6>
                                </div>
                                <div id="betInfo" class="col-sm-9 text-secondary"></div>
                            </div>
                            <div class="form-group row">
                                <label for="bet-cash" class="col-sm-3 col-form-label">
                                    <h6 class="mb-0">
                                        <fmt:message key="label.bet.cash"/>:
                                    </h6>
                                </label>
                                <div class="col-sm-9">
                                    <input onkeyup="updateField()" onkeydown="updateField()" class="form-control"
                                           type="text" name="betCash" required pattern="[1-9]\d*(\.\d+)?"
                                           id="bet-cash">
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-3"><h6 class="mb-0">
                                    <fmt:message key="label.bet.possibleCash"/>:
                                </h6>
                                </div>
                                <div id="potential-win" class="col-sm-9 text-secondary"></div>
                                <script>
                                    function updateField() {
                                        var input = document.getElementById('bet-cash').value;
                                        var ratio = document.getElementById('betInfo').innerText;
                                        document.getElementById('potential-win').innerText =
                                            Math.round(parseFloat(input) * parseFloat(ratio) * 100) / 100;
                                        document.getElementById('add-parameter-cash').value = input;
                                    }
                                </script>
                            </div>
                            <button class="btn btn-primary" type="submit" type="submit">
                                <fmt:message key="label.bet"/>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

</div>
<%@include file="/WEB-INF/pages/fragments/footer.jsp" %>
</div>
</body>
</html>