package by.milavitsky.horse_racing.controller.command;

import by.milavitsky.horse_racing.controller.Command;
import by.milavitsky.horse_racing.controller.Router;
import by.milavitsky.horse_racing.entity.Horse;
import by.milavitsky.horse_racing.entity.enums.PermissionEnum;
import by.milavitsky.horse_racing.exception.CommandException;
import by.milavitsky.horse_racing.exception.ServiceException;
import by.milavitsky.horse_racing.service.ServiceFactory;
import by.milavitsky.horse_racing.service.service_interface.HorseServiceInterface;
import by.milavitsky.horse_racing.service.service_interface.RaceServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static by.milavitsky.horse_racing.controller.CommandParameter.*;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNoneEmpty;

public class AddRaceCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String location = request.getParameter(PARAM_LOCATION);
            String date = request.getParameter(PARAM_DATE);
            Set<Long> horseSet = new HashSet<>();
            for (int i = 0; i <= 5; i++) {
                String id = request.getParameter(PARAM_HORSE + i);
                if (id != null && !id.trim().isEmpty()) {
                    horseSet.add(Long.parseLong(id));
                }
            }
            if (isNoneEmpty(location) && isNotEmpty(date) && horseSet.size() >= MIN_HORSE_IN_RACE) {
                RaceServiceInterface raceService = (RaceServiceInterface) ServiceFactory.getInstance().getClass(RaceServiceInterface.class);
                boolean result = raceService.addRace(horseSet, location, date);
                if (result) {
                    Router router = new Router(PAGE_REDIRECT_INDEX);
                    router.redirect();
                    return router;
                }
            }
            HorseServiceInterface horseService = (HorseServiceInterface) ServiceFactory.getInstance().getClass(HorseServiceInterface.class);
            List<Horse> horses = horseService.findAll();
            request.setAttribute(ATTR_HORSE_LIST, horses);
            return new Router(PAGE_ADD_RACE);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(PermissionEnum.CUSTOMER_BASIC);
    }
}

