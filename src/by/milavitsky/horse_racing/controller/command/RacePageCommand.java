package by.milavitsky.horse_racing.controller.command;

import by.milavitsky.horse_racing.controller.Command;
import by.milavitsky.horse_racing.controller.Router;
import by.milavitsky.horse_racing.entity.Bet;
import by.milavitsky.horse_racing.entity.Horse;
import by.milavitsky.horse_racing.entity.enums.PermissionEnum;
import by.milavitsky.horse_racing.exception.CommandException;
import by.milavitsky.horse_racing.exception.ServiceException;
import by.milavitsky.horse_racing.service.ServiceFactory;
import by.milavitsky.horse_racing.service.service_interface.BetServiceInterface;
import by.milavitsky.horse_racing.service.service_interface.HorseServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

import static by.milavitsky.horse_racing.controller.CommandParameter.*;
import static by.milavitsky.horse_racing.entity.enums.PermissionEnum.*;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class RacePageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String raceId = request.getParameter(PARAM_RACE_ID);
            if (isEmpty(raceId)) {
                return new Router(PAGE_REDIRECT_INDEX);
            }
            HorseServiceInterface horseService = (HorseServiceInterface) ServiceFactory.getInstance().getClass(HorseServiceInterface.class);
            Set<Horse> horses = horseService.showByRace(raceId);
            request.setAttribute(ATTR_RACE_SET, horses);
            BetServiceInterface betService = (BetServiceInterface) ServiceFactory.getInstance().getClass(BetServiceInterface.class);
            List<Bet> ratios = betService.findRatioByRaceId(raceId);
            request.setAttribute(ATTR_RATIO_LIST, ratios);
            return new Router(PAGE_RACE);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(GUEST_BASIC) || permissions.contains(USER_BASIC) ||
                permissions.contains(ADMIN_BASIC) || permissions.contains(CUSTOMER_BASIC);
    }
}