package by.milavitsky.horse_racing.controller.command;

import by.milavitsky.horse_racing.controller.Command;
import by.milavitsky.horse_racing.controller.Router;
import by.milavitsky.horse_racing.entity.enums.PermissionEnum;
import by.milavitsky.horse_racing.service.ServiceFactory;
import by.milavitsky.horse_racing.service.service_interface.UserServiceInterface;
import by.milavitsky.horse_racing.exception.CommandException;
import by.milavitsky.horse_racing.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Set;

import static by.milavitsky.horse_racing.entity.enums.PermissionEnum.ADMIN_BASIC;
import static by.milavitsky.horse_racing.entity.enums.PermissionEnum.BAN_USER;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static by.milavitsky.horse_racing.controller.CommandParameter.PARAM_USER_ID;

public class BanUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String userId = request.getParameter(PARAM_USER_ID);
            if (isNotEmpty(userId)) {
                UserServiceInterface userService = (UserServiceInterface) ServiceFactory.getInstance().getClass(UserServiceInterface.class);
                userService.ban(userId);
            }
            Router router = new Router(request);
            router.redirect();
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(ADMIN_BASIC) && permissions.contains(BAN_USER);
    }
}
