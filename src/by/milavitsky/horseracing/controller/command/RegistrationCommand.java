package by.milavitsky.horseracing.controller.command;

import by.milavitsky.horseracing.controller.Command;
import by.milavitsky.horseracing.controller.Router;
import by.milavitsky.horseracing.entity.User;
import by.milavitsky.horseracing.entity.enumentity.PermissionEnum;
import by.milavitsky.horseracing.service.ServiceFactory;
import by.milavitsky.horseracing.service.serviceinterface.UserService;
import by.milavitsky.horseracing.exception.CommandException;
import by.milavitsky.horseracing.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.Set;

import static by.milavitsky.horseracing.controller.CommandParameter.*;
import static by.milavitsky.horseracing.entity.enumentity.PermissionEnum.*;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class RegistrationCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String name = request.getParameter(PARAM_NAME);
            String surname = request.getParameter(PARAM_SURNAME);
            String password = request.getParameter(PARAM_PASSWORD);
            String email = request.getParameter(PARAM_EMAIL);
            if (isNotEmpty(name) && isNotEmpty(surname) && isNotEmpty(password) && isNotEmpty(email)) {
                UserService userService = (UserService) ServiceFactory.getInstance().getClass(UserService.class);
                Map<String, String> userMap = userService.registration(new User(name, surname, password, email));
                if (userMap == null) {
                    Router router = new Router(PAGE_REDIRECT_INDEX);
                    router.redirect();
                    return router;
                }
                request.setAttribute(ATTR_USER_MAP, userMap);
            }
            request.setAttribute(ATTR_EMPTY_PARAM, ATTR_EMPTY_MESSAGE);
            return new Router(PAGE_REGISTRATION);
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
