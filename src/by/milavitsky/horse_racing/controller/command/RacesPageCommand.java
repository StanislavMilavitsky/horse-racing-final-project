package by.milavitsky.horse_racing.controller.command;

import by.milavitsky.horse_racing.controller.Command;
import by.milavitsky.horse_racing.controller.Router;
import by.milavitsky.horse_racing.entity.Race;
import by.milavitsky.horse_racing.entity.enums.PermissionEnum;
import by.milavitsky.horse_racing.exception.CommandException;
import by.milavitsky.horse_racing.exception.ServiceException;
import by.milavitsky.horse_racing.service.ServiceFactory;
import by.milavitsky.horse_racing.service.service_interface.RaceServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

import static by.milavitsky.horse_racing.controller.CommandParameter.*;
import static by.milavitsky.horse_racing.entity.enums.PermissionEnum.*;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class RacesPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String check = request.getParameter(PARAM_RACE_CHECK);
            if (isEmpty(check)) {
                request.setAttribute(ATTR_CHECKBOX, ATTR_CHECKBOX_OFF);
                RaceServiceInterface raceService = (RaceServiceInterface) ServiceFactory.getInstance().getClass(RaceServiceInterface.class);
                int pagesCount = raceService.pageNumberActive();
                request.setAttribute(ATTR_PAGE_NUMBER, pagesCount);
                String page = request.getParameter(PARAM_PAGE);
                List<Race> races = raceService.showAllActive(page);
                request.setAttribute(ATTR_RACES_LIST, races);
            }
            if (isNotEmpty(check) && equalsIgnoreCase(check, CHECKBOX)) {
                request.setAttribute(ATTR_CHECKBOX, ATTR_CHECKBOX_ON);
                RaceServiceInterface raceService = (RaceServiceInterface) ServiceFactory.getInstance().getClass(RaceServiceInterface.class);
                int pagesCount = raceService.pageNumberAll();
                request.setAttribute(ATTR_PAGE_NUMBER, pagesCount);
                String page = request.getParameter(PARAM_PAGE);
                List<Race> races = raceService.showAll(page);
                request.setAttribute(ATTR_RACES_LIST, races);
            }
            return new Router(PAGE_RACES);
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
