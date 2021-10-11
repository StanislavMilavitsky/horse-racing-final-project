package by.milavitsky.horse_racing.controller.command;

import by.milavitsky.horse_racing.controller.Command;
import by.milavitsky.horse_racing.controller.Router;
import by.milavitsky.horse_racing.entity.Race;
import by.milavitsky.horse_racing.entity.enums.PermissionEnum;
import by.milavitsky.horse_racing.exception.CommandException;
import by.milavitsky.horse_racing.exception.ServiceException;
import by.milavitsky.horse_racing.service.ServiceFactory;
import by.milavitsky.horse_racing.service.service_interface.BetServiceInterface;
import by.milavitsky.horse_racing.service.service_interface.RaceServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static by.milavitsky.horse_racing.controller.CommandParameter.*;
import static by.milavitsky.horse_racing.entity.enums.PermissionEnum.CUSTOMER_BASIC;
import static by.milavitsky.horse_racing.entity.enums.PermissionEnum.PLACE_RESULT;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class EnterResultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            Map<Integer, String> horseMap = new HashMap<>();
            for (int i = 0; i < 5; i++) {
                String horseId = request.getParameter(PARAM_HORSE + i);
                if (isNotEmpty(horseId)) {
                    horseMap.put(i + 1, horseId);
                }
            }
            String id = request.getParameter(PARAM_RACE_ID);
            if (isNotEmpty(id) && horseMap.size() < MIN_HORSE_IN_RACE) {
                RaceServiceInterface raceService = (RaceServiceInterface) ServiceFactory.getInstance().getClass(RaceServiceInterface.class);
                Race race = raceService.findInfo(id);
                request.setAttribute(ATTR_RACE_INFO, race);
                //request.setAttribute(ATTR_RACE_SET, race.getHorse());
                return new Router(PAGE_ENTER_RESULT);
            } else {
                if (isNotEmpty(id)) {
                    BetServiceInterface betService = (BetServiceInterface) ServiceFactory.getInstance().getClass(BetServiceInterface.class);
                    betService.enterResult(horseMap, id);
                }
                Router router = new Router(PAGE_REDIRECT_INDEX);
                router.redirect();
                return router;
            }
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(CUSTOMER_BASIC) && permissions.contains(PLACE_RESULT);
    }
}
