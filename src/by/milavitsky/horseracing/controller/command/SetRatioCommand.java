package by.milavitsky.horseracing.controller.command;

import by.milavitsky.horseracing.controller.Command;
import by.milavitsky.horseracing.controller.Router;
import by.milavitsky.horseracing.entity.Horse;
import by.milavitsky.horseracing.entity.enums.PermissionEnum;
import by.milavitsky.horseracing.exception.CommandException;
import by.milavitsky.horseracing.exception.ServiceException;
import by.milavitsky.horseracing.service.ServiceFactory;
import by.milavitsky.horseracing.service.serviceinterface.BetServiceInterface;
import by.milavitsky.horseracing.service.serviceinterface.HorseServiceInterface;
import by.milavitsky.horseracing.service.serviceinterface.RatioServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static by.milavitsky.horseracing.controller.CommandParameter.*;
import static by.milavitsky.horseracing.entity.enums.PermissionEnum.CUSTOMER_BASIC;
import static by.milavitsky.horseracing.entity.enums.PermissionEnum.PLACE_RATIO;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class SetRatioCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String raceId = request.getParameter(PARAM_RACE_ID);
            if (isNotEmpty(raceId)) {
                HorseServiceInterface horseService = (HorseServiceInterface) ServiceFactory.getInstance().getClass(HorseServiceInterface.class);
                Set<Horse> horses = horseService.showByRace(raceId);
                request.setAttribute(ATTR_RACE_SET, horses);
            } else {
                Map<String, String> parameterMap = new HashMap<>();
                for (Object object : request.getParameterMap().entrySet()) {
                    Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) object;
                    parameterMap.put(entry.getKey(), entry.getValue()[0]);
                }
                RatioServiceInterface ratioService = (RatioServiceInterface) ServiceFactory.getInstance().getClass(RatioServiceInterface.class);
                boolean result = ratioService.addRatios(parameterMap);
                if (result) {
                    Router router = new Router(PAGE_REDIRECT_INDEX);
                    router.redirect();
                    return router;
                }
            }
            return new Router(PAGE_SET_RATIO);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(CUSTOMER_BASIC) || permissions.contains(PLACE_RATIO);
    }
}
