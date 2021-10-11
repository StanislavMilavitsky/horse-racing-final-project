<div class="d-flex">
    <div class="row icon-demo-content">
        <div class="col-xl-3 col-lg-4 col-sm-6">
            <a href="${pageContext.request.contextPath}/dispatcher?command=set_ratio&raceId=${race.id}"
               class="text" data-toggle="tooltip"
               data-placement="top" title="" data-original-title="<fmt:message key="label.customer.setRatio"/>">
                <i class="mdi mdi-pencil-plus font-size-24 "></i>
            </a>
        </div>
        <div class="col-xl-3 col-lg-4 col-sm-6">
            <a href="${pageContext.request.contextPath}/dispatcher?command=enter_result&raceId=${race.id}"
               class="text" data-toggle="tooltip"
               data-placement="top" title="" data-original-title="<fmt:message key="label.customer.enterResult"/>">
                <i class="mdi mdi-lead-pencil  font-size-24"></i>
            </a>
        </div>
        <div class="col-xl-3 col-lg-4 col-sm-6">
            <form method="post">
                <a href="${pageContext.request.contextPath}/dispatcher?command=delete_race&raceId=${race.id}"
                   class="text-danger" data-toggle="tooltip"
                   data-placement="top" title="" data-original-title="<fmt:message key="label.customer.deleteRace"/>">
                    <i class="mdi mdi-trash-can font-size-24"></i>
                </a>
            </form>
        </div>
    </div>
</div>