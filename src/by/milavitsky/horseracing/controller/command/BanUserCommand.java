package by.milavitsky.horseracing.controller.command;

import by.milavitsky.horseracing.controller.Command;
import by.milavitsky.horseracing.controller.Router;
import by.milavitsky.horseracing.entity.enumentity.PermissionEnum;
import by.milavitsky.horseracing.service.ServiceFactory;
import by.milavitsky.horseracing.service.serviceinterface.UserService;
import by.milavitsky.horseracing.exception.CommandException;
import by.milavitsky.horseracing.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

import static by.milavitsky.horseracing.entity.enumentity.PermissionEnum.ADMIN_BASIC;
import static by.milavitsky.horseracing.entity.enumentity.PermissionEnum.BAN_USER;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static by.milavitsky.horseracing.controller.CommandParameter.PARAM_USER_ID;

public class BanUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String userId = request.getParameter(PARAM_USER_ID);
            if (isNotEmpty(userId)) {
                UserService userService = (UserService) ServiceFactory.getInstance().getClass(UserService.class);
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
