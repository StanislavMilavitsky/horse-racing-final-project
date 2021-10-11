package by.milavitsky.horse_racing.controller.command;

import by.milavitsky.horse_racing.controller.Command;
import by.milavitsky.horse_racing.controller.Router;
import by.milavitsky.horse_racing.entity.Horse;
import by.milavitsky.horse_racing.entity.enums.PermissionEnum;
import by.milavitsky.horse_racing.exception.CommandException;
import by.milavitsky.horse_racing.exception.ServiceException;
import by.milavitsky.horse_racing.service.ServiceFactory;
import by.milavitsky.horse_racing.service.service_interface.BetServiceInterface;
import by.milavitsky.horse_racing.service.service_interface.HorseServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static by.milavitsky.horse_racing.controller.CommandParameter.*;
import static by.milavitsky.horse_racing.entity.enums.PermissionEnum.CUSTOMER_BASIC;
import static by.milavitsky.horse_racing.entity.enums.PermissionEnum.PLACE_RATIO;
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
                BetServiceInterface betService = (BetServiceInterface) ServiceFactory.getInstance().getClass(BetServiceInterface.class);
                boolean result = betService.addRatios(parameterMap);
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
