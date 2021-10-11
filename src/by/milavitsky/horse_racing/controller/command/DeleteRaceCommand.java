package by.milavitsky.horse_racing.controller.command;

import by.milavitsky.horse_racing.controller.Command;
import by.milavitsky.horse_racing.controller.Router;
import by.milavitsky.horse_racing.entity.enums.PermissionEnum;
import by.milavitsky.horse_racing.exception.CommandException;
import by.milavitsky.horse_racing.exception.ServiceException;
import by.milavitsky.horse_racing.service.ServiceFactory;
import by.milavitsky.horse_racing.service.service_interface.RaceServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

import static by.milavitsky.horse_racing.controller.CommandParameter.PAGE_REDIRECT_INDEX;
import static by.milavitsky.horse_racing.controller.CommandParameter.PARAM_RACE_ID;
import static by.milavitsky.horse_racing.entity.enums.PermissionEnum.CUSTOMER_BASIC;

import static org.apache.commons.lang3.StringUtils.isNoneEmpty;

public class DeleteRaceCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String raceId = request.getParameter(PARAM_RACE_ID);
            if (isNoneEmpty(raceId)) {
                RaceServiceInterface raceService = (RaceServiceInterface) ServiceFactory.getInstance().getClass(RaceServiceInterface.class);
                raceService.delete(raceId);
            }
            Router router = new Router(PAGE_REDIRECT_INDEX);
            router.redirect();
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(CUSTOMER_BASIC);
    }
}
