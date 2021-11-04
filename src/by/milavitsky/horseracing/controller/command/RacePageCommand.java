package by.milavitsky.horseracing.controller.command;

import by.milavitsky.horseracing.controller.Command;
import by.milavitsky.horseracing.controller.Router;
import by.milavitsky.horseracing.entity.Horse;
import by.milavitsky.horseracing.entity.enumentity.PermissionEnum;
import by.milavitsky.horseracing.entity.Ratio;
import by.milavitsky.horseracing.exception.CommandException;
import by.milavitsky.horseracing.exception.ServiceException;
import by.milavitsky.horseracing.service.ServiceFactory;
import by.milavitsky.horseracing.service.serviceinterface.HorseService;
import by.milavitsky.horseracing.service.serviceinterface.RatioService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

import static by.milavitsky.horseracing.controller.CommandParameter.*;
import static by.milavitsky.horseracing.entity.enumentity.PermissionEnum.*;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class RacePageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String raceId = request.getParameter(PARAM_RACE_ID);
            if (isEmpty(raceId)) {
                return new Router(PAGE_REDIRECT_INDEX);
            }
            HorseService horseService = (HorseService) ServiceFactory.getInstance().getClass(HorseService.class);
            Set<Horse> horses = horseService.showByRace(raceId);
            request.setAttribute(ATTR_RACE_SET, horses);
            RatioService ratioService = (RatioService) ServiceFactory.getInstance().getClass(RatioService.class);
            List<Ratio> ratios = ratioService.findRatio(raceId);
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
